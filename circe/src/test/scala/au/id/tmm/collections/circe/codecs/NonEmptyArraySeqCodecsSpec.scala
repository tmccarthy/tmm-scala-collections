package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.NonEmptyArraySeq
import au.id.tmm.collections.circe.codecs.all._
import au.id.tmm.utilities.testing.syntax._
import io.circe.Json
import io.circe.syntax.EncoderOps
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.immutable.ArraySeq

class NonEmptyArraySeqCodecsSpec extends AnyFlatSpec {
  "the nonEmptyArraySeqEncoder" should "encode a dupeless seq" in {
    assert(NonEmptyArraySeq.of(1, 2, 3).asJson === Json.arr(1.asJson, 2.asJson, 3.asJson))
  }

  "the nonEmptyArraySeqDecoder" should "decode an array" in {
    assert(Json.arr(1.asJson, 2.asJson, 3.asJson).as[NonEmptyArraySeq[Int]] === Right(NonEmptyArraySeq.of(1, 2, 3)))
  }

  it should "error if decoding an empty array" in {
    assert(
      Json
        .arr()
        .as[NonEmptyArraySeq[Int]]
        .leftGet
        .message === "Empty array cannot be decoded to NonEmptyArraySeq",
    )
  }

  it should "specialise the underlying ArraySeq if a classtag is available" in {
    assert(Json.arr(1.asJson).as[NonEmptyArraySeq[Int]].get.underlying.getClass === classOf[ArraySeq.ofInt])
  }

}
