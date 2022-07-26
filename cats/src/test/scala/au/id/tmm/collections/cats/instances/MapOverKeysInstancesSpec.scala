package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.cats.instances.mapOverKeys._
import cats.MonoidK
import munit.FunSuite

class MapOverKeysInstancesSpec extends FunSuite {

  test("the monoid for a map over keys should combine values") {
    val left: Map[Int, List[String]] = Map(
      1 -> List("a"),
      2 -> List("a"),
    )

    val right: Map[Int, List[String]] = Map(
      2 -> List("b"),
      3 -> List("c"),
    )

    val actual = MonoidK[Map[*, List[String]]].combineK(left, right)

    val expected = Map(
      1 -> List("a"),
      2 -> List("a", "b"),
      3 -> List("c"),
    )

    assertEquals(expected, actual)
  }

}
