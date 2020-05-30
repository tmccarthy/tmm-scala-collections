package au.id.tmm.utilities.collection.classes

import au.id.tmm.utilities.collection.NonEmptySet
import org.scalatest.FlatSpec
import au.id.tmm.utilities.collection.syntax.toSafeGroupByOps

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
