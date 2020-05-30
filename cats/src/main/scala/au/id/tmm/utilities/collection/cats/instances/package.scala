package au.id.tmm.utilities.collection.cats

package object instances {

  object nonEmptySet         extends NonEmptySetInstances
  object dupelessSeq         extends DupelessSeqInstances
  object nonEmptyDupelessSeq extends NonEmptyDupelessSeqInstances
  object nonEmptyArraySeq    extends NonEmptyArraySeqInstances
  object list                extends ListInstances
  object vector              extends VectorInstances

  // TODO this is not possible because of collisions with unlawful instance names
//  object all
//      extends AnyRef
//      with NonEmptySetInstances
//      with DupelessSeqInstances
//      with NonEmptyDupelessSeqInstances
//      with ListInstances
//      with VectorInstances

  // TODO should do something like this
  // object unlawful
//        extends AnyRef
//        with NonEmptySetInstances.Unlawful

}
