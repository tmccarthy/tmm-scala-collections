package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptySet
import cats.kernel.{CommutativeMonoid, Eq, Semilattice}
import cats.syntax.functor.toFunctorOps
import cats.{CommutativeApplicative, Hash, SemigroupK, Show, UnorderedTraverse}

trait NonEmptySetInstances extends NonEmptySetInstances1 {

  implicit def tmmUtilsHashForTmmUtilsNonEmptySet[A : Hash]: Hash[NonEmptySet[A]] = Hash.by(_.underlying)

  implicit def tmmUtilsShowForTmmUtilsNonEmptySet[A : Show]: Show[NonEmptySet[A]] =
    s => s.iterator.map(Show[A].show).mkString("NonEmptySet(", ", ", ")")

}

private[instances] trait NonEmptySetInstances1 {

  implicit def tmmUtilsEqForTmmUtilsNonEmptySet[A : Eq]: Eq[NonEmptySet[A]] = Eq.by(_.underlying)

  implicit def tmmUtilsSemilatticeForTmmUtilsNonEmptySet[A]: Semilattice[NonEmptySet[A]] = (x, y) => x concat y

  implicit val tmmUtilsInstancesForTmmUtilsNonEmptySet: SemigroupK[NonEmptySet] with UnorderedTraverse[NonEmptySet] =
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
