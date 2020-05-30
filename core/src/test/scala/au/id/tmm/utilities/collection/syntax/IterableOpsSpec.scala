package au.id.tmm.utilities.collection.syntax

import au.id.tmm.utilities.testing.syntax._
import org.scalatest.FlatSpec

class IterableOpsSpec extends FlatSpec {

  "at most one" should "return None for an empty iterable" in {
    assert(List.empty[Int].atMostOne.get === None)
  }

  it should "return Some for a single element list" in {
    assert(List(1).atMostOne.get === Some(1))
  }

  it should "return an error for a 2 element list" in {
    assert(List(1, 2).atMostOne.isLeft)
  }

  it should "return an error for an infinitely sized list" in {
    assert(LazyList.continually(0).atMostOne.isLeft)
  }

  "only element" should "return an error for an empty iterable" in {
    assert(List.empty[Int].onlyElementOrException.isLeft)
  }

  it should "return the element for a 1 element iterable" in {
    assert(List(1).onlyElementOrException.get === 1)
  }

  it should "return an error for a 2 element list" in {
    assert(List(1, 2).onlyElementOrException.isLeft)
  }

  it should "return an error for an infinitely sized list" in {
    assert(LazyList.continually(0).onlyElementOrException.isLeft)
  }

  "emptyOr" should "return unit for an empty iterable" in {
    assert(List.empty[Int].emptyOrException.get === ())
  }

  it should "return an error if the iterable is nonempty" in {
    assert(List(1).emptyOrException.leftGet.getMessage === "Expected empty iterable. Iterable was List(1)")
  }

  "count occurrences" should "count the occurances of each element" in {
    assert(List(1, 1, 1, 2, 2, 3).countOccurrences === Map(1 -> 3, 2 -> 2, 3 -> 1))
  }

  it should "return an empty map for an empty collection" in {
    assert(List.empty[Int].countOccurrences === Map.empty[Int, Int])
  }

  "groupByKey" should "group by the key for a list of tuples" in {
    val tuples = List(
      1 -> "hello",
      1 -> "world",
      2 -> "blah",
    )

    val expectedGrouped = Map(
      1 -> List("hello", "world"),
      2 -> List("blah"),
    )

    assert(tuples.groupByKey === expectedGrouped)
  }

  it should "group by the the key for a Set of tuples" in {
    val tuples = Set(
      1 -> "hello",
      1 -> "world",
      2 -> "blah",
    )

    val expectedGrouped = Map(
      1 -> Set("hello", "world"),
      2 -> Set("blah"),
    )

    assert(tuples.groupByKey === expectedGrouped)
  }

  it should "return an empty map for an empty list of tuples" in {
    assert(List.empty[(Int, String)].groupByKey === Map.empty)
  }

}
