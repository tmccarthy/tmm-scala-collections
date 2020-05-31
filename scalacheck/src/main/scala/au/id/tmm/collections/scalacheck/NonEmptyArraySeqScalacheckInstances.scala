package au.id.tmm.collections.scalacheck

import au.id.tmm.collections.NonEmptyArraySeq
import org.scalacheck.{Arbitrary, Cogen, Gen}
import org.scalacheck.util.Buildable

import scala.collection.mutable

trait NonEmptyArraySeqScalacheckInstances {

  implicit def nonEmptyArraySeqScalacheckBuildable[A]: Buildable[A, NonEmptyArraySeq[A]] =
    new Buildable[A, NonEmptyArraySeq[A]] {
      override def builder: mutable.Builder[A, NonEmptyArraySeq[A]] = NonEmptyArraySeq.untagged.unsafeBuilder
    }

  implicit def nonEmptyArraySeqScalacheckArbitrary[A : Arbitrary]: Arbitrary[NonEmptyArraySeq[A]] =
    Arbitrary(
      Gen.nonEmptyBuildableOf[NonEmptyArraySeq[A], A](Arbitrary.arbitrary[A])(
        implicitly[Buildable[A, NonEmptyArraySeq[A]]],
        _.underlying,
      ),
    )

  implicit def nonEmptyArraySeqScalacheckCogen[A : Cogen]: Cogen[NonEmptyArraySeq[A]] =
    Cogen.it[NonEmptyArraySeq[A], A](_.iterator)

}
