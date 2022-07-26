package au.id.tmm.collections.syntax

import munit.FunSuite

class IteratorOpsSpec extends FunSuite {

  private val data            = List("the", "quick", "brown", "fox")
  private val iteratorFixture = FunFixture[Iterator[String]](_ => data.iterator, _ => ())

  iteratorFixture.test("readAtMost read the first n elements of the iterator") { iterator =>
    assertEquals(List("the", "quick"), iterator.readAtMost(2).toList)
  }

  iteratorFixture.test("readAtMost leave the underlying iterator iterating through the subsequent elements") {
    iterator =>
      iterator.readAtMost(2)

      assertEquals(List("brown", "fox"), iterator.toList)
  }

  iteratorFixture.test("readAtMost read only as many elements remain in the iterator") { iterator =>
    assertEquals(data, iterator.readAtMost(5).toList)
  }

  iteratorFixture.test("readUntil read until encountering an element matching the condition") { iterator =>
    assertEquals(Vector("the", "quick", "brown"), iterator.readUntil(_.startsWith("b")))
  }

  iteratorFixture.test("readUntil leave the underlying iterator iterating through the subsequent elements") {
    iterator =>
      iterator.readUntil(_.startsWith("b"))

      assertEquals(List("fox"), iterator.toList)
  }

  iteratorFixture.test("readUntil read only as many elements remain in the iterator") { iterator =>
    assertEquals(data, iterator.readUntil(_ => false).toList)
  }

  iteratorFixture.test(
    "readAtMostUntil read until encountering an element matching the condition if that is less than the size limit",
  ) { iterator =>
    assertEquals(Vector("the", "quick", "brown"), iterator.readAtMostUntil(3, _ startsWith "b"))
  }

  iteratorFixture.test("readAtMostUntil read up to the size limit if no elements match condition") { iterator =>
    assertEquals(Vector("the", "quick"), iterator.readAtMostUntil(2, _ => false))
  }

  iteratorFixture.test(
    "readAtMostUntil leave the underlying iterator iterating through the subsequent elements when hitting the number limit",
  ) { iterator =>
    iterator.readAtMostUntil(2, _ => false)

    assertEquals(List("brown", "fox"), iterator.toList)
  }

  iteratorFixture.test(
    "readAtMostUntil leave the underlying iterator iterating through the subsequent elements when finding an element that matches",
  ) { iterator =>
    iterator.readAtMostUntil(4, _ startsWith "b")

    assertEquals(List("fox"), iterator.toList)
  }

}
