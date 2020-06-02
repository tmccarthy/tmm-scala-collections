package au.id.tmm.collections.cats

package object instances {

  object nonEmptySet         extends NonEmptySetInstances
  object dupelessSeq         extends DupelessSeqInstances
  object nonEmptyDupelessSeq extends NonEmptyDupelessSeqInstances
  object nonEmptyArraySeq    extends NonEmptyArraySeqInstances
  object list                extends ListInstances
  object vector              extends VectorInstances
  object mapOverKeys         extends MapOverKeysInstances

  object all
      extends AnyRef
      with NonEmptySetInstances
      with DupelessSeqInstances
      with NonEmptyDupelessSeqInstances
      with ListInstances
      with VectorInstances
      with NonEmptyArraySeqInstances
      with MapOverKeysInstances

}
