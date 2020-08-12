package au.id.tmm.collections.circe.codecs

import au.id.tmm.collections.NonEmptyArraySeq
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.ArraySeq
import scala.reflect.ClassTag

trait NonEmptyArraySeqCodecs extends NonEmptyArraySeqCodecs1 {

  implicit def nonEmptyArraySeqEncoder[A : Encoder]: Encoder[NonEmptyArraySeq[A]] =
    NonEmptyCollectionCodecs.encoderFor(_.underlying)

  implicit def nonEmptyArraySeqDecoderTagged[A : Decoder : ClassTag]: Decoder[NonEmptyArraySeq[A]] =
    NonEmptyCollectionCodecs.decoderFor[ArraySeq, NonEmptyArraySeq, A](
      "NonEmptyArraySeq",
      NonEmptyArraySeq.fromArraySeq,
    )

}

private[circe] trait NonEmptyArraySeqCodecs1 {

  implicit def nonEmptyArraySeqDecoderUntagged[A : Decoder]: Decoder[NonEmptyArraySeq[A]] =
    NonEmptyCollectionCodecs.decoderFor[ArraySeq, NonEmptyArraySeq, A](
      "NonEmptyArraySeq",
      NonEmptyArraySeq.fromArraySeq,
    )

}
