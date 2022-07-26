package au.id.tmm.collections.syntax

import munit.FunSuite

class UnzipOpsSpec extends FunSuite {

  test("unzip10 unzip") {
    val list = List(
      (0, 1, 2, 3, 4, 5, 6, 7, 8, 9),
      (10, 11, 12, 13, 14, 15, 16, 17, 18, 19),
      (20, 21, 22, 23, 24, 25, 26, 27, 28, 29),
    )

    val expectedUnzipped = (
      List(0, 10, 20),
      List(1, 11, 21),
      List(2, 12, 22),
      List(3, 13, 23),
      List(4, 14, 24),
      List(5, 15, 25),
      List(6, 16, 26),
      List(7, 17, 27),
      List(8, 18, 28),
      List(9, 19, 29),
    )

    assertEquals(list.unzip10, expectedUnzipped)
  }

  test("unzip9 unzip") {
    val list = List(
      (0, 1, 2, 3, 4, 5, 6, 7, 8),
      (10, 11, 12, 13, 14, 15, 16, 17, 18),
      (20, 21, 22, 23, 24, 25, 26, 27, 28),
    )

    val expectedUnzipped = (
      List(0, 10, 20),
      List(1, 11, 21),
      List(2, 12, 22),
      List(3, 13, 23),
      List(4, 14, 24),
      List(5, 15, 25),
      List(6, 16, 26),
      List(7, 17, 27),
      List(8, 18, 28),
    )

    assertEquals(list.unzip9, expectedUnzipped)
  }

  test("unzip8 unzip") {
    val list = List(
      (0, 1, 2, 3, 4, 5, 6, 7),
      (10, 11, 12, 13, 14, 15, 16, 17),
      (20, 21, 22, 23, 24, 25, 26, 27),
    )

    val expectedUnzipped = (
      List(0, 10, 20),
      List(1, 11, 21),
      List(2, 12, 22),
      List(3, 13, 23),
      List(4, 14, 24),
      List(5, 15, 25),
      List(6, 16, 26),
      List(7, 17, 27),
    )

    assertEquals(list.unzip8, expectedUnzipped)
  }

  test("unzip7 unzip") {
    val list = List(
      (0, 1, 2, 3, 4, 5, 6),
      (10, 11, 12, 13, 14, 15, 16),
      (20, 21, 22, 23, 24, 25, 26),
    )

    val expectedUnzipped = (
      List(0, 10, 20),
      List(1, 11, 21),
      List(2, 12, 22),
      List(3, 13, 23),
      List(4, 14, 24),
      List(5, 15, 25),
      List(6, 16, 26),
    )

    assertEquals(list.unzip7, expectedUnzipped)
  }

  test("unzip6 unzip") {
    val list = List(
      (0, 1, 2, 3, 4, 5),
      (10, 11, 12, 13, 14, 15),
      (20, 21, 22, 23, 24, 25),
    )

    val expectedUnzipped = (
      List(0, 10, 20),
      List(1, 11, 21),
      List(2, 12, 22),
      List(3, 13, 23),
      List(4, 14, 24),
      List(5, 15, 25),
    )

    assertEquals(list.unzip6, expectedUnzipped)
  }

  test("unzip5 unzip") {
    val list = List(
      (0, 1, 2, 3, 4),
      (10, 11, 12, 13, 14),
      (20, 21, 22, 23, 24),
    )

    val expectedUnzipped = (
      List(0, 10, 20),
      List(1, 11, 21),
      List(2, 12, 22),
      List(3, 13, 23),
      List(4, 14, 24),
    )

    assertEquals(list.unzip5, expectedUnzipped)
  }

  test("unzip4 unzip") {
    val list = List(
      (0, 1, 2, 3),
      (10, 11, 12, 13),
      (20, 21, 22, 23),
    )

    val expectedUnzipped = (
      List(0, 10, 20),
      List(1, 11, 21),
      List(2, 12, 22),
      List(3, 13, 23),
    )

    assertEquals(list.unzip4, expectedUnzipped)
  }

}
