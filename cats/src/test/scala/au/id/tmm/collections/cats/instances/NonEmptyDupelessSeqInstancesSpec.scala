package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptyDupelessSeq
import au.id.tmm.collections.cats.instances.nonEmptyDupelessSeq._
import cats.kernel.Semigroup
import cats.syntax.show.toShow
import munit.FunSuite

class NonEmptyDupelessSeqInstancesSpec extends FunSuite {

  test("the semigroup instance for NonEmptyDupelessSeq can be found with a simple import") {
    val combined = Semigroup[NonEmptyDupelessSeq[Int]].combine(NonEmptyDupelessSeq.of(1), NonEmptyDupelessSeq.of(2))

    assertEquals(combined, NonEmptyDupelessSeq.of(1, 2))
  }

  test("the show instance for NonEmptyDupelessSeq should produce a sensible string") {
    assertEquals(NonEmptyDupelessSeq.of(1, 2).show, "NonEmptyDupelessSeq(1, 2)")
  }

}
