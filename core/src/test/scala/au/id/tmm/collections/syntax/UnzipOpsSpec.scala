package au.id.tmm.collections.syntax

import org.scalatest.flatspec.AnyFlatSpec

class UnzipOpsSpec extends AnyFlatSpec {

  "unzip10" should "unzip" in {
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

    assert(list.unzip10 === expectedUnzipped)
  }

  "unzip9" should "unzip" in {
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

    assert(list.unzip9 === expectedUnzipped)
  }

  "unzip8" should "unzip" in {
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

    assert(list.unzip8 === expectedUnzipped)
  }

  "unzip7" should "unzip" in {
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

    assert(list.unzip7 === expectedUnzipped)
  }

  "unzip6" should "unzip" in {
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

    assert(list.unzip6 === expectedUnzipped)
  }

  "unzip5" should "unzip" in {
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

    assert(list.unzip5 === expectedUnzipped)
  }

  "unzip4" should "unzip" in {
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

    assert(list.unzip4 === expectedUnzipped)
  }

}
