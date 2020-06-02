package au.id.tmm.collections.circe

import au.id.tmm.collections.NonEmptyMap
import io.circe.{Decoder, Encoder, Json, KeyDecoder, KeyEncoder}
import io.circe.syntax.KeyOps

import scala.collection.immutable.ArraySeq

trait NonEmptyMapCodecs extends NonEmptyMapCodecs1 {

  /**
    * Provide an encoder for the special case where elements are ordered, so that the encoded Json is stable.
    */
  implicit def nonEmptyMapEncoderOrdered[K : KeyEncoder : Ordering, V : Encoder]: Encoder[NonEmptyMap[K, V]] =
    map =>
      Json.fromFields {
        map
          .to(ArraySeq)
          .sortBy(_._1)
          .map {
            case (k, v) => k := v
          }
      }

  implicit def nonEmptyMapDecoder[K : KeyDecoder, V : Decoder]: Decoder[NonEmptyMap[K, V]] =
    Decoder[Map[K, V]].emap(map => NonEmptyMap.fromMap(map).toRight("Empty array cannot be decoded to NonEmptyMap"))

}

private[circe] trait NonEmptyMapCodecs1 {
  implicit def nonEmptyMapEncoder[K : KeyEncoder, V : Encoder]: Encoder[NonEmptyMap[K, V]] =
    Encoder[Map[K, V]].contramap(_.underlying)
}
