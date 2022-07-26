package au.id.tmm.collections

import munit.FunSuite

import scala.collection.immutable.ArraySeq
import scala.reflect.ClassTag

class NonEmptyArraySeqSpec extends FunSuite {

  private def assertSpecialisedAs[T : ClassTag](nonEmptyArraySeq: NonEmptyArraySeq[_]): Unit =
    assertEquals(
      nonEmptyArraySeq.underlying.getClass.asInstanceOf[Class[Any]],
      implicitly[ClassTag[T]].runtimeClass.asInstanceOf[Class[Any]],
    )

  test("The specialised factory methods create a specialised NonEmptyArraySeq from one element") {
    assertSpecialisedAs[ArraySeq.ofInt](NonEmptyArraySeq.one(1))
  }

  test("The specialised factory methods create a specialised NonEmptyArraySeq from many elements") {
    assertSpecialisedAs[ArraySeq.ofInt](NonEmptyArraySeq.of(1, 2, 3))
  }

  test("The specialised factory methods create a specialised NonEmptyArraySeq from a head and a tail") {
    assertSpecialisedAs[ArraySeq.ofInt](NonEmptyArraySeq.fromHeadTail(1, List(2, 3)))
  }

  test("The specialised factory methods create a specialised NonEmptyArraySeq from an iterable") {
    assertSpecialisedAs[ArraySeq.ofInt](NonEmptyArraySeq.fromIterable(List(1, 2, 3)).get)
  }

  test("The untagged factory methods create an unspecialised NonEmptyArraySeq from one element") {
    assertSpecialisedAs[ArraySeq.ofRef[_]](NonEmptyArraySeq.untagged.one(1))
  }

}
