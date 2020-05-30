package au.id.tmm.utilities.collection.circe

import io.circe.{Decoder, Encoder}

private[circe] object NonEmptyCollectionCodecs {

  def encoderFor[C[_], NEC[_], A](unWrap: NEC[A] => C[A])(implicit cEncoder: Encoder[C[A]]): Encoder[NEC[A]] =
    cEncoder.contramap(unWrap)

  def decoderFor[C[_], NEC[_], A](
    className: String,
    wrap: C[A] => Option[NEC[A]],
  )(implicit
    cDecoder: Decoder[C[A]],
  ): Decoder[NEC[A]] =
    Decoder[C[A]].emap { c =>
      wrap(c).toRight(s"Empty array cannot be decoded to $className")
    }

}
