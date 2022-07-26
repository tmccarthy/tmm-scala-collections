package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.DupelessSeq
import io.circe.Json
import io.circe.syntax.EncoderOps
import munit.FunSuite

class DupelessSeqCodecsSpec extends FunSuite {

  test("the dupelessSeqEncoder should encode a dupeless seq") {
    assertEquals(DupelessSeq(1, 2, 3).asJson, Json.arr(1.asJson, 2.asJson, 3.asJson))
  }

  test("the dupelessSeqDecoder should decode an array") {
    assertEquals(Json.arr(1.asJson, 2.asJson, 3.asJson).as[DupelessSeq[Int]], Right(DupelessSeq(1, 2, 3)))
  }

  test("the dupelessSeqDecoder should silently drop duplicates when decoding") {
    assertEquals(Json.arr(1.asJson, 1.asJson, 2.asJson).as[DupelessSeq[Int]], Right(DupelessSeq(1, 2)))
  }

}
