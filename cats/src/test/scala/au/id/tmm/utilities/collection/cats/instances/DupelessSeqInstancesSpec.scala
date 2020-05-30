package au.id.tmm.utilities.collection.cats.instances

import cats.syntax.monoid._
import au.id.tmm.utilities.collection.cats.instances.dupelessSeq._
import au.id.tmm.utilities.collection.DupelessSeq
import org.scalatest.flatspec.AnyFlatSpec

class DupelessSeqInstancesSpec extends AnyFlatSpec {

  "combineN" should "work for an empty DupelessSeq" in {
    assert(DupelessSeq().combineN(0) === DupelessSeq.empty)
  }

}
