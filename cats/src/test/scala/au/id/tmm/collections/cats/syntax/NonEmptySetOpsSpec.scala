package au.id.tmm.collections.cats.syntax

import au.id.tmm.collections.NonEmptySet
import cats.data.{NonEmptyList, NonEmptyVector}
import munit.FunSuite

class NonEmptySetOpsSpec extends FunSuite {

  test("a non-empty set can be converted to a cats NonEmptyList") {
    assertEquals(NonEmptySet.of(1, 2, 3).toNel.sorted, NonEmptyList.of(1, 2, 3))
  }

  test("a non-empty set can be converted to a cats NonEmptyVector") {
    assertEquals(NonEmptySet.of(1, 2, 3).toNev.sorted, NonEmptyVector.of(1, 2, 3))
  }

  test("a non-empty list can be converted to a tmmUtils NonEmptySet") {
    assertEquals(NonEmptyList.of(1, 2, 2).to[NonEmptySet], NonEmptySet.of(1, 2))
  }

  test("a non-empty vector can be converted to a tmmUtils NonEmptySet") {
    assertEquals(NonEmptyVector.of(1, 2, 2).to[NonEmptySet], NonEmptySet.of(1, 2))
  }

}
