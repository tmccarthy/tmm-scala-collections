package au.id.tmm.collections.cats.instances.unlawful

import cats.kernel.instances.MapMonoid
import cats.kernel.{CommutativeGroup, Group}

trait MapUnlawfulInstances extends LowPriorityMapUnlawfulInstances {
  implicit def tmmUtilsUnlawfulCommutativeGroupForMap[K, V : CommutativeGroup]: CommutativeGroup[Map[K, V]] =
    new MapGroup[K, V] with CommutativeGroup[Map[K, V]]
}

private[instances] trait LowPriorityMapUnlawfulInstances {
  implicit def tmmUtilsUnlawfulGroupForMap[K, V : Group]: Group[Map[K, V]] = new MapGroup[K, V]
}

private class MapGroup[K, V : Group] extends MapMonoid[K, V] with Group[Map[K, V]] {
  override def inverse(a: Map[K, V]): Map[K, V] = a.view.mapValues(Group[V].inverse).toMap
}
