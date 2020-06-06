package au.id.tmm.collections.circe

import io.circe.Decoder

import scala.collection.immutable.ArraySeq
import scala.reflect.ClassTag

trait ArraySeqDecoders extends ArraySeqDecoders1 {

  implicit def arraySeqDecoderTagged[A : Decoder : ClassTag]: Decoder[ArraySeq[A]] =
    Decoder.decodeIterable(
      decodeA = Decoder[A],
      factory = ArraySeq.evidenceIterableFactory[A],
    )

}

private[circe] trait ArraySeqDecoders1 {

  implicit def arraySeqDecoderUntagged[A : Decoder]: Decoder[ArraySeq[A]] =
    Decoder.decodeIterable(
      decodeA = Decoder[A],
      factory = ArraySeq.untagged,
    )

}
