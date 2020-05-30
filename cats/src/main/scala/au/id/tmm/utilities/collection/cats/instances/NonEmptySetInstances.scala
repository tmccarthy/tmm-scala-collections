package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptySet
import cats.kernel.{CommutativeMonoid, Eq, Semilattice}
import cats.syntax.functor.toFunctorOps
import cats.{Apply, CommutativeApplicative, Eval, Hash, Monad, NonEmptyTraverse, SemigroupK, Show, UnorderedTraverse}

import scala.collection.mutable

trait NonEmptySetInstances extends NonEmptySetInstances1 {

  implicit def catsStdHashForTmmUtilsNonEmptySet[A : Hash]: Hash[NonEmptySet[A]] = new Hash[NonEmptySet[A]] {
    override def hash(x: NonEmptySet[A]): Int                       = Hash[Set[A]].hash(x.underlying)
    override def eqv(x: NonEmptySet[A], y: NonEmptySet[A]): Boolean = Eq[Set[A]].eqv(x.underlying, y.underlying)
  }

  implicit def catsStdShowForTmmUtilsNonEmptySet[A : Show]: Show[NonEmptySet[A]] =
    s => s.iterator.map(Show[A].show).mkString("NonEmptySet(", ", ", ")")

  object unlawful {

    implicit val catsUnlawfulInstancesForTmmUtilsNonEmptySet: NonEmptyTraverse[NonEmptySet] with Monad[NonEmptySet] = {
      new NonEmptyTraverse[NonEmptySet] with Monad[NonEmptySet] {

        override def nonEmptyTraverse[G[_], A, B](
          fa: NonEmptySet[A],
        )(
          f: A => G[B],
        )(implicit
          G: Apply[G],
        ): G[NonEmptySet[B]] =
          reduceRightTo[A, G[NonEmptySet[B]]](fa)(a => G.map[B, NonEmptySet[B]](f(a))(NonEmptySet.one)) {
            case (a, evalGNesB) => {
              G.map2Eval[B, NonEmptySet[B], NonEmptySet[B]](f(a), evalGNesB) {
                case (b, nesB) => nesB.incl(b)
              }
            }
          }.value

        override def reduceLeftTo[A, B](fa: NonEmptySet[A])(f: A => B)(g: (B, A) => B): B =
          fa.tail.foldRight[B](f(fa.head)) {
            case (a, b) => g(b, a)
          }

        override def reduceRightTo[A, B](fa: NonEmptySet[A])(f: A => B)(g: (A, Eval[B]) => Eval[B]): Eval[B] =
          fa.init.foldRight[Eval[B]](Eval.now[A](fa.last).map(f))(g)

        override def foldLeft[A, B](fa: NonEmptySet[A], b: B)(f: (B, A) => B): B =
          fa.foldLeft(b)(f)

        override def foldRight[A, B](fa: NonEmptySet[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
          fa.foldRight(lb)(f)

        override def flatMap[A, B](fa: NonEmptySet[A])(f: A => NonEmptySet[B]): NonEmptySet[B] =
          fa.flatMap(f)

        override def tailRecM[A, B](a: A)(f: A => NonEmptySet[Either[A, B]]): NonEmptySet[B] = {
          val resultBuilder            = Set.newBuilder[B]
          val aQueue: mutable.Queue[A] = mutable.Queue(a)

          while (aQueue.nonEmpty) {
            f(aQueue.dequeue()).foreach {
              case Right(b) => resultBuilder.addOne(b)
              case Left(a)  => aQueue.addOne(a)
            }
          }

          NonEmptySet.fromSetUnsafe(resultBuilder.result())
        }

        override def pure[A](x: A): NonEmptySet[A] = NonEmptySet.one(x)
      }
    }
  }

}

private[instances] trait NonEmptySetInstances1 {

  implicit def catsStdSemilatticeForNonEmptySet[A]: Semilattice[NonEmptySet[A]] = (x, y) => x concat y

  implicit val catsStdInstancesForNonEmptySet: SemigroupK[NonEmptySet] with UnorderedTraverse[NonEmptySet] =
    new SemigroupK[NonEmptySet] with UnorderedTraverse[NonEmptySet] {
      override def combineK[A](x: NonEmptySet[A], y: NonEmptySet[A]): NonEmptySet[A] = x concat y

      override def unorderedTraverse[G[_], A, B](
        sa: NonEmptySet[A],
      )(
        f: A => G[B],
      )(implicit
        evidence$1: CommutativeApplicative[G],
      ): G[NonEmptySet[B]] =
        UnorderedTraverse[Set].unorderedTraverse(sa.underlying)(f).map(NonEmptySet.fromSetUnsafe)

      override def unorderedFoldMap[A, B](fa: NonEmptySet[A])(f: A => B)(implicit evidence$1: CommutativeMonoid[B]): B =
        UnorderedTraverse[Set].unorderedFoldMap(fa.underlying)(f)
    }

}
