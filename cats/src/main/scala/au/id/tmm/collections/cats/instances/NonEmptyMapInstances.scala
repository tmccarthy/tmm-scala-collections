package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptyMap
import cats.data.Ior
import cats.instances.map._
import cats.kernel.{CommutativeMonoid, CommutativeSemigroup, Eq, Hash}
import cats.{Align, CommutativeApplicative, FlatMap, Functor, FunctorFilter, Semigroup, Show, UnorderedTraverse}

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
  ]: UnorderedTraverse[NonEmptyMap[K, *]] with FlatMap[NonEmptyMap[K, *]] with Align[NonEmptyMap[K, *]] =
    new UnorderedTraverse[NonEmptyMap[K, *]] with FlatMap[NonEmptyMap[K, *]] with Align[NonEmptyMap[K, *]] {
      override def unorderedTraverse[G[_], A, B](
        sa: NonEmptyMap[K, A],
      )(
        f: A => G[B],
      )(implicit
        evidence$1: CommutativeApplicative[G],
      ): G[NonEmptyMap[K, B]] = ???

      override def flatMap[A, B](fa: NonEmptyMap[K, A])(f: A => NonEmptyMap[K, B]): NonEmptyMap[K, B] =
        fa.flatMap {
          case (k, a) => f(a)
        }

      override def tailRecM[A, B](a: A)(f: A => NonEmptyMap[K, Either[A, B]]): NonEmptyMap[K, B] =
        NonEmptyMap.fromMapUnsafe(FlatMap[Map[K, *]].tailRecM(a)(f.andThen(_.underlying)))

      override def functor: Functor[NonEmptyMap[K, *]] = this

      override def align[A, B](fa: NonEmptyMap[K, A], fb: NonEmptyMap[K, B]): NonEmptyMap[K, Ior[A, B]] = ???

      override def unorderedFoldMap[A, B](
        fa: NonEmptyMap[K, A],
      )(
        f: A => B,
      )(implicit
        evidence$1: CommutativeMonoid[B],
      ): B = ???

      override def map[A, B](fa: NonEmptyMap[K, A])(f: A => B): NonEmptyMap[K, B] =
        fa.map {
          case (k, a) => k -> f(a)
        }
    }

  implicit def tmmCollectionsFunctorFilterForNonEmptyMap[K]: FunctorFilter[NonEmptyMap[K, *]] =
    new FunctorFilter[NonEmptyMap[K, *]] {
      override def functor: Functor[NonEmptyMap[K, *]] = tmmCollectionsStdInstancesForNonEmptyMap

      override def mapFilter[V1, V2](fa: NonEmptyMap[K, V1])(f: V1 => Option[V2]): NonEmptyMap[K, V2] = ???
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

  implicit def tmmCollectionsFunctorOverKeysForNonEmptyMap[V : Semigroup]: Functor[NonEmptyMap[*, V]] =
    new Functor[NonEmptyMap[*, V]] {

      import au.id.tmm.collections.cats.instances.mapOverKeys._

      override def map[A, B](fa: NonEmptyMap[A, V])(f: A => B): NonEmptyMap[B, V] =
        NonEmptyMap.fromMapUnsafe(Functor[Map[*, V]].map(fa.underlying)(f))
    }

}
