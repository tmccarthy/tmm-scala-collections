package au.id.tmm.collections.cats.instances

package object unlawful {

  object dupelessSeq         extends DupelessSeqUnlawfulInstances
  object nonEmptyDupelessSeq extends NonEmptyDupelessSeqUnlawfulInstances
  object nonEmptySet         extends NonEmptySetUnlawfulInstances
  object nonEmptyMap         extends NonEmptyMapUnlawfulInstances

  object all
      extends AnyRef
      with DupelessSeqUnlawfulInstances
      with NonEmptyDupelessSeqUnlawfulInstances
      with NonEmptySetUnlawfulInstances
      with NonEmptyMapUnlawfulInstances

  // TODO generic conversion from UnorderedTraverse to Traverse

}
