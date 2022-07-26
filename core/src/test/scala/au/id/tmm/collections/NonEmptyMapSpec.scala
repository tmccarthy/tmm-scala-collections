package au.id.tmm.collections

import org.scalatest.flatspec.AnyFlatSpec

class NonEmptyMapSpec extends AnyFlatSpec {

  "a non-empty map" can "be flatMapped with a possibly empty collection of tuples" in {
    val base = NonEmptyMap.of(
      "apple"  -> 1,
      "banana" -> 2,
    )

    val actual = base.flatMap {
      case (name, count) =>
        List(
          s"${name}_1" -> count,
          s"${name}_2" -> (count + 10),
        )
    }

    val expected = Map(
      "apple_1"  -> 1,
      "apple_2"  -> 11,
      "banana_1" -> 2,
      "banana_2" -> 12,
    )

    assert(actual === expected)
  }

  it can "be flatMapped with a possibly empty collection of single elements" in {
    val base = NonEmptyMap.of(
      "apple"  -> 1,
      "banana" -> 2,
    )

    val actual = base.flatMap {
      case (name, _) =>
        List(
          s"${name}_1",
          s"${name}_2",
        )
    }

    val expected = List(
      "apple_1",
      "apple_2",
      "banana_1",
      "banana_2",
    )

    assert(actual.toList === expected)
  }

  it can "be flatMapped with another NonEmptyMap of tuples" in {
    val base = NonEmptyMap.of(
      "apple"  -> 1,
      "banana" -> 2,
    )

    val actual = base.flatMap {
      case (name, count) =>
        NonEmptyMap.of(
          s"${name}_1" -> count,
          s"${name}_2" -> (count + 10),
        )
    }

    val expected = NonEmptyMap.of(
      "apple_1"  -> 1,
      "apple_2"  -> 11,
      "banana_1" -> 2,
      "banana_2" -> 12,
    )

    assert(actual === expected)
  }

  it can "be constructed from a non-empty arrayseq" in {
    assert(NonEmptyArraySeq.of("hello" -> 1).toNonEmptyMap === NonEmptyMap.one("hello", 1))
  }

}
