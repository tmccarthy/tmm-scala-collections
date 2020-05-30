package au.id.tmm.utilities.collection.circe

import au.id.tmm.utilities.collection.NonEmptyDupelessSeq
import au.id.tmm.utilities.testing.syntax._
import io.circe.Json
import io.circe.syntax.EncoderOps
import org.scalatest.FlatSpec

class NonEmptyDupelessSeqCodecsSpec extends FlatSpec {

  "the nonEmptyDupelessSeqEncoder" should "encode a dupeless seq" in {
    assert(NonEmptyDupelessSeq.of(1, 2, 3).asJson === Json.arr(1.asJson, 2.asJson, 3.asJson))
  }

  "the nonEmptyDupelessSeqDecoder" should "decode an array" in {
    assert(
      Json.arr(1.asJson, 2.asJson, 3.asJson).as[NonEmptyDupelessSeq[Int]] === Right(NonEmptyDupelessSeq.of(1, 2, 3)))
  }

  it should "silently drop duplicates when decoding" in {
    assert(Json.arr(1.asJson, 1.asJson, 2.asJson).as[NonEmptyDupelessSeq[Int]] === Right(NonEmptyDupelessSeq.of(1, 2)))
  }

  it should "error if decoding an empty array" in {
    assert(
      Json
        .arr()
        .as[NonEmptyDupelessSeq[Int]]
        .leftGet
        .message === "Empty array cannot be decoded to NonEmptyDupelessSeq")
  }

}
