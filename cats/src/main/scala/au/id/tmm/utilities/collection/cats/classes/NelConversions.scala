package au.id.tmm.utilities.collection.cats.classes

import au.id.tmm.utilities.collection
import au.id.tmm.utilities.collection.{NonEmptyArraySeq, NonEmptyDupelessSeq, NonEmptySet}
import cats.data.NonEmptyList

import scala.reflect.ClassTag

trait NelConversions[NEC[_]] {

  def toNel[A](nec: NEC[A]): NonEmptyList[A]
  def fromNel[A](nel: NonEmptyList[A]): NEC[A]

}

object NelConversions {

  def apply[NEC[_]](implicit nelConversions: NelConversions[NEC]): NelConversions[NEC] = nelConversions

  trait Specialised[NEC[_]] extends NelConversions[NEC] {
    def fromNelSpecialised[A : ClassTag](nel: NonEmptyList[A]): NEC[A]
  }

  class Ops[NEC[_], A](nec: NEC[A])(implicit nelConversions: NelConversions[NEC]) {
    def toNel: NonEmptyList[A] = nelConversions.toNel(nec)
  }

  class CatsNonEmptyListOps[A](nel: NonEmptyList[A]) {
    def to[NEC[_]](implicit nelConversions: NelConversions[NEC]): NEC[A] = nelConversions.fromNel(nel)
    def toSpecialised[NEC[_]](implicit nelConversions: NelConversions.Specialised[NEC], classTag: ClassTag[A]): NEC[A] =
      nelConversions.fromNelSpecialised(nel)
  }

  trait ToNelConversionsOps {
    implicit def toNelConversionsOps[NEC[_] : NelConversions, A](nec: NEC[A]): Ops[NEC, A] = new Ops(nec)
  }

  trait ToFromNelConversionsOps {
    implicit def toFromNelConversionsOps[A](nel: NonEmptyList[A]): CatsNonEmptyListOps[A] = new CatsNonEmptyListOps(nel)
  }

  implicit val nelConversionsForNonEmptyDupelessSeq: NelConversions[NonEmptyDupelessSeq] =
    new NelConversions[collection.NonEmptyDupelessSeq] {
      override def toNel[A](nec: collection.NonEmptyDupelessSeq[A]): NonEmptyList[A] =
        NonEmptyList.fromListUnsafe(nec.toList)
      override def fromNel[A](nel: NonEmptyList[A]): collection.NonEmptyDupelessSeq[A] =
        NonEmptyDupelessSeq.fromHeadTail(nel.head, nel.tail)
    }

  implicit val nelConversionsForTmmUtilsNonEmptySet: NelConversions[NonEmptySet] =
    new NelConversions[collection.NonEmptySet] {
      override def toNel[A](nec: collection.NonEmptySet[A]): NonEmptyList[A] = NonEmptyList.fromListUnsafe(nec.toList)
      override def fromNel[A](nel: NonEmptyList[A]): collection.NonEmptySet[A] =
        NonEmptySet.fromHeadTail(nel.head, nel.tail)
    }

  implicit val nelConversionsForTmmUtilsNonEmptyArraySeq: NelConversions.Specialised[NonEmptyArraySeq] =
    new NelConversions.Specialised[collection.NonEmptyArraySeq] {
      override def toNel[A](nec: collection.NonEmptyArraySeq[A]): NonEmptyList[A] =
        NonEmptyList.fromListUnsafe(nec.toList)
      override def fromNel[A](nel: NonEmptyList[A]): collection.NonEmptyArraySeq[A] =
        NonEmptyArraySeq.untagged.fromHeadTail(nel.head, nel.tail)
      override def fromNelSpecialised[A : ClassTag](nel: NonEmptyList[A]): NonEmptyArraySeq[A] =
        NonEmptyArraySeq.fromHeadTail(nel.head, nel.tail)
    }

}
