package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.DupelessSeq
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.ArraySeq

trait DupelessSeqCodecs {

  implicit def dupelessSeqEncoder[A : Encoder]: Encoder[DupelessSeq[A]] =
    Encoder[ArraySeq[A]].contramap(_.toArraySeq)

  implicit def dupelessSeqDecoder[A : Decoder]: Decoder[DupelessSeq[A]] =
    Decoder[ArraySeq[A]].map(DupelessSeq.from)

}
