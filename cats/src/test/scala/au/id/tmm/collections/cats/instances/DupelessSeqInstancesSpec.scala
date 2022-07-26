package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.DupelessSeq
import au.id.tmm.collections.cats.instances.dupelessSeq._
import cats.syntax.monoid._
import munit.FunSuite

class DupelessSeqInstancesSpec extends FunSuite {

  test("combineN work for an empty DupelessSeq") {
    assertEquals(DupelessSeq().combineN(0), DupelessSeq.empty)
  }

}
