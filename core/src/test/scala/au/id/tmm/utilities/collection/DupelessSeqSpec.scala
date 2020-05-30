package au.id.tmm.utilities.collection

import org.scalatest.FlatSpec

import scala.collection.immutable.ArraySeq

class DupelessSeqSpec extends FlatSpec {

  "a DupelessSeq" should "retain the order of the earliest element when initialised" in {
    assert(DupelessSeq(1, 2, 3, 1).toList === List(1, 2, 3))
  }

  it should "not contain duplicates" in {
    assert(DupelessSeq(1, 1, 1).count(_ == 1) === 1)
  }

  it should "retain insertion order" in {
    assert(DupelessSeq("the", "quick", "brown", "fox").toList === List("the", "quick", "brown", "fox"))
  }

  it should "correctly implement contains" in {
    val sut = DupelessSeq(1, 2, 3)

    assert(sut contains 1)
    assert(!(sut contains 4))
  }

  it should "correctly implement size" in {
    assert(DupelessSeq(1, 2, 3).size === 3)
  }

  it should "correctly implement size for the empty seq" in {
    assert(DupelessSeq().size === 0)
  }

  it should "append an element correctly" in {
    assert((DupelessSeq(1, 2) :+ 3) === DupelessSeq(1, 2, 3))
  }

  it should "return the same seq when appending a duplicate element" in {
    val originalSeq = DupelessSeq(1, 2)

    assert((originalSeq :+ 1) eq originalSeq)
  }

  it should "prepend an element correctly" in {
    assert(DupelessSeq(1, 2).+:(3) === DupelessSeq(3, 1, 2))
  }

  it should "return the same seq when prepending a duplicate element" in {
    val originalSeq = DupelessSeq(1, 2)

    assert(originalSeq.+:(2) eq originalSeq)
  }

  it should "remove an element correctly" in {
    assert((DupelessSeq(1, 2, 3) - 3) === DupelessSeq(1, 2))
  }

  it should "return the same seq when removing an element not in the seq" in {
    val originalSeq = DupelessSeq(1, 2)
    assert((originalSeq - 3) eq originalSeq)
  }

  it should "have a sensible toString" in {
    assert(DupelessSeq(1, 2, 3).toString === "DupelessSeq(1, 2, 3)")
  }

  it should "allow random access" in {
    assert(DupelessSeq(1, 2, 3, 4)(3) === 4)
  }

  it should "support splitting at an index" in {
    assert(DupelessSeq(1, 2, 3, 4, 5).splitAt(2) === (DupelessSeq(1, 2), DupelessSeq(3, 4, 5)))
  }

  it should "support updating an element with a new element" in {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    val actual = original.updated(3, 8)

    val expected = DupelessSeq(1, 2, 3, 8, 5)

    assert(actual === expected)
  }

  it should "remove an element when updating it with an element that exists before it in the seq" in {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    val actual = original.updated(3, 1)

    val expected = DupelessSeq(1, 2, 3, 5)

    assert(actual === expected)
  }

  it should "remove the later element when updating it with an element that exists after it in the seq" in {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    val actual = original.updated(1, 3)

    val expected = DupelessSeq(1, 3, 4, 5)

    assert(actual === expected)
  }

  it can "pad the end of the seq with an element that is not in the seq" in {
    assert(DupelessSeq(1, 2, 3, 4, 5).padTo(99999, 6) === DupelessSeq(1, 2, 3, 4, 5, 6))
  }

  it should "return the same seq if padded with an element already in the seq" in {
    val original = DupelessSeq(1, 2, 3, 4, 5)

    assert(original.padTo(99999, 2) eq original)
  }

  it should "support sorting" in {
    assert(DupelessSeq(1, 5, 3, 2, 4).sorted === DupelessSeq(1, 2, 3, 4, 5))
  }

  it can "be reversed" in {
    assert(DupelessSeq(1, 2, 3, 4).reverse === DupelessSeq(4, 3, 2, 1))
  }

  it can "be iterated over" in {
    assert(DupelessSeq(1, 2, 3, 4).iterator.toList === List(1, 2, 3, 4))
  }

