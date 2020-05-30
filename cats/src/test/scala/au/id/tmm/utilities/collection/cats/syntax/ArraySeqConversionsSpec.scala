package au.id.tmm.utilities.collection.cats.syntax

import au.id.tmm.utilities.collection.NonEmptyArraySeq
import cats.data.NonEmptyVector
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.immutable.ArraySeq

class ArraySeqConversionsSpec extends AnyFlatSpec {

  "a non-empty arrayseq" can "be converted to a non-empty vector" in {
    assert(NonEmptyArraySeq.of(1, 2, 3).toNev === NonEmptyVector.of(1, 2, 3))
  }

  "a non-empty vector" can "be converted to a non-empty arrayseq" in {
    assert(NonEmptyVector.of(1, 2, 3).to[NonEmptyArraySeq] === NonEmptyArraySeq.of(1, 2, 3))
  }

  it can "be converted to a specialised non-empty arraySeq" in {
    assert(NonEmptyVector.of(1, 2, 3).toSpecialised[NonEmptyArraySeq].underlying.getClass === classOf[ArraySeq.ofInt])
  }

}
