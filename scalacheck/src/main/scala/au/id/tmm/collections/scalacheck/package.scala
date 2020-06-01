package au.id.tmm.collections

package object scalacheck {

  object dupelessSeq         extends DupelessSeqScalacheckInstances
  object nonEmptyDupelessSeq extends NonEmptyDupelessSeqScalacheckInstances
  object nonEmptyArraySeq    extends NonEmptyArraySeqScalacheckInstances
  object nonEmptySet         extends NonEmptySetScalacheckInstances
  object nonEmptyMap         extends NonEmptyMapScalacheckInstances

  object all
      extends AnyRef
      with DupelessSeqScalacheckInstances
      with NonEmptyDupelessSeqScalacheckInstances
      with NonEmptyArraySeqScalacheckInstances
      with NonEmptySetScalacheckInstances
      with NonEmptyMapScalacheckInstances

}
