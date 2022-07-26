package au.id.tmm.collections.syntax

import munit.FunSuite

class IteratorOpsSpec extends FunSuite {

  private val data     = List("the", "quick", "brown", "fox")
  private val iterator = data.iterator

  test("readAtMost read the first n elements of the iterator") {
    assertEquals(List("the", "quick"), iterator.readAtMost(2).toList)
  }

  test("readAtMost leave the underlying iterator iterating through the subsequent elements") {
    iterator.readAtMost(2)

    assertEquals(List("brown", "fox"), iterator.toList)
  }

  test("readAtMost read only as many elements remain in the iterator") {
    assertEquals(data, iterator.readAtMost(5).toList)
  }

  test("readUntil read until encountering an element matching the condition") {
    assertEquals(Vector("the", "quick", "brown"), iterator.readUntil(_.startsWith("b")))
  }

  test("readUntil leave the underlying iterator iterating through the subsequent elements") {
    iterator.readUntil(_.startsWith("b"))

    assertEquals(List("fox"), iterator.toList)
  }

  test("readUntil read only as many elements remain in the iterator") {
    assertEquals(data, iterator.readUntil(_ => false).toList)
  }

  test(
    "readAtMostUntil read until encountering an element matching the condition if that is less than the size limit",
  ) {
    assertEquals(Vector("the", "quick", "brown"), iterator.readAtMostUntil(3, _ startsWith "b"))
  }

  test("readAtMostUntil read up to the size limit if no elements match condition") {
    assertEquals(Vector("the", "quick"), iterator.readAtMostUntil(2, _ => false))
  }

  test(
    "readAtMostUntil leave the underlying iterator iterating through the subsequent elements when hitting the number limit",
  ) {
    iterator.readAtMostUntil(2, _ => false)

    assertEquals(List("brown", "fox"), iterator.toList)
  }

  test(
    "readAtMostUntil leave the underlying iterator iterating through the subsequent elements when finding an element that matches",
  ) {
    iterator.readAtMostUntil(4, _ startsWith "b")

    assertEquals(List("fox"), iterator.toList)
  }

}
