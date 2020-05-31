package au.id.tmm.collections.cats.instances

import cats.syntax.monoid._
import au.id.tmm.collections.cats.instances.dupelessSeq._
import au.id.tmm.collections.DupelessSeq
import org.scalatest.flatspec.AnyFlatSpec

class DupelessSeqInstancesSpec extends AnyFlatSpec {

  "combineN" should "work for an empty DupelessSeq" in {
    assert(DupelessSeq().combineN(0) === DupelessSeq.empty)
  }

}
