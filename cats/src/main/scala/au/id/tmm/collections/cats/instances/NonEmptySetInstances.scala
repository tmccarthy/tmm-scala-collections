package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptySet
import cats.kernel.{CommutativeMonoid, Eq, Semilattice}
import cats.syntax.functor.toFunctorOps
import cats.{CommutativeApplicative, Hash, SemigroupK, Show, UnorderedTraverse}

trait NonEmptySetInstances extends NonEmptySetInstances1 {

  implicit def catsStdHashForTmmUtilsNonEmptySet[A : Hash]: Hash[NonEmptySet[A]] =
    new Hash[NonEmptySet[A]] {
      override def hash(x: NonEmptySet[A]): Int                       = Hash[Set[A]].hash(x.underlying)
      override def eqv(x: NonEmptySet[A], y: NonEmptySet[A]): Boolean = Eq[Set[A]].eqv(x.underlying, y.underlying)
    }

  implicit def catsStdShowForTmmUtilsNonEmptySet[A : Show]: Show[NonEmptySet[A]] =
    s => s.iterator.map(Show[A].show).mkString("NonEmptySet(", ", ", ")")

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
