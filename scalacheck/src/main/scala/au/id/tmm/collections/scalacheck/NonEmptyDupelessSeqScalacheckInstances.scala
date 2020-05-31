package au.id.tmm.collections.scalacheck

import au.id.tmm.collections.NonEmptyDupelessSeq
import org.scalacheck.util.Buildable
import org.scalacheck.{Arbitrary, Cogen, Gen}

import scala.collection.mutable

trait NonEmptyDupelessSeqScalacheckInstances {

  implicit def nonEmptyDupelessSeqScalacheckBuildable[A]: Buildable[A, NonEmptyDupelessSeq[A]] =
    new Buildable[A, NonEmptyDupelessSeq[A]] {
      override def builder: mutable.Builder[A, NonEmptyDupelessSeq[A]] = NonEmptyDupelessSeq.unsafeBuilder
    }

  implicit def nonEmptyDupelessSeqScalacheckArbitrary[A : Arbitrary]: Arbitrary[NonEmptyDupelessSeq[A]] =
    Arbitrary(
      Gen.nonEmptyBuildableOf[NonEmptyDupelessSeq[A], A](Arbitrary.arbitrary[A])(
        implicitly[Buildable[A, NonEmptyDupelessSeq[A]]],
        _.underlying,
      ),
    )

  implicit def nonEmptyDupelessSeqScalacheckCogen[A : Cogen]: Cogen[NonEmptyDupelessSeq[A]] =
    Cogen.it[NonEmptyDupelessSeq[A], A](_.iterator)

}
