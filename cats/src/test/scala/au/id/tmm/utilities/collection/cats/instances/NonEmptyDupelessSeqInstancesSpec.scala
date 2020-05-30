package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.cats.instances.nonEmptyDupelessSeq._
import au.id.tmm.utilities.collection.NonEmptyDupelessSeq
import cats.kernel.Semigroup
import org.scalatest.flatspec.AnyFlatSpec
import cats.syntax.show.toShow

class NonEmptyDupelessSeqInstancesSpec extends AnyFlatSpec {

  "the semigroup instance for NonEmptyDupelessSeq" can "be found with a simple import" in {
    val combined = Semigroup[NonEmptyDupelessSeq[Int]].combine(NonEmptyDupelessSeq.of(1), NonEmptyDupelessSeq.of(2))

    assert(combined === NonEmptyDupelessSeq.of(1, 2))
  }

  "the show instance for NonEmptyDupelessSeq" should "produce a sensible string" in {
    assert(NonEmptyDupelessSeq.of(1, 2).show === "NonEmptyDupelessSeq(1, 2)")
  }

}
