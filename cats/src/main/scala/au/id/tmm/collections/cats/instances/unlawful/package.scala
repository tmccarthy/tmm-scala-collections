package au.id.tmm.collections.cats.instances

package object unlawful {

  object dupelessSeq         extends DupelessSeqUnlawfulInstances
  object nonEmptyDupelessSeq extends NonEmptyDupelessSeqUnlawfulInstances
  object nonEmptySet         extends NonEmptySetUnlawfulInstances
  object nonEmptyMap         extends NonEmptyMapUnlawfulInstances
  object applicative         extends ApplicativeUnlawfulInstances
  object map                 extends MapUnlawfulInstances

  object all
      extends AnyRef
      with DupelessSeqUnlawfulInstances
      with NonEmptyDupelessSeqUnlawfulInstances
      with NonEmptySetUnlawfulInstances
      with NonEmptyMapUnlawfulInstances
      with ApplicativeUnlawfulInstances

}
