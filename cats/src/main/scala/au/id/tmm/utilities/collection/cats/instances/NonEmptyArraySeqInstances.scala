package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptyArraySeq
import cats.data.Ior
import cats.kernel.{Eq, Hash, Semigroup}
import cats.{Align, Apply, Bimonad, CoflatMap, Eval, Functor, Monad, NonEmptyTraverse, SemigroupK, Show}
import cats.instances.arraySeq._

import scala.collection.immutable.ArraySeq

trait NonEmptyArraySeqInstances extends NonEmptyArraySeqInstances1 {

  implicit def catsStdHashForTmmUtilsNonEmptyArraySeq[A : Hash]: Hash[NonEmptyArraySeq[A]] =
    new Hash[NonEmptyArraySeq[A]] {
      override def hash(x: NonEmptyArraySeq[A]): Int                            = Hash.hash(x.underlying)
      override def eqv(x: NonEmptyArraySeq[A], y: NonEmptyArraySeq[A]): Boolean = Hash.eqv(x.underlying, y.underlying)
    }

  implicit def catsStdShowForTmmUtilsNonEmptyArraySeq[A : Show]: Show[NonEmptyArraySeq[A]] =
    new Show[NonEmptyArraySeq[A]] {
      override def show(t: NonEmptyArraySeq[A]): String = ???
    }

  implicit val catsStdInstancesForTmmUtilsNonEmptyArraySeq: NonEmptyTraverse[NonEmptyArraySeq]
    with Bimonad[NonEmptyArraySeq]
    with SemigroupK[NonEmptyArraySeq]
    with Align[NonEmptyArraySeq] = new NonEmptyTraverse[NonEmptyArraySeq] with Bimonad[NonEmptyArraySeq]
  with SemigroupK[NonEmptyArraySeq] with Align[NonEmptyArraySeq] {
    override def nonEmptyTraverse[G[_], A, B](
      fa: NonEmptyArraySeq[A],
    )(
      f: A => G[B],
    )(implicit
      G: Apply[G],
    ): G[NonEmptyArraySeq[B]] =
      reduceRightTo[A, G[NonEmptyArraySeq[B]]](fa)(a =>
        G.map[B, NonEmptyArraySeq[B]](f(a))(NonEmptyArraySeq.untagged.one)) {
        case (a, evalGNesB) => {
          G.map2Eval[B, NonEmptyArraySeq[B], NonEmptyArraySeq[B]](f(a), evalGNesB) {
            case (b, nesB) => nesB.prepended(b)
          }
        }
      }.value

    override def combineK[A](x: NonEmptyArraySeq[A], y: NonEmptyArraySeq[A]): NonEmptyArraySeq[A] = x concat y

    override def functor: Functor[NonEmptyArraySeq] = this

    override def align[A, B](fa: NonEmptyArraySeq[A], fb: NonEmptyArraySeq[B]): NonEmptyArraySeq[Ior[A, B]] =
      NonEmptyArraySeq.untagged.fromArraySeqUnsafe(Align[ArraySeq].align(fa.underlying, fb.underlying))

    override def foldLeft[A, B](fa: NonEmptyArraySeq[A], b: B)(f: (B, A) => B): B = fa.foldLeft(b)(f)

    override def foldRight[A, B](fa: NonEmptyArraySeq[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa.foldRight(lb)(f)

    override def flatMap[A, B](fa: NonEmptyArraySeq[A])(f: A => NonEmptyArraySeq[B]): NonEmptyArraySeq[B] =
      fa.flatMap(f)

    override def tailRecM[A, B](a: A)(f: A => NonEmptyArraySeq[Either[A, B]]): NonEmptyArraySeq[B] =
      NonEmptyArraySeq.untagged.fromArraySeqUnsafe(Monad[ArraySeq].tailRecM(a)(f.andThen(_.underlying)))

    override def pure[A](x: A): NonEmptyArraySeq[A] = NonEmptyArraySeq.untagged.one(x)

    override def coflatMap[A, B](fa: NonEmptyArraySeq[A])(f: NonEmptyArraySeq[A] => B): NonEmptyArraySeq[B] =
      NonEmptyArraySeq.untagged.fromArraySeqUnsafe(
        CoflatMap[ArraySeq].coflatMap(fa.underlying)(f.compose(NonEmptyArraySeq.untagged.fromArraySeqUnsafe[A])))

    override def reduceLeftTo[A, B](fa: NonEmptyArraySeq[A])(f: A => B)(g: (B, A) => B): B =
      fa.tail.foldLeft[B](f(fa.head))(g)

    override def reduceRightTo[A, B](fa: NonEmptyArraySeq[A])(f: A => B)(g: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa.init.foldRight[Eval[B]](Eval.now[A](fa.last).map(f))(g)

    override def extract[A](x: NonEmptyArraySeq[A]): A = x.head
  }

  implicit def catsStdSemigroupForTmmUtilsNonEmptyArraySeq[A]: Semigroup[NonEmptyArraySeq[A]] =
    catsStdInstancesForTmmUtilsNonEmptyArraySeq.algebra[A]

}

trait NonEmptyArraySeqInstances1 {
  implicit def catsStdEqForTmmUtilsNonEmptyArraySeq[A : Eq]: Eq[NonEmptyArraySeq[A]] =
    new Eq[NonEmptyArraySeq[A]] {
      override def eqv(x: NonEmptyArraySeq[A], y: NonEmptyArraySeq[A]): Boolean = Eq.eqv(x.underlying, y.underlying)
    }
}
