package au.id.tmm.utilities.collection.cats.syntax

import au.id.tmm.utilities.collection.NonEmptySet
import cats.data.{NonEmptyList, NonEmptyVector}
import org.scalatest.flatspec.AnyFlatSpec

class NonEmptySetOpsSpec extends AnyFlatSpec {

  "a non-empty set" can "be converted to a cats NonEmptyList" in {
    assert(NonEmptySet.of(1, 2, 3).toNel.sorted === NonEmptyList.of(1, 2, 3))
  }

  it can "be converted to a cats NonEmptyVector" in {
    assert(NonEmptySet.of(1, 2, 3).toNev.sorted === NonEmptyVector.of(1, 2, 3))
  }

  "a non-empty list" can "be converted to a tmmUtils NonEmptySet" in {
    assert(NonEmptyList.of(1, 2, 2).to[NonEmptySet] === NonEmptySet.of(1, 2))
  }

  "a non-empty vector" can "be converted to a tmmUtils NonEmptySet" in {
    assert(NonEmptyVector.of(1, 2, 2).to[NonEmptySet] === NonEmptySet.of(1, 2))
  }

}
