package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.DupelessSeq
import au.id.tmm.collections.cats.instances.dupelessSeq._
import cats.syntax.monoid._
import org.scalatest.flatspec.AnyFlatSpec

class DupelessSeqInstancesSpec extends AnyFlatSpec {

  "combineN" should "work for an empty DupelessSeq" in {
    assert(DupelessSeq().combineN(0) === DupelessSeq.empty)
  }

}
