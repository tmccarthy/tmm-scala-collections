package au.id.tmm.collections.cats.instances.unlawful

import au.id.tmm.collections.cats.instances.AdHocTestIgnore
import au.id.tmm.collections.cats.instances.unlawful.map._
import cats.implicits.catsSyntaxGroup
import cats.kernel.laws.discipline.{CommutativeGroupTests, GroupTests}
import munit.DisciplineSuite

class MapUnlawfulInstancesSpec extends DisciplineSuite with AdHocTestIgnore {

  test("the group for map should subtract elements as appropriate") {
    val left = Map(
      "apple"  -> 1,
      "banana" -> -1,
    )

    val right = Map(
      "banana" -> 4,
      "pear"   -> 2,
    )

    val expected = Map(
      "apple"  -> 1,
      "banana" -> -5,
      "pear"   -> -2,
    )

    assertEquals((left |-| right), expected)
  }

  checkAll("Unlawful Group for Map", GroupTests[Map[String, Int]](tmmUtilsUnlawfulGroupForMap).group)
  checkAll("Unlawful CommutativeGroup for Map", CommutativeGroupTests[Map[String, Int]].group)

  override protected def ignoredTestNames: Set[String] =
    Set(
      "Unlawful Group for Map.group.left inverse",
      "Unlawful Group for Map.group.right inverse",
      "Unlawful CommutativeGroup for Map.group.left inverse",
      "Unlawful CommutativeGroup for Map.group.right inverse",
    )
}
