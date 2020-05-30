package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptyDupelessSeq
import au.id.tmm.utilities.collection.cats.instances.ScalacheckInstances._
import au.id.tmm.utilities.collection.cats.instances.nonEmptyDupelessSeq._
import au.id.tmm.utilities.testing.AdHocTestIgnore
import cats.data.Validated
import cats.kernel.laws.discipline.{BandTests, HashTests}
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{MonadTests, NonEmptyTraverseTests, SemigroupKTests, SemigroupalTests}
import cats.tests.CatsSuite

class NonEmptyDupelessSeqInstancesLawsSpec extends CatsSuite with AdHocTestIgnore {

  checkAll("Hash for NonEmptyDupelessSeq", HashTests[NonEmptyDupelessSeq[Int]].hash)
  checkAll("Band for NonEmptyDupelessSeq", BandTests[NonEmptyDupelessSeq[Int]].band)
  checkAll("SemigroupK for NonEmptyDupelessSeq", SemigroupKTests[NonEmptyDupelessSeq].semigroupK[Int])
  checkAll(
    "NonEmptyTraverse for NonEmptyDupelessSeq",
    NonEmptyTraverseTests[NonEmptyDupelessSeq].nonEmptyTraverse[Option, Int, Int, Int, Int, Option, Validated[Unit, *]],
  )

  override protected val ignoredTestNames: Set[String] = Set(
    // TODO these should probably pass
    "NonEmptyTraverse for NonEmptyDupelessSeq.nonEmptyTraverse.forall is lazy",
    "NonEmptyTraverse for NonEmptyDupelessSeq.nonEmptyTraverse.foldRight is lazy",
    "NonEmptyTraverse for NonEmptyDupelessSeq.nonEmptyTraverse.exists is lazy",
  )

  // Unlawful tests below

  private implicit val dupelessSeqIsomorphism: SemigroupalTests.Isomorphisms[NonEmptyDupelessSeq] =
    SemigroupalTests.Isomorphisms.invariant[NonEmptyDupelessSeq](catsStdInstancesForNonEmptyDupelessSeq)

  checkAll(
    "Monad for NonEmptyDupelessSeq",
    MonadTests[NonEmptyDupelessSeq](unlawful.catsUnlawfulInstancesForNonEmptyDupelessSeq).monad[Int, Int, Int],
  )

}
