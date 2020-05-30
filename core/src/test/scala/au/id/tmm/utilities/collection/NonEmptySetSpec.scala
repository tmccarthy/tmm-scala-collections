package au.id.tmm.utilities.collection

import org.scalatest.FlatSpec

class NonEmptySetSpec extends FlatSpec {

  "A non-empty set" can "be constructed with a single element" in {
    assert(NonEmptySet.one(1).toList === List(1))
  }

  it can "be constructed with multiple elements" in {
    assert(NonEmptySet.of(1, 2, 3).toList === List(1, 2, 3))
  }

  it should "not be equal to another set with a different implementation but same elements" in {
    assert(NonEmptySet.of(1, 2, 3) !== Set(1, 2, 3))
  }

  it can "be constructed from a non-empty set" in {
    assert(NonEmptySet.fromSet(Set(1)) === Some(NonEmptySet.of(1)))
  }

  it can "not be constructed from an empty set" in {
    assert(NonEmptySet.fromSet(Set.empty[Int]) === None)
  }

  it can "be constructed unsafely from a non-empty set" in {
    assert(NonEmptySet.fromSetUnsafe(Set(1)) === NonEmptySet.of(1))
  }

  it can "not be constructed unsafely from an empty set" in {
    intercept[IllegalArgumentException](NonEmptySet.fromSetUnsafe(Set.empty))
  }

  it can "produce subsets" in {
    val set = NonEmptySet.of(1, 2)
    val expectedSubsets: Set[Int] => Boolean = Set(
      Set(),
      Set(1),
      Set(2),
      Set(1, 2),
    )
    assert(set.subsets().toSet === expectedSubsets)
  }

  it should "have a sensible toString" in {
    assert(NonEmptySet.of(1, 2).toString === "NonEmptySet(1, 2)")
  }

  it can "be constructed from a non-empty iterable" in {
    assert(NonEmptySet.fromIterable(List(1)) === Some(NonEmptySet.of(1)))
  }

  it can "be constructed from a non-empty iterable which is a Set" in {
    assert(NonEmptySet.fromIterable(Set(1)) === Some(NonEmptySet.of(1)))
  }

  it can "not be constructed from an empty iterable" in {
    assert(NonEmptySet.fromIterable(List.empty[Int]) === None)
  }

  it can "be constructed unsafely from a non-empty iterable" in {
    assert(NonEmptySet.fromIterableUnsafe(List(1)) === NonEmptySet.of(1))
  }

  it can "not be constructed unsafely from an empty iterable" in {
    intercept[IllegalArgumentException](NonEmptySet.fromIterableUnsafe(List.empty))
  }

  it can "be constructed from cons" in {
    assert(NonEmptySet.fromCons(::(1, Nil)) === NonEmptySet.of(1))
  }

  it can "map" in {
    assert(NonEmptySet.of("hello", "world").map(_.length) === NonEmptySet.of(5))
  }

  it can "flatMap" in {
    assert(NonEmptySet.of("hello", "world").flatMap(s => NonEmptySet.of(s.length)) === NonEmptySet.of(5))
  }

  it can "be flattened" in {
    val nested: NonEmptySet[NonEmptySet[Int]] = NonEmptySet.of(
      NonEmptySet.of(1, 2),
      NonEmptySet.of(3),
    )

    assert(nested.flatten === NonEmptySet.of(1, 2, 3))
  }

}
