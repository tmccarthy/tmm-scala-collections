package au.id.tmm.utilities.collection

import org.scalatest.FlatSpec

import scala.collection.immutable.ArraySeq
import scala.reflect.ClassTag

class NonEmptyArraySeqSpec extends FlatSpec {

  private def assertSpecialisedAs[T : ClassTag](nonEmptyArraySeq: NonEmptyArraySeq[_]): Unit =
    assert(nonEmptyArraySeq.underlying.getClass === implicitly[ClassTag[T]].runtimeClass)

  "The specialised factory methods" can "create a specialised NonEmptyArraySeq from one element" in {
    assertSpecialisedAs[ArraySeq.ofInt](NonEmptyArraySeq.one(1))
  }

  it can "create a specialised NonEmptyArraySeq from many elements" in {
    assertSpecialisedAs[ArraySeq.ofInt](NonEmptyArraySeq.of(1, 2, 3))
  }

  it can "create a specialised NonEmptyArraySeq from a head and a tail" in {
    assertSpecialisedAs[ArraySeq.ofInt](NonEmptyArraySeq.fromHeadTail(1, List(2, 3)))
  }

  it can "create a specialised NonEmptyArraySeq from an iterable" in {
    assertSpecialisedAs[ArraySeq.ofInt](NonEmptyArraySeq.fromIterable(List(1, 2, 3)).get)
  }

  "The untagged factory methods" can "create an unspecialised NonEmptyArraySeq from one element" in {
    assertSpecialisedAs[ArraySeq.ofRef[_]](NonEmptyArraySeq.untagged.one(1))
  }

}
