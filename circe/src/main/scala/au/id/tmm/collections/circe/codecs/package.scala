package au.id.tmm.collections.circe

package object codecs {

  object nonEmptySet         extends NonEmptySetCodecs
  object dupelessSeq         extends DupelessSeqCodecs
  object nonEmptyDupelessSeq extends NonEmptyDupelessSeqCodecs
  object nonEmptyArraySeq    extends NonEmptyArraySeqCodecs
  object nonEmptyMap         extends NonEmptyMapCodecs
  object arraySeq            extends ArraySeqDecoders

  object all
      extends AnyRef
      with NonEmptySetCodecs
      with DupelessSeqCodecs
      with NonEmptyDupelessSeqCodecs
      with NonEmptyArraySeqCodecs
      with NonEmptyMapCodecs
      with ArraySeqDecoders

}
