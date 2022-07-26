package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptyArraySeq
import au.id.tmm.collections.cats.instances.nonEmptyArraySeq._
import au.id.tmm.collections.scalacheck.nonEmptyArraySeq._
import cats.data.Validated
import cats.kernel.laws.discipline.{EqTests, HashTests, SemigroupTests}
import cats.laws.discipline.arbitrary._
import cats.laws.discipline.{AlignTests, BimonadTests, NonEmptyTraverseTests, SemigroupKTests}
import munit.DisciplineSuite

class NonEmptyArraySeqInstancesLawsSpec extends DisciplineSuite {

  checkAll("Eq for NonEmptyArraySeq", EqTests[NonEmptyArraySeq[EqOnlyInt]].eqv)
  checkAll("Hash for NonEmptyArraySeq", HashTests[NonEmptyArraySeq[Int]].hash)
  checkAll("Bimonad for NonEmptyArraySeq", BimonadTests[NonEmptyArraySeq].bimonad[Int, Int, Int])
  checkAll("SemigroupK for NonEmptyArraySeq", SemigroupKTests[NonEmptyArraySeq].semigroupK[Int])
  checkAll("Align for NonEmptyArraySeq", AlignTests[NonEmptyArraySeq].align[Int, Int, Int, Int])
  checkAll(
    "NonEmptyTraverse for NonEmptyArraySeq",
    NonEmptyTraverseTests[NonEmptyArraySeq].nonEmptyTraverse[Option, Int, Int, Int, Int, Option, Validated[Unit, *]],
  )
  checkAll("Semigroup for NonEmptyArraySeq", SemigroupTests[NonEmptyArraySeq[Int]].semigroup)

}
