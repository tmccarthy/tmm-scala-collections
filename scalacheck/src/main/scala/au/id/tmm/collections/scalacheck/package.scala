package au.id.tmm.collections

package object scalacheck {

  object dupelessSeq extends DupelessSeqScalacheckInstances

  object all extends AnyRef with DupelessSeqScalacheckInstances

}
