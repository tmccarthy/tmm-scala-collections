package au.id.tmm.utilities.collection

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

class NonEmptyDupelessSeq[+A] private (val underlying: DupelessSeq[A])
    extends NonEmptySeqOps[DupelessSeq, NonEmptyDupelessSeq, A] {

  override protected def constructor[X](cx: DupelessSeq[X]): NonEmptyDupelessSeq[X] = new NonEmptyDupelessSeq[X](cx)

  override protected def unwrap[X](necX: NonEmptyDupelessSeq[X]): DupelessSeq[X] = necX.underlying

  override def companion: NonEmptyIterableCompanion[DupelessSeq, NonEmptyDupelessSeq] = NonEmptyDupelessSeq

  def -[A1 >: A](elem: A1): DupelessSeq[A] =
    underlying.-(elem)

  override def toSet[B >: A]: Set[B] = underlying.toSet

  def toNonEmptySet[B >: A]: NonEmptySet[B] = NonEmptySet.fromSetUnsafe(underlying.toSet)

  def toArraySeq: ArraySeq[A] = underlying.toArraySeq

  override def equals(other: Any): Boolean = other match {
    case that: NonEmptyDupelessSeq[_] =>
      this.underlying == that.underlying
    case _ => false
  }

  override def hashCode(): Int = underlying.hashCode()

}

object NonEmptyDupelessSeq extends NonEmptyIterableCompanion[DupelessSeq, NonEmptyDupelessSeq] {

  override protected[collection] def className: String = "NonEmptyDupelessSeq"

  override protected[collection] def constructor[A](ca: DupelessSeq[A]): NonEmptyDupelessSeq[A] =
    new NonEmptyDupelessSeq[A](ca)

  override protected def newUnderlyingBuilder[A]: mutable.Builder[A, DupelessSeq[A]] = DupelessSeq.newBuilder

  override def one[A](head: A): NonEmptyDupelessSeq[A] = new NonEmptyDupelessSeq[A](DupelessSeq(head))

  def fromDupelessSeq[A](set: DupelessSeq[A]): Option[NonEmptyDupelessSeq[A]] = this.fromUnderlying(set)

  def fromDupelessSeqUnsafe[A](set: DupelessSeq[A]): NonEmptyDupelessSeq[A] = this.fromUnderlyingUnsafe(set)

  override def fromIterable[A](iterable: IterableOnce[A]): Option[NonEmptyDupelessSeq[A]] =
    iterable match {
      case s: DupelessSeq[A] => fromDupelessSeq(s)
      case i: Iterable[A]    => super.fromIterable(i)
    }

}
