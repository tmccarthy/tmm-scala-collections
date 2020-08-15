package au.id.tmm.collections.classes

import au.id.tmm.collections.{NonEmptyDupelessSeq, NonEmptySet}
import au.id.tmm.collections.syntax.toSafeGroupByOps
import org.scalatest.flatspec.AnyFlatSpec

class SafeGroupBySpec extends AnyFlatSpec {

  "safe group by for a Set" should "work" in {
    val set = Set(
      "apple",
      "apricot",
      "banana",
    )

    val expectedGrouped = Map(
      'a' -> NonEmptySet.of("apple", "apricot"),
      'b' -> NonEmptySet.of("banana"),
    )

    assert(set.safeGroupBy(_.head) === expectedGrouped)
  }

  "safe group by for a NonEmptyDupelessSeq" should "work" in {
    val nonEmptyDupelessSeq = NonEmptyDupelessSeq.of(
      "apple",
      "apricot",
      "banana",
    )

    val expectedGrouped = Map(
      'a' -> NonEmptyDupelessSeq.of("apple", "apricot"),
      'b' -> NonEmptyDupelessSeq.of("banana"),
    )

    assert(nonEmptyDupelessSeq.safeGroupBy(_.head) === expectedGrouped)
  }

  "safe group by for a NonEmptySet" should "work" in {
    val nonEmptySet = NonEmptySet.of(
      "apple",
      "apricot",
      "banana",
    )

    val expectedGrouped = Map(
      'a' -> NonEmptySet.of("apple", "apricot"),
      'b' -> NonEmptySet.of("banana"),
    )

    assert(nonEmptySet.safeGroupBy(_.head) === expectedGrouped)
  }

}
