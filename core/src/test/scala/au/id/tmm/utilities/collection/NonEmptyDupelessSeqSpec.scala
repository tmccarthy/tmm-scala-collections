package au.id.tmm.utilities.collection

import org.scalatest.FlatSpec

import scala.collection.immutable.ArraySeq

class NonEmptyDupelessSeqSpec extends FlatSpec {

  "a non-empty dupelessSeq" can "be flattened" in {
    val neds: NonEmptyDupelessSeq[NonEmptyDupelessSeq[Int]] = NonEmptyDupelessSeq.of(
      NonEmptyDupelessSeq.of(1),
      NonEmptyDupelessSeq.of(2, 3),
    )

    assert(neds.flatten === NonEmptyDupelessSeq.of(1, 2, 3))
  }

  it can "be flatMapped to another non-empty dupelessSeq" in {
    val neds: NonEmptyDupelessSeq[NonEmptyDupelessSeq[Int]] = NonEmptyDupelessSeq.of(
      NonEmptyDupelessSeq.of(1),
      NonEmptyDupelessSeq.of(2, 3),
    )

    assert(neds.flatMap(identity(_)) === NonEmptyDupelessSeq.of(1, 2, 3))
  }

  it can "be flatMapped to a possibly empty collection" in {
    val neds: NonEmptyDupelessSeq[List[Int]] = NonEmptyDupelessSeq.of(
      List(1),
      List(2, 3),
      List(),
    )

    assert(neds.flatMap(identity(_)) === DupelessSeq(1, 2, 3))
  }

  it can "be converted to an ArraySeq" in {
    assert(NonEmptyDupelessSeq.of(1, 2, 3).to(ArraySeq.untagged) === ArraySeq.untagged(1, 2, 3))
  }

  it can "be converted to a NonEmptySet" in {
    assert(NonEmptyDupelessSeq.of(1, 2, 3).toNonEmptySet === NonEmptySet.of(1, 2, 3))
  }

}