  it can "be iterated over in reverse" in {
    assert(DupelessSeq(1, 2, 3, 4).reverseIterator.toList === List(4, 3, 2, 1))
  }

  it should "return itself when asked for distinct elements" in {
    val originalSeq = DupelessSeq(1, 2, 3, 4)

    assert(originalSeq.distinct eq originalSeq)
  }

  it can "be converted to a Set" in {
    assert(DupelessSeq(1, 2, 3).toSet === Set(1, 2, 3))
  }

  it can "be converted to a Set of a higher type" in {
    val asSet = DupelessSeq(1, 2, 3).toSet[Any]

    assert(asSet.contains("hello world") === false)
  }

  it can "be converted to a Vector" in {
    assert(DupelessSeq(1, 2, 3).toVector === Vector(1, 2, 3))
  }

  it should "be equal to a DupelessSeq with the same elements and order" in {
    assert(DupelessSeq(1, 2, 3) === DupelessSeq(1, 2, 3))
  }

  it should "not be equal to a DupelessSeq with the same elements and a different order" in {
    assert(DupelessSeq(1, 2, 3) !== DupelessSeq(2, 3, 1))
  }

  it should "not be equal to a DupelessSeq with different elements" in {
    assert(DupelessSeq(1, 2, 3) !== DupelessSeq(1, 2, 4))
  }

  it should "not be equal to a list with the same elements" in {
    assert(DupelessSeq(1, 2, 3) !== List(1, 2, 3))
  }

  it should "not be equal to a list with duplicated elements" in {
    assert(DupelessSeq(1, 2, 3) !== List(1, 2, 3, 1))
  }

  it should "not be equal to a set with the same elements" in {
    assert(DupelessSeq(1, 2, 3) !== Set(1, 2, 3))
  }

  it should "allow appending an element" in {
    assert((DupelessSeq(1, 2, 3) :+ 4: DupelessSeq[Int]) === DupelessSeq(1, 2, 3, 4))
  }

  it should "allow appending a list" in {
    assert((DupelessSeq(1, 2, 3) ++ List(4, 5, 6): DupelessSeq[Int]) === DupelessSeq(1, 2, 3, 4, 5, 6))
  }

  it can "be converted to a NonEmptyDupelessSeq if it is nonEmpty" in {
    assert(DupelessSeq(1, 2, 3).toNonEmptyDupelessSeq === Some(NonEmptyDupelessSeq.of(1, 2, 3)))
  }

  "the empty DupelessSeq" should "be a singleton" in {
    assert(DupelessSeq() eq DupelessSeq())
  }

  it can "not be converted to a NonEmptyDupelessSeq" in {
    assert(DupelessSeq().toNonEmptyDupelessSeq === None)
  }

  "the DupelessSeq builder" should "build the empty seq" in {
    assert(DupelessSeq.newBuilder[Int].result() eq DupelessSeq.empty)
  }

  it should "build a seq with 2 distinct items" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder += 1
    builder += 3

    assert(builder.result() === DupelessSeq(1, 3))
  }

  it should "iterate in the order of first insertion" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder += 1
    builder += 3
    builder += 1

    assert(builder.result() === DupelessSeq(1, 3))
  }

  it can "be cleared" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder += 1
    builder += 3
    builder.clear()

    assert(builder.result().isEmpty)
  }

  it can "append multiple elements at once" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder ++= Vector(1, 3, 1)

    assert(builder.result() === DupelessSeq(1, 3))
  }

  it should "accept a size hint" in {
    val builder = DupelessSeq.newBuilder[Int]

    builder.sizeHint(5)

    builder += 1
    builder += 3

    assert(builder.result() === DupelessSeq(1, 3))
  }

  it can "be converted to an ArraySeq" in {
    assert(DupelessSeq(1, 2, 3).toArraySeq === ArraySeq(1, 2, 3))
  }

  it should "allow appending an element" in {
    assert((DupelessSeq(1, 2, 3).appended(4): DupelessSeq[Int]) === DupelessSeq(1, 2, 3, 4))
  }

  it should "allow appending a list" in {
    assert((DupelessSeq(1, 2, 3).appendedAll(List(4, 5, 6)): DupelessSeq[Int]) === DupelessSeq(1, 2, 3, 4, 5, 6))
  }

}
