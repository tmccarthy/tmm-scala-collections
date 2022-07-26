package au.id.tmm.collections.cats.instances

import munit.DisciplineSuite

// TODO
trait AdHocTestIgnore { this: DisciplineSuite =>

  protected def ignoredTestNames: Set[String]

}
