package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.NonEmptyArraySeq
import au.id.tmm.utilities.collection.cats.instances.ScalacheckInstances._
import au.id.tmm.utilities.collection.cats.instances.nonEmptyArraySeq._
import au.id.tmm.utilities.testing.AdHocTestIgnore
import cats.data.Validated
import cats.kernel.laws.discipline.{EqTests, HashTests, SemigroupTests}
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{AlignTests, BimonadTests, NonEmptyTraverseTests, SemigroupKTests}
import cats.tests.CatsSuite

class NonEmptyArraySeqInstancesLawsSpec extends CatsSuite with AdHocTestIgnore {

  checkAll("Hash for NonEmptyArraySeq", HashTests[NonEmptyArraySeq[Int]].hash)
  checkAll("Eq for NonEmptyArraySeq", EqTests[NonEmptyArraySeq[Int]](catsStdEqForTmmUtilsNonEmptyArraySeq).eqv)
  checkAll("Bimonad for NonEmptyArraySeq", BimonadTests[NonEmptyArraySeq].bimonad[Int, Int, Int])
  checkAll("SemigroupK for NonEmptyArraySeq", SemigroupKTests[NonEmptyArraySeq].semigroupK[Int])
  checkAll("Align for NonEmptyArraySeq", AlignTests[NonEmptyArraySeq].align[Int, Int, Int, Int])
  checkAll(
    "NonEmptyTraverse for NonEmptyArraySeq",
    NonEmptyTraverseTests[NonEmptyArraySeq].nonEmptyTraverse[Option, Int, Int, Int, Int, Option, Validated[Unit, *]])
  checkAll("Semigroup for NonEmptyArraySeq", SemigroupTests[NonEmptyArraySeq[Int]].semigroup)

  override protected val ignoredTestNames: Set[String] = Set(
    // TODO these should probably pass
    "NonEmptyTraverse for NonEmptyArraySeq.nonEmptyTraverse.forall is lazy",
    "NonEmptyTraverse for NonEmptyArraySeq.nonEmptyTraverse.foldRight is lazy",
    "NonEmptyTraverse for NonEmptyArraySeq.nonEmptyTraverse.exists is lazy",
  )

}
