package au.id.tmm.collections.scalacheck

import au.id.tmm.collections.NonEmptyMap
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalacheck.util.Buildable

import scala.collection.mutable

trait NonEmptyMapScalacheckInstances {

  implicit def nonEmptySetScalacheckBuildable[K, V]: Buildable[(K, V), NonEmptyMap[K, V]] =
    new Buildable[(K, V), NonEmptyMap[K, V]] {
      override def builder: mutable.Builder[(K, V), NonEmptyMap[K, V]] = NonEmptyMap.unsafeBuilder
    }

  implicit def nonEmptySetScalacheckArbitrary[K : Arbitrary, V : Arbitrary]: Arbitrary[NonEmptyMap[K, V]] =
    Arbitrary(
      Gen.nonEmptyBuildableOf[NonEmptyMap[K, V], (K, V)](Arbitrary.arbitrary[(K, V)])(
        implicitly[Buildable[(K, V), NonEmptyMap[K, V]]],
        _.underlying,
      ),
    )

  implicit def nonEmptySetScalacheckCogen[K : Cogen, V : Cogen]: Cogen[NonEmptyMap[K, V]] =
    Cogen.it[NonEmptyMap[K, V], (K, V)](_.iterator)


}
