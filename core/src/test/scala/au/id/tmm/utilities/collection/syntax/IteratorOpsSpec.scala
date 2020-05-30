package au.id.tmm.utilities.collection.syntax

import org.scalatest.{FlatSpec, OneInstancePerTest}

class IteratorOpsSpec extends FlatSpec with OneInstancePerTest {

  private val data     = List("the", "quick", "brown", "fox")
  private val iterator = data.iterator

  "readAtMost" should "read the first n elements of the iterator" in {
    assert(List("the", "quick") === iterator.readAtMost(2))
  }

  it should "leave the underlying iterator iterating through the subsequent elements" in {
    iterator.readAtMost(2)

    assert(List("brown", "fox") === iterator.toList)
  }

  it should "read only as many elements remain in the iterator" in {
    assert(data === iterator.readAtMost(5))
  }

  "readUntil" should "read until encountering an element matching the condition" in {
    assert(Vector("the", "quick", "brown") === iterator.readUntil(_.startsWith("b")))
  }

  it should "leave the underlying iterator iterating through the subsequent elements" in {
    iterator.readUntil(_.startsWith("b"))

    assert(List("fox") === iterator.toList)
  }

  it should "read only as many elements remain in the iterator" in {
    assert(data === iterator.readUntil(_ => false))
  }

  "readAtMostUntil" should "read until encountering an element matching the condition if that is less than the size limit" in {
    assert(Vector("the", "quick", "brown") === iterator.readAtMostUntil(3, _ startsWith "b"))
  }

  it should "read up to the size limit if no elements match condition" in {
    assert(Vector("the", "quick") === iterator.readAtMostUntil(2, _ => false))
  }

  it should "leave the underlying iterator iterating through the subsequent elements when hitting the number limit" in {
    iterator.readAtMostUntil(2, _ => false)

    assert(List("brown", "fox") === iterator.toList)
  }

  it should "leave the underlying iterator iterating through the subsequent elements when finding an element that matches" in {
    iterator.readAtMostUntil(4, _ startsWith "b")

    assert(List("fox") === iterator.toList)
  }

}
