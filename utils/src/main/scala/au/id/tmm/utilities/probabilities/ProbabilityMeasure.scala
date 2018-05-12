package au.id.tmm.utilities.probabilities

import spire.math.Rational

sealed trait ProbabilityMeasure[A] {
  def chanceOf(outcome: A): Rational

  def map[U](f: A => U): ProbabilityMeasure[U]

  def flatMap[U](f: A => ProbabilityMeasure[U]): ProbabilityMeasure[U]

  def asMap: Map[A, Rational]

  def anyOutcome: A

  def onlyOutcome: A

  def hasOnlyOneOutcome: Boolean

  override def toString: String = {
    val asMap = this.asMap

    val className = classOf[ProbabilityMeasure[Any]].getSimpleName

    val possibilityList = {
      if (hasOnlyOneOutcome) {
        s"${asMap.keys.head} -> always"
      } else {
        asMap.map { case (possibility, probability) =>
          s"$possibility -> $probability"
        }
          .mkString(", ")
      }
    }

    s"$className($possibilityList)"
  }

  override def equals(obj: scala.Any): Boolean = obj match {
    case that: ProbabilityMeasure[A @unchecked] => this.asMap == that.asMap
    case _ => false
  }
}

object ProbabilityMeasure {

  def always[A](outcome: A): ProbabilityMeasure[A] = Always(outcome)

  def evenly[A](possibilities: A*): ProbabilityMeasure[A] = allElementsEvenly(possibilities)

  def allElementsEvenly[A](possibilities: Traversable[A]): ProbabilityMeasure[A] = {
    val probability = Rational(1, possibilities.size)

    val asMap = possibilities
      .map(p => p -> probability)
      .toMap

    ProbabilityMeasure(
      asMap
    )
  }

  def apply[A](branches: (A, Rational)*): ProbabilityMeasure[A] = ProbabilityMeasure(branches.toMap)

  def apply[A](asMap: Map[A, Rational]): ProbabilityMeasure[A] = {
    if (asMap.size == 1 && asMap.values.head == Rational.one) {
      Always(asMap.keys.head)
    } else {
      Varied(asMap)
    }
  }

  final case class Always[A](outcome: A) extends ProbabilityMeasure[A] {
    override def chanceOf(possibility: A): Rational = {
      if (outcome == possibility) {
        Rational.one
      } else {
        Rational.zero
      }
    }

    override def map[U](f: A => U): ProbabilityMeasure[U] = Always(f(outcome))

    override def flatMap[U](f: A => ProbabilityMeasure[U]): ProbabilityMeasure[U] = f(outcome)

    override def asMap: Map[A, Rational] = Map(outcome -> Rational.one)

    override def anyOutcome: A = outcome

    override def onlyOutcome: A = outcome

    override def hasOnlyOneOutcome: Boolean = true
  }

  final case class Varied[A](asMap: Map[A, Rational]) extends ProbabilityMeasure[A] {
    require(asMap.valuesIterator.foldLeft(Rational.zero)(_ + _) == Rational.one)
    require(asMap.valuesIterator.forall(_ >= Rational.zero))
    require(asMap.size > 1)

    override def chanceOf(outcome: A): Rational = asMap.getOrElse(outcome, Rational.zero)

    override def map[U](f: A => U): ProbabilityMeasure[U] = {
      val newAsMap = asMap.map { case (key, probability) =>
        f(key) -> probability
      }

      ProbabilityMeasure(newAsMap)
    }

    override def flatMap[U](f: A => ProbabilityMeasure[U]): ProbabilityMeasure[U] = {
      val newAsMap = for {
        (possibility, branchProbability) <- asMap
        (newPossibility, subBranchProbability) <- f(possibility).asMap
      } yield newPossibility -> branchProbability * subBranchProbability

      ProbabilityMeasure(newAsMap)
    }

    override def anyOutcome: A = asMap.keys.head

    override def onlyOutcome: A = throw new IllegalStateException()

    override def hasOnlyOneOutcome: Boolean = false
  }
}
