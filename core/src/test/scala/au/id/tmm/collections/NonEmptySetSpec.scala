package au.id.tmm.collections

import munit.FunSuite

class NonEmptySetSpec extends FunSuite {

  test("A non-empty set be constructed with a single element") {
    assertEquals(NonEmptySet.one(1).toList, List(1))
  }

  test("A non-empty set be constructed with multiple elements") {
    assertEquals(NonEmptySet.of(1, 2, 3).toList, List(1, 2, 3))
  }

  test("A non-empty set not be equal to another set with a different implementation but same elements") {
    assertEquals(NonEmptySet.of(1, 2, 3): IterableOnce[Int], Set(1, 2, 3))
  }

  test("A non-empty set be constructed from a non-empty set") {
    assertEquals(NonEmptySet.fromSet(Set(1)), Some(NonEmptySet.of(1)))
  }

  test("A non-empty set not be constructed from an empty set") {
    assertEquals(NonEmptySet.fromSet(Set.empty[Int]), None)
  }

  test("A non-empty set be constructed unsafely from a non-empty set") {
    assertEquals(NonEmptySet.fromSetUnsafe(Set(1)), NonEmptySet.of(1))
  }

  test("A non-empty set not be constructed unsafely from an empty set") {
    intercept[IllegalArgumentException](NonEmptySet.fromSetUnsafe(Set.empty))
  }

  test("A non-empty set produce subsets") {
    val set = NonEmptySet.of(1, 2)
    val expectedSubsets: Set[Set[Int]] = Set(
      Set(),
      Set(1),
      Set(2),
      Set(1, 2),
    )
    assertEquals(set.subsets().toSet, expectedSubsets)
  }

  test("A non-empty set have a sensible toString") {
    assertEquals(NonEmptySet.of(1, 2).toString, "NonEmptySet(1, 2)")
  }

  test("A non-empty set be constructed from a non-empty iterable") {
    assertEquals(NonEmptySet.fromIterable(List(1)), Some(NonEmptySet.of(1)))
  }

  test("A non-empty set be constructed from a non-empty iterable which is a Set") {
    assertEquals(NonEmptySet.fromIterable(Set(1)), Some(NonEmptySet.of(1)))
  }

  test("A non-empty set not be constructed from an empty iterable") {
    assertEquals(NonEmptySet.fromIterable(List.empty[Int]), None)
  }

  test("A non-empty set be constructed unsafely from a non-empty iterable") {
    assertEquals(NonEmptySet.fromIterableUnsafe(List(1)), NonEmptySet.of(1))
  }

  test("A non-empty set not be constructed unsafely from an empty iterable") {
    intercept[IllegalArgumentException](NonEmptySet.fromIterableUnsafe(List.empty))
  }

  test("A non-empty set be constructed from cons") {
    assertEquals(NonEmptySet.fromCons(::(1, Nil)), NonEmptySet.of(1))
  }

  test("A non-empty set map") {
    assertEquals(NonEmptySet.of("hello", "world").map(_.length), NonEmptySet.of(5))
  }

  test("A non-empty set flatMap") {
    assertEquals(NonEmptySet.of("hello", "world").flatMap(s => NonEmptySet.of(s.length)), NonEmptySet.of(5))
  }

  test("A non-empty set be flattened") {
    val nested: NonEmptySet[NonEmptySet[Int]] = NonEmptySet.of(
      NonEmptySet.of(1, 2),
      NonEmptySet.of(3),
    )

    assertEquals(nested.flatten, NonEmptySet.of(1, 2, 3))
  }

}
