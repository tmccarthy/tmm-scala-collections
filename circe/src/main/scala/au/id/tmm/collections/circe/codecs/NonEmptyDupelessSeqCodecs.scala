package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.{DupelessSeq, NonEmptyDupelessSeq}
import io.circe.{Decoder, Encoder}

trait NonEmptyDupelessSeqCodecs {

  implicit def nonEmptyDupelessSeqEncoder[A : Encoder]: Encoder[NonEmptyDupelessSeq[A]] =
    NonEmptyCollectionCodecs.encoderFor(_.underlying)

  implicit def nonEmptyDupelessSeqDecoder[A : Decoder]: Decoder[NonEmptyDupelessSeq[A]] =
    NonEmptyCollectionCodecs.decoderFor[DupelessSeq, NonEmptyDupelessSeq, A](
      "NonEmptyDupelessSeq",
      NonEmptyDupelessSeq.fromDupelessSeq,
    )

}
