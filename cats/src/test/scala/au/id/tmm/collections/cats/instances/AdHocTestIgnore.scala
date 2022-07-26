package au.id.tmm.collections.cats.instances

import munit.DisciplineSuite

trait AdHocTestIgnore extends DisciplineSuite {

  protected def ignoredTestNames: Set[String]

  override def munitTests(): Seq[Test] = {
    val superTests = super.munitTests()

    superTests.map { test =>
      if (ignoredTestNames.contains(test.name)) {
        test.withTags(test.tags + munit.Ignore)
      } else {
        test
      }
    }

  }

}
