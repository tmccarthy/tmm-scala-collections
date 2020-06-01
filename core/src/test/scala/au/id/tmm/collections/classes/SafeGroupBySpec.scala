package au.id.tmm.collections.classes

import au.id.tmm.collections.syntax.toSafeGroupByOps
import au.id.tmm.collections.{NonEmptyMap, NonEmptySet}
import org.scalatest.flatspec.AnyFlatSpec

class SafeGroupBySpec extends AnyFlatSpec {

  "safe group by for a Set" should "work" in {
    val set = Set(
      "apple",
      "apricot",
      "banana",
    )

    val expectedGrouped = NonEmptyMap.of(
      'a' -> NonEmptySet.of("apple", "apricot"),
      'b' -> NonEmptySet.of("banana"),
    )

    assert(set.safeGroupBy(_.head) === expectedGrouped)
  }

}
