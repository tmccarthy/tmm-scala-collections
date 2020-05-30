package au.id.tmm.utilities.collection.circe

import au.id.tmm.utilities.collection.DupelessSeq
import io.circe.Json
import io.circe.syntax.EncoderOps
import org.scalatest.FlatSpec

class DupelessSeqCodecsSpec extends FlatSpec {

  "the dupelessSeqEncoder" should "encode a dupeless seq" in {
    assert(DupelessSeq(1, 2, 3).asJson === Json.arr(1.asJson, 2.asJson, 3.asJson))
  }

  "the dupelessSeqDecoder" should "decode an array" in {
    assert(Json.arr(1.asJson, 2.asJson, 3.asJson).as[DupelessSeq[Int]] === Right(DupelessSeq(1, 2, 3)))
  }

  it should "silently drop duplicates when decoding" in {
    assert(Json.arr(1.asJson, 1.asJson, 2.asJson).as[DupelessSeq[Int]] === Right(DupelessSeq(1, 2)))
  }

}
