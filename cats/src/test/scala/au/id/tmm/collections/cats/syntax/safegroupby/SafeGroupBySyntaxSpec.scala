package au.id.tmm.collections.cats.syntax.safegroupby

import au.id.tmm.collections.NonEmptySet
import au.id.tmm.collections.cats.instances.list._
import au.id.tmm.collections.syntax.toSafeGroupByOps
import cats.data.NonEmptyList
import munit.FunSuite

class SafeGroupBySyntaxSpec extends FunSuite {

  test("safe group by for Set should work") {
    val groupedBy = Set("apple", "apricot", "banana").safeGroupBy(_.head)

    val expected = Map(
      'a' -> NonEmptySet.of("apple", "apricot"),
      'b' -> NonEmptySet.of("banana"),
    )

    assertEquals(groupedBy, expected)
  }

  test("safe group by for Set should work for groupMap") {
    val groupedBy = Set("apple", "apricot", "banana").safeGroupMap(_.head)(_.toUpperCase)

    val expected = Map(
      'a' -> NonEmptySet.of("APPLE", "APRICOT"),
      'b' -> NonEmptySet.of("BANANA"),
    )

    assertEquals(groupedBy, expected)
  }

  test("safe group by for Set should work for the key value") {
    val set = Set(
      (1, "hello"),
      (1, "world"),
      (2, "testing"),
    )

    val expected = Map(
      1 -> NonEmptySet.of("hello", "world"),
      2 -> NonEmptySet.of("testing"),
    )

    assertEquals(set.safeGroupByKey, expected)
  }

  test("safe group by for List should work for a list of tuples") {
    val l = List(
      "a" -> 1,
      "a" -> 2,
      "b" -> 3,
    )

    val expectedGrouped = Map(
      "a" -> NonEmptyList.of(1, 2),
      "b" -> NonEmptyList.of(3),
    )

    assertEquals(l.safeGroupByKey, expectedGrouped)
  }

  test("safe group by for List should work for an empty list") {
    assertEquals(List.empty[Int].safeGroupBy(_ => ()), Map.empty[Unit, NonEmptyList[Int]])
  }

}
