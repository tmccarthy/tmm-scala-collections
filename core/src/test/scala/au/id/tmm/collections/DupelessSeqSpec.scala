package au.id.tmm.collections

import munit.FunSuite

import scala.collection.immutable.ArraySeq

class DupelessSeqSpec extends FunSuite {

  test("a DupelessSeq retain the order of the earliest element when initialised") {
    assertEquals(DupelessSeq(1, 2, 3, 1).toList, List(1, 2, 3))
  }

  test("a DupelessSeq not contain duplicates") {
    assertEquals(DupelessSeq(1, 1, 1).count(_ == 1), 1)
  }

  test("a DupelessSeq retain insertion order") {
    assertEquals(DupelessSeq("the", "quick", "brown", "fox").toList, List("the", "quick", "brown", "fox"))
  }

  test("a DupelessSeq correctly implement contains") {
    val sut = DupelessSeq(1, 2, 3)

    assert(sut contains 1)
    assert(!(sut contains 4))
  }

  test("a DupelessSeq correctly implement size") {
    assertEquals(DupelessSeq(1, 2, 3).size, 3)
  }

  test("a DupelessSeq correctly implement size for the empty seq") {
    assertEquals(DupelessSeq().size, 0)
  }

  test("a DupelessSeq append an element correctly") {
    assertEquals(DupelessSeq(1, 2) :+ 3, DupelessSeq(1, 2, 3))
  }

  test("a DupelessSeq return the same seq when appending a duplicate element") {
    val originalSeq = DupelessSeq(1, 2)

    assert((originalSeq :+ 1) eq originalSeq)
  }

  test("a DupelessSeq prepend an element correctly") {
    assertEquals(DupelessSeq(1, 2).+:(3), DupelessSeq(3, 1, 2))
  }

  test("a DupelessSeq return the same seq when prepending a duplicate element") {
    val originalSeq = DupelessSeq(1, 2)

    assert(originalSeq.+:(2) eq originalSeq)
  }

  test("a DupelessSeq remove an element correctly") {
    assertEquals(DupelessSeq(1, 2, 3) - 3, DupelessSeq(1, 2))
  }

  test("a DupelessSeq return the same seq when removing an element not in the seq") {
    val originalSeq = DupelessSeq(1, 2)
    assert((originalSeq - 3) eq originalSeq)
  }

  test("a DupelessSeq have a sensible toString") {
    assertEquals(DupelessSeq(1, 2, 3).toString, "DupelessSeq(1, 2, 3)")
  }

  test("a DupelessSeq allow random access") {
    assertEquals(DupelessSeq(1, 2, 3, 4)(3), 4)
  }

  test("a DupelessSeq support splitting at an index") {
    assertEquals(DupelessSeq(1, 2, 3, 4, 5).splitAt(2), (DupelessSeq(1, 2), DupelessSeq(3, 4, 5)))
  }

  test("a DupelessSeq support updating an element with a new element") {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    val actual = original.updated(3, 8)

    val expected = DupelessSeq(1, 2, 3, 8, 5)

    assertEquals(actual, expected)
  }

  test("a DupelessSeq remove an element when updating it with an element that exists before it in the seq") {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    val actual = original.updated(3, 1)

    val expected = DupelessSeq(1, 2, 3, 5)

    assertEquals(actual, expected)
  }

  test("a DupelessSeq remove the later element when updating it with an element that exists after it in the seq") {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    val actual = original.updated(1, 3)

    val expected = DupelessSeq(1, 3, 4, 5)

    assertEquals(actual, expected)
  }

  test("a DupelessSeq pad the end of the seq with an element that is not in the seq") {
    assertEquals(DupelessSeq(1, 2, 3, 4, 5).padTo(99999, 6), DupelessSeq(1, 2, 3, 4, 5, 6))
  }

  test("a DupelessSeq return the same seq if padded with an element already in the seq") {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    assert(original.padTo(99999, 2) eq original)
  }

  test("a DupelessSeq support sorting") {
    assertEquals(DupelessSeq(1, 5, 3, 2, 4).sorted, DupelessSeq(1, 2, 3, 4, 5))
  }

  test("a DupelessSeq be reversed") {
    assertEquals(DupelessSeq(1, 2, 3, 4).reverse, DupelessSeq(4, 3, 2, 1))
  }

  test("a DupelessSeq be iterated over") {
    assertEquals(DupelessSeq(1, 2, 3, 4).iterator.toList, List(1, 2, 3, 4))
  }

  test("a DupelessSeq be iterated over in reverse") {
    assertEquals(DupelessSeq(1, 2, 3, 4).reverseIterator.toList, List(4, 3, 2, 1))
  }

  test("a DupelessSeq return itself when asked for distinct elements") {
    val originalSeq = DupelessSeq(1, 2, 3, 4)

    assert(originalSeq.distinct eq originalSeq)
  }

