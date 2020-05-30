package au.id.tmm.utilities.collection.circe

import au.id.tmm.utilities.collection.NonEmptyArraySeq
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.ArraySeq

trait NonEmptyArraySeqCodecs {

  import ArraySeqCodecs._

  implicit def nonEmptyArraySeqEncoder[A : Encoder]: Encoder[NonEmptyArraySeq[A]] =
    NonEmptyCollectionCodecs.encoderFor(_.underlying)

  implicit def nonEmptyArraySeqDecoder[A : Decoder]: Decoder[NonEmptyArraySeq[A]] =
    NonEmptyCollectionCodecs.decoderFor[ArraySeq, NonEmptyArraySeq, A](
      "NonEmptyArraySeq",
      NonEmptyArraySeq.fromArraySeq)

}
