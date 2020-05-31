package au.id.tmm.collections.scalacheck

import au.id.tmm.collections.DupelessSeq
import org.scalacheck.util.Buildable
import org.scalacheck.{Arbitrary, Cogen, Gen}

import scala.collection.mutable

trait DupelessSeqScalacheckInstances {

  implicit def dupelessSeqScalacheckBuildable[A]: Buildable[A, DupelessSeq[A]] =
    new Buildable[A, DupelessSeq[A]] {
      override def builder: mutable.Builder[A, DupelessSeq[A]] = DupelessSeq.newBuilder
    }

  implicit def dupelessSeqScalacheckArbitrary[A : Arbitrary]: Arbitrary[DupelessSeq[A]] =
    Arbitrary(
      Gen.buildableOf[DupelessSeq[A], A](Arbitrary.arbitrary[A]),
    )

  implicit def dupelessSeqScalacheckCogen[A : Cogen]: Cogen[DupelessSeq[A]] = Cogen.it[DupelessSeq[A], A](_.iterator)

}
