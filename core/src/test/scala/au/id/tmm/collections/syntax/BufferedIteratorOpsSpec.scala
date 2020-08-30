package au.id.tmm.collections.syntax

import org.scalatest.flatspec.AnyFlatSpec

class BufferedIteratorOpsSpec extends AnyFlatSpec {

  "takeUpTo" should "work for an empty iterator" in {
    assert(List.empty[Int].iterator.buffered.takeUpTo(_ > 0).toList === List.empty[Int])
  }

  it should "work for a collection where the first element does not match predicate" in {
    assert(Iterator(1, 2, 3).buffered.takeUpTo(_ < 0).toList === List.empty)
  }

  it should "take elements from the front of the iterator until the first element that fails the test" in {
    assert(Iterator(1, 2, 3, 4, 5).buffered.takeUpTo(_ < 3).toList === List(1, 2))
  }

  it should "not progress the underlying iterator beyond the first failed element" in {
    val underlyingIterator = Iterator(1, 2, 3, 4, 5).buffered

    underlyingIterator.takeUpTo(_ < 3).toList

    assert(underlyingIterator.toList === List(3, 4, 5))
  }

  it should "return an iterator with a known size if the underlying iterator is empty" in {
    assert(Iterator.empty[Int].buffered.takeUpTo(_ => true).knownSize === 0)
  }

  it should "return an iterator with a known size if the head does not match the predicate" in {
    assert(Iterator(1, 2, 3).buffered.takeUpTo(_ < 0).knownSize === 0)
  }

  it should "return an unknown size if the head matches the predicate" in {
    assert(Iterator(1, 2, 3).buffered.takeUpTo(_ > 0).knownSize === -1)
  }

}