  test("a DupelessSeq be converted to a Set") {
    assertEquals(DupelessSeq(1, 2, 3).toSet, Set(1, 2, 3))
  }

  test("a DupelessSeq be converted to a Set of a higher type") {
    val asSet = DupelessSeq(1, 2, 3).toSet[Any]

    assertEquals(asSet.contains("hello world"), false)
  }

  test("a DupelessSeq be converted to a Vector") {
    assertEquals(DupelessSeq(1, 2, 3).toVector, Vector(1, 2, 3))
  }

  test("a DupelessSeq be equal to a DupelessSeq with the same elements and order") {
    assertEquals(DupelessSeq(1, 2, 3), DupelessSeq(1, 2, 3))
  }

  test("a DupelessSeq not be equal to a DupelessSeq with the same elements and a different order") {
    assertNotEquals(DupelessSeq(1, 2, 3), DupelessSeq(2, 3, 1))
  }

  test("a DupelessSeq not be equal to a DupelessSeq with different elements") {
    assertNotEquals(DupelessSeq(1, 2, 3), DupelessSeq(1, 2, 4))
  }

  test("a DupelessSeq not be equal to a list with the same elements") {
    assertNotEquals(DupelessSeq(1, 2, 3): Seq[Int], List(1, 2, 3): Seq[Int])
  }

  test("a DupelessSeq not be equal to a list with duplicated elements") {
    assertNotEquals(DupelessSeq(1, 2, 3): Seq[Int], List(1, 2, 3, 1): Seq[Int])
  }

  test("a DupelessSeq not be equal to a set with the same elements") {
    assertNotEquals(DupelessSeq(1, 2, 3): Iterable[Int], Set(1, 2, 3): Iterable[Int])
  }

  test("a DupelessSeq allow appending an element") {
    assertEquals(DupelessSeq(1, 2, 3) :+ 4: DupelessSeq[Int], DupelessSeq(1, 2, 3, 4))
  }

  test("a DupelessSeq allow appending a list") {
    assertEquals(DupelessSeq(1, 2, 3) ++ List(4, 5, 6): DupelessSeq[Int], DupelessSeq(1, 2, 3, 4, 5, 6))
  }

  test("a DupelessSeq be converted to a NonEmptyDupelessSeq if it is nonEmpty") {
    assertEquals(DupelessSeq(1, 2, 3).toNonEmptyDupelessSeq, Some(NonEmptyDupelessSeq.of(1, 2, 3)))
  }

  test("the empty DupelessSeq be a singleton") {
    assert(DupelessSeq() eq DupelessSeq())
  }

  test("the empty DupelessSeq not be converted to a NonEmptyDupelessSeq") {
    assertEquals(DupelessSeq().toNonEmptyDupelessSeq, None)
  }

  test("the DupelessSeq builder build the empty seq") {
    assert(DupelessSeq.newBuilder[Int].result() eq DupelessSeq.empty)
  }

  test("the empty DupelessSeq build a seq with 2 distinct items") {
    val builder = DupelessSeq.newBuilder[Int]

    builder += 1
    builder += 3

    assertEquals(builder.result(), DupelessSeq(1, 3))
  }

  test("the empty DupelessSeq iterate in the order of first insertion") {
    val builder = DupelessSeq.newBuilder[Int]

    builder += 1
    builder += 3
    builder += 1

    assertEquals(builder.result(), DupelessSeq(1, 3))
  }

  test("the empty DupelessSeq be cleared") {
    val builder = DupelessSeq.newBuilder[Int]

    builder += 1
    builder += 3
    builder.clear()

    assert(builder.result().isEmpty)
  }

  test("the empty DupelessSeq append multiple elements at once") {
    val builder = DupelessSeq.newBuilder[Int]

    builder ++= Vector(1, 3, 1)

    assertEquals(builder.result(), DupelessSeq(1, 3))
  }

  test("the empty DupelessSeq accept a size hint") {
    val builder = DupelessSeq.newBuilder[Int]

    builder.sizeHint(5)

    builder += 1
    builder += 3

    assertEquals(builder.result(), DupelessSeq(1, 3))
  }

  test("the empty DupelessSeq be converted to an ArraySeq") {
    assertEquals(DupelessSeq(1, 2, 3).toArraySeq, ArraySeq(1, 2, 3))
  }

  test("the empty DupelessSeq allow appending an element") {
    assertEquals(DupelessSeq(1, 2, 3).appended(4): DupelessSeq[Int], DupelessSeq(1, 2, 3, 4))
  }

  test("the empty DupelessSeq allow appending a list") {
    assertEquals(DupelessSeq(1, 2, 3).appendedAll(List(4, 5, 6)): DupelessSeq[Int], DupelessSeq(1, 2, 3, 4, 5, 6))
  }

}
