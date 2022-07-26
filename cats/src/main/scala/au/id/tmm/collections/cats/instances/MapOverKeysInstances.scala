package au.id.tmm.collections.cats.instances

import cats.instances.map.catsKernelStdMonoidForMap
import cats.kernel.{Monoid, Semigroup}
import cats.{Functor, MonoidK}

trait MapOverKeysInstances {

  implicit def tmmUtilsFunctorForMapOverKeys[V : Semigroup]: Functor[Map[*, V]] =
    new Functor[Map[*, V]] {
      override def map[K1, K2](fa: Map[K1, V])(f: K1 => K2): Map[K2, V] =
        fa.groupMapReduce[K2, V](
          key = {
            case (k1, _) => f(k1): K2
          },
        )(
          f = {
            case (_, v) => v
          },
        )(
          reduce = Semigroup[V].combine,
        )
    }

  implicit def tmmUtilsMonoidKForMapOverKeys[V : Semigroup]: MonoidK[Map[*, V]] =
    new MonoidK[Map[*, V]] {
      override def empty[K]: Map[K, V]                                = catsKernelStdMonoidForMap[K, V].empty
      override def combineK[K](x: Map[K, V], y: Map[K, V]): Map[K, V] = catsKernelStdMonoidForMap[K, V].combine(x, y)
      override def algebra[K]: Monoid[Map[K, V]]                      = catsKernelStdMonoidForMap
    }

}
