package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.classes.SafeGroupBy
import cats.data.NonEmptyList

trait ListInstances {

  implicit val safeGroupByForListUsingCatsNel: SafeGroupBy[List, NonEmptyList] =
    new SafeGroupBy.ForScalaIterable[List, NonEmptyList] {
      override def makeNecUnsafe[A](ca: List[A]): NonEmptyList[A] = NonEmptyList.fromListUnsafe(ca)
    }

}
