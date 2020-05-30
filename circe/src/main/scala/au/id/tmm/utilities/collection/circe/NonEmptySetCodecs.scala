package au.id.tmm.utilities.collection.circe

import au.id.tmm.utilities.collection.NonEmptySet
import io.circe.{Decoder, Encoder}

import scala.collection.immutable.ArraySeq

trait NonEmptySetCodecs extends NonEmptySetCodecs1 {

  /**
    * Provide an encoder for the special case where elements are ordered, so that the encoded Json is stable.
    */
  implicit def nonEmptySetEncoderOrdered[A : Encoder : Ordering]: Encoder[NonEmptySet[A]] =
    Encoder.encodeIterable[A, NonEmptySet](Encoder[A], _.to(ArraySeq.untagged).sorted)

  implicit def nonEmptySetDecoder[A : Decoder]: Decoder[NonEmptySet[A]] =
    NonEmptyCollectionCodecs.decoderFor("NonEmptySet", NonEmptySet.fromSet)

}

private[circe] trait NonEmptySetCodecs1 {
  implicit def nonEmptySetEncoder[A : Encoder]: Encoder[NonEmptySet[A]] =
    NonEmptyCollectionCodecs.encoderFor(_.underlying)
}
