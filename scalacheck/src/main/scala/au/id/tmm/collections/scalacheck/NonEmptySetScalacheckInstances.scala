package au.id.tmm.collections.scalacheck

import au.id.tmm.collections.NonEmptySet
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalacheck.util.Buildable

import scala.collection.mutable

trait NonEmptySetScalacheckInstances {

  implicit def nonEmptySetScalacheckBuildable[A]: Buildable[A, NonEmptySet[A]] =
    new Buildable[A, NonEmptySet[A]] {
      override def builder: mutable.Builder[A, NonEmptySet[A]] = NonEmptySet.unsafeBuilder
    }

  implicit def nonEmptySetScalacheckArbitrary[A : Arbitrary]: Arbitrary[NonEmptySet[A]] =
    Arbitrary(
      Gen.nonEmptyBuildableOf[NonEmptySet[A], A](Arbitrary.arbitrary[A])(
        implicitly[Buildable[A, NonEmptySet[A]]],
        _.underlying,
      ),
    )

  implicit def nonEmptySetScalacheckCogen[A : Cogen]: Cogen[NonEmptySet[A]] =
    Cogen.it[NonEmptySet[A], A](_.iterator)

}
