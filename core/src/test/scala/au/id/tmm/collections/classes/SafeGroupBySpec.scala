package au.id.tmm.collections.classes

import au.id.tmm.collections.NonEmptySet
import au.id.tmm.collections.syntax.toSafeGroupByOps
import org.scalatest.FlatSpec

class SafeGroupBySpec extends FlatSpec {

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

}
