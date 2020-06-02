package au.id.tmm.collections.cats.instances

package object unlawful {

  object dupelessSeq         extends DupelessSeqUnlawfulInstances
  object nonEmptyDupelessSeq extends NonEmptyDupelessSeqUnlawfulInstances
  object nonEmptySet         extends NonEmptySetUnlawfulInstances

  object all
      extends AnyRef
      with DupelessSeqUnlawfulInstances
      with NonEmptyDupelessSeqUnlawfulInstances
      with NonEmptySetUnlawfulInstances

}
