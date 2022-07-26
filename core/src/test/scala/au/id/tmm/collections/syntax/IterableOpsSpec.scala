package au.id.tmm.collections.syntax

import au.id.tmm.utilities.testing.syntax._
import munit.FunSuite

class IterableOpsSpec extends FunSuite {

  test("at most one return None for an empty iterable") {
    assertEquals(List.empty[Int].atMostOne.get, None)
  }

  test("at most one return Some for a single element list") {
    assertEquals(List(1).atMostOne.get, Some(1))
  }

  test("at most one return an error for a 2 element list") {
    assert(List(1, 2).atMostOne.isLeft)
  }

  test("at most one return an error for an infinitely sized list") {
    assert(LazyList.continually(0).atMostOne.isLeft)
  }

  test("only element return an error for an empty iterable") {
    assert(List.empty[Int].onlyElementOrException.isLeft)
  }

  test("only element return the element for a 1 element iterable") {
    assertEquals(List(1).onlyElementOrException.get, 1)
  }

  test("only element return an error for a 2 element list") {
    assert(List(1, 2).onlyElementOrException.isLeft)
  }

  test("only element return an error for an infinitely sized list") {
    assert(LazyList.continually(0).onlyElementOrException.isLeft)
  }

  test("head or error return an error for an empty iterable") {
    assert(List.empty[Int].headOrException.isLeft)
  }

  test("head or error return the element for a 1 element iterable") {
    assertEquals(List(1).headOrException.get, 1)
  }

  test("head or error return the first element for a 2 element list") {
    assertEquals(List(1, 2).headOrException.get, 1)
  }

  test("head or error return the first element for an infinitely sized list") {
    assertEquals(LazyList.continually(0).headOrException.get, 0)
  }

  test("last or error return an error for an empty iterable") {
    assert(List.empty[Int].lastOrException.isLeft)
  }

  test("last or error return the element for a 1 element iterable") {
    assertEquals(List(1).lastOrException.get, 1)
  }

  test("last or error return an error for a 2 element list") {
    assertEquals(List(1, 2).lastOrException.get, 2)
  }

  test("emptyOr return unit for an empty iterable") {
    assertEquals(List.empty[Int].emptyOrException.get, ())
  }

  test("emptyOr return an error if the iterable is nonempty") {
    assertEquals(List(1).emptyOrException.leftGet.getMessage, "Expected empty iterable. Iterable was List(1)")
  }

  test("count occurrences count the occurances of each element") {
    assertEquals(List(1, 1, 1, 2, 2, 3).countOccurrences, Map(1 -> 3, 2 -> 2, 3 -> 1))
  }

  test("count occurrences return an empty map for an empty collection") {
    assertEquals(List.empty[Int].countOccurrences, Map.empty[Int, Int])
  }

  test("groupByKey group by the key for a list of tuples") {
    val tuples = List(
      1 -> "hello",
      1 -> "world",
      2 -> "blah",
    )

    val expectedGrouped = Map(
      1 -> List("hello", "world"),
      2 -> List("blah"),
    )

    assertEquals(tuples.groupByKey, expectedGrouped)
  }

  test("groupByKey group by the the key for a Set of tuples") {
    val tuples = Set(
      1 -> "hello",
      1 -> "world",
      2 -> "blah",
    )

    val expectedGrouped = Map(
      1 -> Set("hello", "world"),
      2 -> Set("blah"),
    )

    assertEquals(tuples.groupByKey, expectedGrouped)
  }

  test("groupByKey return an empty map for an empty list of tuples") {
    assertEquals(List.empty[(Int, String)].groupByKey, Map.empty[Int, List[String]])
  }

}
