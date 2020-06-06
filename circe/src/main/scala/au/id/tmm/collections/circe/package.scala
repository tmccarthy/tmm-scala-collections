package au.id.tmm.collections

package object circe
    extends AnyRef
    with NonEmptySetCodecs
    with DupelessSeqCodecs
    with NonEmptyDupelessSeqCodecs
    with NonEmptyArraySeqCodecs
    with NonEmptyMapCodecs
    with ArraySeqDecoders
