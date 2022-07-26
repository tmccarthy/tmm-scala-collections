package au.id.tmm.collections.syntax

import munit.FunSuite

class BufferedIteratorOpsSpec extends FunSuite {

  test("takeUpTo work for an empty iterator") {
    assertEquals(List.empty[Int].iterator.buffered.takeUpTo(_ > 0).toList, List.empty[Int])
  }

  test("takeUpTo work for a collection where the first element does not match predicate") {
    assertEquals(Iterator(1, 2, 3).buffered.takeUpTo(_ < 0).toList, List.empty)
  }

  test("takeUpTo take elements from the front of the iterator until the first element that fails the test") {
    assertEquals(Iterator(1, 2, 3, 4, 5).buffered.takeUpTo(_ < 3).toList, List(1, 2))
  }

  test("takeUpTo not progress the underlying iterator beyond the first failed element") {
    val underlyingIterator = Iterator(1, 2, 3, 4, 5).buffered

    underlyingIterator.takeUpTo(_ < 3).toList

    assertEquals(underlyingIterator.toList, List(3, 4, 5))
  }

  test("takeUpTo return an iterator with a known size if the underlying iterator is empty") {
    assertEquals(Iterator.empty[Int].buffered.takeUpTo(_ => true).knownSize, 0)
  }

  test("takeUpTo return an iterator with a known size if the head does not match the predicate") {
    assertEquals(Iterator(1, 2, 3).buffered.takeUpTo(_ < 0).knownSize, 0)
  }

  test("takeUpTo return an unknown size if the head matches the predicate") {
    assertEquals(Iterator(1, 2, 3).buffered.takeUpTo(_ > 0).knownSize, -1)
  }

}
