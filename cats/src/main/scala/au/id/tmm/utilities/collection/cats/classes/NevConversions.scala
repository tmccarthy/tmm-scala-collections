package au.id.tmm.utilities.collection.cats.classes

import au.id.tmm.utilities.collection
import au.id.tmm.utilities.collection.{NonEmptyArraySeq, NonEmptyDupelessSeq, NonEmptySet}
import cats.data.NonEmptyVector

import scala.reflect.ClassTag

trait NevConversions[NEC[_]] {

  def toNev[A](nec: NEC[A]): NonEmptyVector[A]
  def fromNev[A](nev: NonEmptyVector[A]): NEC[A]

}

object NevConversions {

  def apply[NEC[_]](implicit nevConversions: NevConversions[NEC]): NevConversions[NEC] = nevConversions

  trait Specialised[NEC[_]] extends NevConversions[NEC] {
    def fromNevSpecialised[A : ClassTag](nev: NonEmptyVector[A]): NEC[A]
  }

  class Ops[NEC[_], A](nec: NEC[A])(implicit nevConversions: NevConversions[NEC]) {
    def toNev: NonEmptyVector[A] = nevConversions.toNev(nec)
  }

  class CatsNonEmptyVectorOps[A](nev: NonEmptyVector[A]) {
    def to[NEC[_]](implicit nevConversions: NevConversions[NEC]): NEC[A] = nevConversions.fromNev(nev)
    def toSpecialised[NEC[_]](implicit nevConversions: NevConversions.Specialised[NEC], classTag: ClassTag[A]): NEC[A] =
      nevConversions.fromNevSpecialised(nev)
  }

  trait ToNevConversionsOps {
    implicit def toNevConversionsOps[NEC[_] : NevConversions, A](nec: NEC[A]): Ops[NEC, A] = new Ops(nec)
  }

  trait ToFromNevConversionsOps {
    implicit def toFromNevConversionsOps[A](nev: NonEmptyVector[A]): CatsNonEmptyVectorOps[A] =
      new CatsNonEmptyVectorOps(nev)
  }

  implicit val nevConversionsForNonEmptyDupelessSeq: NevConversions[NonEmptyDupelessSeq] =
    new NevConversions[collection.NonEmptyDupelessSeq] {
      override def toNev[A](nec: collection.NonEmptyDupelessSeq[A]): NonEmptyVector[A] =
        NonEmptyVector.fromVectorUnsafe(nec.toVector)
      override def fromNev[A](nev: NonEmptyVector[A]): collection.NonEmptyDupelessSeq[A] =
        NonEmptyDupelessSeq.fromHeadTail(nev.head, nev.tail)
    }

  implicit val nevConversionsForTmmUtilsNonEmptySet: NevConversions[NonEmptySet] =
    new NevConversions[collection.NonEmptySet] {
      override def toNev[A](nec: collection.NonEmptySet[A]): NonEmptyVector[A] =
        NonEmptyVector.fromVectorUnsafe(nec.toVector)
      override def fromNev[A](nev: NonEmptyVector[A]): collection.NonEmptySet[A] =
        NonEmptySet.fromHeadTail(nev.head, nev.tail)
    }

  implicit val nevConversionsForTmmUtilsNonEmptyArraySeq: NevConversions.Specialised[NonEmptyArraySeq] =
    new NevConversions.Specialised[collection.NonEmptyArraySeq] {
      override def toNev[A](nec: collection.NonEmptyArraySeq[A]): NonEmptyVector[A] =
        NonEmptyVector.fromVectorUnsafe(nec.toVector)
      override def fromNev[A](nev: NonEmptyVector[A]): collection.NonEmptyArraySeq[A] =
        NonEmptyArraySeq.untagged.fromHeadTail(nev.head, nev.tail)
      override def fromNevSpecialised[A : ClassTag](nev: NonEmptyVector[A]): NonEmptyArraySeq[A] =
        NonEmptyArraySeq.fromHeadTail(nev.head, nev.tail)
    }

}
