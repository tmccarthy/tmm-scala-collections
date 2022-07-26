package au.id.tmm.collections

import munit.FunSuite

class NonEmptyMapSpec extends FunSuite {

  test("a non-empty map be flatMapped with a possibly empty collection of tuples") {
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

    assertEquals(actual, expected)
  }

  test("a non-empty map be flatMapped with a possibly empty collection of single elements") {
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

    assertEquals(actual.toList, expected)
  }

  test("a non-empty map be flatMapped with another NonEmptyMap of tuples") {
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

    assertEquals(actual, expected)
  }

  test("a non-empty map be constructed from a non-empty arrayseq") {
    assertEquals(NonEmptyArraySeq.of("hello" -> 1).toNonEmptyMap, NonEmptyMap.one("hello", 1))
  }

}
