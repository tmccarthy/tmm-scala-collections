package au.id.tmm.collections.classes

import au.id.tmm.collections.syntax.toSafeGroupByOps
import au.id.tmm.collections.{NonEmptyDupelessSeq, NonEmptySet}
import munit.FunSuite

class SafeGroupBySpec extends FunSuite {

  test("safe group by for a Set work") {
    val set = Set(
      "apple",
      "apricot",
      "banana",
    )

    val expectedGrouped = Map(
      'a' -> NonEmptySet.of("apple", "apricot"),
      'b' -> NonEmptySet.of("banana"),
    )

    assertEquals(set.safeGroupBy(_.head), expectedGrouped)
  }

  test("safe group by for a NonEmptyDupelessSeq work") {
    val nonEmptyDupelessSeq = NonEmptyDupelessSeq.of(
      "apple",
      "apricot",
      "banana",
    )

    val expectedGrouped = Map(
      'a' -> NonEmptyDupelessSeq.of("apple", "apricot"),
      'b' -> NonEmptyDupelessSeq.of("banana"),
    )

    assertEquals(nonEmptyDupelessSeq.safeGroupBy(_.head), expectedGrouped)
  }

  test("safe group by for a NonEmptySet work") {
    val nonEmptySet = NonEmptySet.of(
      "apple",
      "apricot",
      "banana",
    )

    val expectedGrouped = Map(
      'a' -> NonEmptySet.of("apple", "apricot"),
      'b' -> NonEmptySet.of("banana"),
    )

    assertEquals(nonEmptySet.safeGroupBy(_.head), expectedGrouped)
  }

}
