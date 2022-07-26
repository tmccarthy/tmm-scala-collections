package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.NonEmptyArraySeq
import au.id.tmm.collections.circe.codecs.all._
import au.id.tmm.utilities.testing.syntax._
import io.circe.Json
import io.circe.syntax.EncoderOps
import munit.FunSuite

import scala.collection.immutable.ArraySeq

class NonEmptyArraySeqCodecsSpec extends FunSuite {
  test("the nonEmptyArraySeqEncoder should encode a dupeless seq") {
    assertEquals(NonEmptyArraySeq.of(1, 2, 3).asJson, Json.arr(1.asJson, 2.asJson, 3.asJson))
  }

  test("the nonEmptyArraySeqDecoder should decode an array") {
    assertEquals(Json.arr(1.asJson, 2.asJson, 3.asJson).as[NonEmptyArraySeq[Int]], Right(NonEmptyArraySeq.of(1, 2, 3)))
  }

  test("the nonEmptyArraySeqDecoder should error if decoding an empty array") {
    assertEquals(
      Json
        .arr()
        .as[NonEmptyArraySeq[Int]]
        .leftGet
        .message,
      "Empty array cannot be decoded to NonEmptyArraySeq",
    )
  }

  test("the nonEmptyArraySeqDecoder should specialise the underlying ArraySeq if a classtag is available") {
    assertEquals(Json.arr(1.asJson).as[NonEmptyArraySeq[Int]].get.underlying.getClass.asInstanceOf[Class[Any]], classOf[ArraySeq.ofInt].asInstanceOf[Class[Any]])
  }

}
