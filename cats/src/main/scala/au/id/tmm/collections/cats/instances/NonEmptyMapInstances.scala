package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptyMap
import cats.arrow.Compose
import cats.data.Ior
import cats.{
  Align,
  CommutativeApplicative,
  FlatMap,
  Functor,
  FunctorFilter,
  Semigroup,
  SemigroupK,
  Show,
  UnorderedTraverse,
}
import cats.kernel.{CommutativeMonoid, CommutativeSemigroup, Eq, Hash}

trait NonEmptyMapInstances extends NonEmptyMapInstances1 {

  implicit def tmmCollectionsHashForNonEmptyMap[K, V : Hash]: Hash[NonEmptyMap[K, V]] =
    new Hash[NonEmptyMap[K, V]] {
      override def hash(x: NonEmptyMap[K, V]): Int                          = ???
      override def eqv(x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): Boolean = ???
    }

  implicit def show[K : Show, V : Show]: Show[NonEmptyMap[K, V]] =
    new Show[NonEmptyMap[K, V]] {
      override def show(t: NonEmptyMap[K, V]): String = ???
    }

  implicit def tmmCollectionsStdInstancesForNonEmptyMap[
    K,
  ]: UnorderedTraverse[NonEmptyMap[K, *]] with FlatMap[NonEmptyMap[K, *]] with Align[NonEmptyMap[K, *]] =
    new UnorderedTraverse[NonEmptyMap[K, *]] with FlatMap[NonEmptyMap[K, *]] with Align[NonEmptyMap[K, *]] {
      override def unorderedTraverse[G[_], A, B](sa: NonEmptyMap[K, A])(f: A => G[B])(implicit evidence$1: CommutativeApplicative[G]): G[NonEmptyMap[K, B]] = ???

      override def flatMap[A, B](fa: NonEmptyMap[K, A])(f: A => NonEmptyMap[K, B]): NonEmptyMap[K, B] = ???

      override def tailRecM[A, B](a: A)(f: A => NonEmptyMap[K, Either[A, B]]): NonEmptyMap[K, B] = ???

      override def functor: Functor[NonEmptyMap[K, *]] = ???

      override def align[A, B](fa: NonEmptyMap[K, A], fb: NonEmptyMap[K, B]): NonEmptyMap[K, Ior[A, B]] = ???

      override def unorderedFoldMap[A, B](fa: NonEmptyMap[K, A])(f: A => B)(implicit evidence$1: CommutativeMonoid[B]): B = ???

      override def map[A, B](fa: NonEmptyMap[K, A])(f: A => B): NonEmptyMap[K, B] = ???
    }

  implicit val tmmCollectionsComposeForNonEmptyMap: Compose[NonEmptyMap] = new Compose[NonEmptyMap] {
    override def compose[A, B, C](f: NonEmptyMap[B, C], g: NonEmptyMap[A, B]): NonEmptyMap[A, C] = ???
  }

  implicit def tmmCollectionsFunctorFilterForNonEmptyMap[K]: FunctorFilter[NonEmptyMap[K, *]] =
    new FunctorFilter[NonEmptyMap[K, *]] {
      override def functor: Functor[NonEmptyMap[K, *]] = ???
      override def mapFilter[V1, V2](fa: NonEmptyMap[K, V1])(f: V1 => Option[V2]): NonEmptyMap[K, V2] = ???
    }

  implicit def tmmCollectionsSemigroupKForNonEmptyMap[K]: SemigroupK[NonEmptyMap[K, *]] =
    new SemigroupK[NonEmptyMap[K, *]] {
      override def combineK[V](x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): NonEmptyMap[K, V] = ???
      override def algebra[V]: Semigroup[NonEmptyMap[K, V]] = ???
    }

  implicit def tmmCollectionsCommutativeSemigroupForNonEmptyMap[
    K,
    V : CommutativeSemigroup,
  ]: CommutativeSemigroup[NonEmptyMap[K, V]] =
    new CommutativeSemigroup[NonEmptyMap[K, V]] {
      override def combine(x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): NonEmptyMap[K, V] = ???
    }

}

private[instances] trait NonEmptyMapInstances1 extends NonEmptyMapInstances2 {

  implicit def tmmCollectionsEqForNonEmptyMap[K, V : Eq]: Eq[NonEmptyMap[K, V]] =
    new Eq[NonEmptyMap[K, V]] {
      override def eqv(x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): Boolean = ???
    }

  implicit def tmmCollectionsSemigroupForNonEmptyMap[K, V : Semigroup]: Semigroup[NonEmptyMap[K, V]] =
    new Semigroup[NonEmptyMap[K, V]] {
      override def combine(x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): NonEmptyMap[K, V] = ???
    }

}

private[instances] trait NonEmptyMapInstances2 {

  implicit def tmmCollectionsFunctorOverKeysForNonEmptyMap[K, V : Semigroup]: Semigroup[NonEmptyMap[K, V]] =
    new Semigroup[NonEmptyMap[K, V]] {
      override def combine(x: NonEmptyMap[K, V], y: NonEmptyMap[K, V]): NonEmptyMap[K, V] = ???
    }

}
