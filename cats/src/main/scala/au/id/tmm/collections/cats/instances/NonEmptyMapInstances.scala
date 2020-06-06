package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptyMap
import cats.data.Ior
import cats.instances.map._
import cats.kernel.{CommutativeMonoid, CommutativeSemigroup, Eq, Hash}
import cats.syntax.functor.toFunctorOps
import cats.{Align, CommutativeApplicative, Functor, MonoidK, Semigroup, SemigroupK, Show, UnorderedTraverse}

trait NonEmptyMapInstances extends NonEmptyMapInstances1 {

  implicit def tmmCollectionsHashForNonEmptyMap[K : Hash, V : Hash]: Hash[NonEmptyMap[K, V]] =
    new Hash[NonEmptyMap[K, V]] {
      override def hash(x: NonEmptyMap[K, V]): Int = Hash[Map[K, V]].hash(x.underlying)

      override def eqv(x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): Boolean =
        Hash[Map[K, V]].eqv(x.underlying, y.underlying)
    }

  implicit def show[K : Show, V : Show]: Show[NonEmptyMap[K, V]] =
    new Show[NonEmptyMap[K, V]] {
      override def show(map: NonEmptyMap[K, V]): String =
        map.iterator
          .map { case (a, b) => Show[K].show(a) + " -> " + Show[V].show(b) }
          .mkString("NonEmptyMap(", ", ", ")")
    }

  implicit def tmmCollectionsStdInstancesForNonEmptyMap[
    K,
  ]: UnorderedTraverse[NonEmptyMap[K, *]] with Functor[NonEmptyMap[K, *]] with Align[NonEmptyMap[K, *]] =
    new UnorderedTraverse[NonEmptyMap[K, *]] with Functor[NonEmptyMap[K, *]] with Align[NonEmptyMap[K, *]] {
      override def unorderedTraverse[G[_], A, B](
        sa: NonEmptyMap[K, A],
      )(
        f: A => G[B],
      )(implicit
        G: CommutativeApplicative[G],
      ): G[NonEmptyMap[K, B]] =
        UnorderedTraverse[Map[K, *]]
          .unorderedTraverse(sa.underlying)(f)
          .map(NonEmptyMap.fromMapUnsafe)

      override def functor: Functor[NonEmptyMap[K, *]] = this

      override def align[A, B](fa: NonEmptyMap[K, A], fb: NonEmptyMap[K, B]): NonEmptyMap[K, Ior[A, B]] =
        NonEmptyMap.fromMapUnsafe(Align[Map[K, *]].align(fa.underlying, fb.underlying))

      override def unorderedFoldMap[A, B](
        fa: NonEmptyMap[K, A],
      )(
        f: A => B,
      )(implicit
        monoidB: CommutativeMonoid[B],
      ): B =
        UnorderedTraverse[Map[K, *]].unorderedFoldMap(fa.underlying)(f)

      override def map[A, B](fa: NonEmptyMap[K, A])(f: A => B): NonEmptyMap[K, B] =
        fa.map {
          case (k, a) => k -> f(a)
        }
    }

  implicit def tmmCollectionsCommutativeSemigroupForNonEmptyMap[
    K,
    V : CommutativeSemigroup,
  ]: CommutativeSemigroup[NonEmptyMap[K, V]] =
    new CommutativeSemigroup[NonEmptyMap[K, V]] {
      override def combine(x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): NonEmptyMap[K, V] =
        tmmCollectionsSemigroupForNonEmptyMap[K, V].combine(x, y)
    }

}

private[instances] trait NonEmptyMapInstances1 extends NonEmptyMapInstances2 {

  implicit def tmmCollectionsEqForNonEmptyMap[K, V : Eq]: Eq[NonEmptyMap[K, V]] =
    new Eq[NonEmptyMap[K, V]] {
      override def eqv(x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): Boolean =
        Eq[Map[K, V]].eqv(x.underlying, y.underlying)
    }

  implicit def tmmCollectionsSemigroupForNonEmptyMap[K, V : Semigroup]: Semigroup[NonEmptyMap[K, V]] =
    new Semigroup[NonEmptyMap[K, V]] {
      override def combine(x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): NonEmptyMap[K, V] =
        NonEmptyMap.fromMapUnsafe(Semigroup[Map[K, V]].combine(x.underlying, y.underlying))

      override def combineAllOption(as: IterableOnce[NonEmptyMap[K, V]]): Option[NonEmptyMap[K, V]] =
        Semigroup[Map[K, V]].combineAllOption(as.iterator.map(_.underlying)).map(NonEmptyMap.fromMapUnsafe)
    }

}

private[instances] trait NonEmptyMapInstances2 {

  import au.id.tmm.collections.cats.instances.mapOverKeys._

  implicit def tmmCollectionsFunctorOverKeysForNonEmptyMap[V : Semigroup]: Functor[NonEmptyMap[*, V]] =
    new Functor[NonEmptyMap[*, V]] {
      override def map[A, B](fa: NonEmptyMap[A, V])(f: A => B): NonEmptyMap[B, V] =
        NonEmptyMap.fromMapUnsafe(Functor[Map[*, V]].map(fa.underlying)(f))
    }

  implicit def tmmCollectionsSemigroupOverKeysForNonEmptyMap[V : Semigroup]: SemigroupK[NonEmptyMap[*, V]] =
    new SemigroupK[NonEmptyMap[*, V]] {
      override def combineK[A](x: NonEmptyMap[A, V], y: NonEmptyMap[A, V]): NonEmptyMap[A, V] =
        NonEmptyMap.fromMapUnsafe(MonoidK[Map[*, V]].combineK(x.underlying, y.underlying))
    }

}
