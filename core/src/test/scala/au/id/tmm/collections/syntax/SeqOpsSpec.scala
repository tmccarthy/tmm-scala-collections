package au.id.tmm.collections.syntax

import munit.FunSuite

class SeqOpsSpec extends FunSuite {

  test("finding every nth element return empty for an empty seq") {
    assertEquals(List.empty[Int].everyNth(5), List.empty)
  }

  test("finding every nth element return the first element if there are less than n elements") {
    assertEquals(List(1, 2, 3).everyNth(5), List(1))
  }

  test("finding every nth element return every nth element") {
    assertEquals(Range.inclusive(1, 11).toList.everyNth(5), List(1, 6, 11))
  }

  test("finding every nth element drop any extra elements") {
    assertEquals(Range.inclusive(1, 20).toList.everyNth(5), List(1, 6, 11, 16))
  }

}
