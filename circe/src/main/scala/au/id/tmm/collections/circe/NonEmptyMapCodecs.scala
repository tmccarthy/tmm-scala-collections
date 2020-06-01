package au.id.tmm.collections.circe

import au.id.tmm.collections.NonEmptyMap
import io.circe.{Decoder, Encoder, KeyDecoder, KeyEncoder}

trait NonEmptyMapCodecs extends NonEmptyMapCodecs1 {

  /**
    * Provide an encoder for the special case where elements are ordered, so that the encoded Json is stable.
    */
  implicit def nonEmptySetEncoderOrdered[K : KeyEncoder : Ordering, V : Encoder]: Encoder[NonEmptyMap[K, V]] =
    ???

  implicit def nonEmptySetDecoder[K : KeyDecoder, V : Decoder]: Decoder[NonEmptyMap[K, V]] =
    ???

}

private[circe] trait NonEmptyMapCodecs1 {
  implicit def nonEmptySetEncoder[K : KeyEncoder, V : Encoder]: Encoder[NonEmptyMap[K, V]] =
    ???
}
