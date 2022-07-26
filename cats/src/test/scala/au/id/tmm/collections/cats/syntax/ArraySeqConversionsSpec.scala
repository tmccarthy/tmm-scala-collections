package au.id.tmm.collections.cats.syntax

import au.id.tmm.collections.NonEmptyArraySeq
import cats.data.NonEmptyVector
import munit.FunSuite

import scala.collection.immutable.ArraySeq

class ArraySeqConversionsSpec extends FunSuite {

  test("a non-empty arrayseq can be converted to a non-empty vector") {
    assertEquals(NonEmptyArraySeq.of(1, 2, 3).toNev, NonEmptyVector.of(1, 2, 3))
  }

  test("a non-empty vector can be converted to a non-empty arrayseq") {
    assertEquals(NonEmptyVector.of(1, 2, 3).to[NonEmptyArraySeq], NonEmptyArraySeq.of(1, 2, 3))
  }

  test("a non-empty vector can be converted to a specialised non-empty arraySeq") {
    assertEquals(
      NonEmptyVector.of(1, 2, 3).toSpecialised[NonEmptyArraySeq].underlying.getClass.asInstanceOf[Class[Any]],
      classOf[ArraySeq.ofInt].asInstanceOf[Class[Any]],
    )
  }

}
