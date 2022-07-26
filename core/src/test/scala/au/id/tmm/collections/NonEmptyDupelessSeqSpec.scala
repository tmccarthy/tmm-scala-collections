package au.id.tmm.collections

import munit.FunSuite

import scala.collection.immutable.ArraySeq

class NonEmptyDupelessSeqSpec extends FunSuite {

  test("a non-empty dupelessSeq be flattened") {
    val neds: NonEmptyDupelessSeq[NonEmptyDupelessSeq[Int]] = NonEmptyDupelessSeq.of(
      NonEmptyDupelessSeq.of(1),
      NonEmptyDupelessSeq.of(2, 3),
    )

    assertEquals(neds.flatten, NonEmptyDupelessSeq.of(1, 2, 3))
  }

  test("a non-empty dupelessSeq be flatMapped to another non-empty dupelessSeq") {
    val neds: NonEmptyDupelessSeq[NonEmptyDupelessSeq[Int]] = NonEmptyDupelessSeq.of(
      NonEmptyDupelessSeq.of(1),
      NonEmptyDupelessSeq.of(2, 3),
    )

    assertEquals(neds.flatMap(identity(_)), NonEmptyDupelessSeq.of(1, 2, 3))
  }

  test("a non-empty dupelessSeq be flatMapped to a possibly empty collection") {
    val neds: NonEmptyDupelessSeq[List[Int]] = NonEmptyDupelessSeq.of(
      List(1),
      List(2, 3),
      List(),
    )

    assertEquals(neds.flatMap(identity(_)), DupelessSeq(1, 2, 3))
  }

  test("a non-empty dupelessSeq be converted to an ArraySeq") {
    assertEquals(NonEmptyDupelessSeq.of(1, 2, 3).to(ArraySeq.untagged), ArraySeq.untagged(1, 2, 3))
  }

  test("a non-empty dupelessSeq be converted to a NonEmptySet") {
    assertEquals(NonEmptyDupelessSeq.of(1, 2, 3).toNonEmptySet, NonEmptySet.of(1, 2, 3))
  }

  test("a non-empty dupelessSeq be grouped") {
    assertEquals(
      NonEmptyDupelessSeq.of("hello", "world").groupBy(_.head),
      Map(
        'h' -> NonEmptyDupelessSeq.of("hello"),
        'w' -> NonEmptyDupelessSeq.of("world"),
      ),
    )
  }

}
