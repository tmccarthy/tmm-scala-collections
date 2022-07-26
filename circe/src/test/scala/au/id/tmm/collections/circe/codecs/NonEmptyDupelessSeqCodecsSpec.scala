package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.NonEmptyDupelessSeq
import au.id.tmm.collections.circe.codecs.all._
import au.id.tmm.utilities.testing.syntax._
import io.circe.Json
import io.circe.syntax.EncoderOps
import munit.FunSuite

class NonEmptyDupelessSeqCodecsSpec extends FunSuite {

  test("the nonEmptyDupelessSeqEncoder should encode a dupeless seq") {
    assertEquals(NonEmptyDupelessSeq.of(1, 2, 3).asJson, Json.arr(1.asJson, 2.asJson, 3.asJson))
  }

  test("the nonEmptyDupelessSeqDecoder should decode an array") {
    assertEquals(
      Json.arr(1.asJson, 2.asJson, 3.asJson).as[NonEmptyDupelessSeq[Int]],
      Right(NonEmptyDupelessSeq.of(1, 2, 3)),
    )
  }

  test("the nonEmptyDupelessSeqDecoder should silently drop duplicates when decoding") {
    assertEquals(
      Json.arr(1.asJson, 1.asJson, 2.asJson).as[NonEmptyDupelessSeq[Int]],
      Right(NonEmptyDupelessSeq.of(1, 2)),
    )
  }

  test("the nonEmptyDupelessSeqDecoder should error if decoding an empty array") {
    assertEquals(
      Json
        .arr()
        .as[NonEmptyDupelessSeq[Int]]
        .leftGet
        .message,
      "Empty array cannot be decoded to NonEmptyDupelessSeq",
    )
  }

}
