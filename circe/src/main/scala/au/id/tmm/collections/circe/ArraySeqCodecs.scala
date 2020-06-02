package au.id.tmm.collections.circe

import io.circe.{Decoder, Encoder}

import scala.collection.Factory
import scala.collection.immutable.ArraySeq

private[circe] object ArraySeqCodecs {

  private implicit def factory[A]: Factory[A, ArraySeq[A]] = ArraySeq.untagged

  implicit def arraySeqDecoder[A : Decoder]: Decoder[ArraySeq[A]] =
    Decoder.decodeIterable[A, ArraySeq]

  implicit def arraySeqEncoder[A : Encoder]: Encoder[ArraySeq[A]] =
    Encoder.encodeIterable[A, ArraySeq]

}
