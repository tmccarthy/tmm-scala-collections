package au.id.tmm.utilities.collection

import scala.collection.{AbstractSet, View}
import scala.collection.immutable.Set

/**
  * A Set that is guaranteed to be non-empty. Equality of elements is based on universal equality.
  */
final class NonEmptySet[A] private (underlying: Set[A]) extends AbstractSet[A] with Set[A] with Serializable {

  override def toSet[B >: A]: Set[B] = underlying.toSet

  override def incl(elem: A): NonEmptySet[A] = new NonEmptySet(underlying.incl(elem))

  override def excl(elem: A): Set[A] = underlying.excl(elem)

  override def contains(elem: A): Boolean = underlying.contains(elem)

  override def iterator: Iterator[A] = underlying.iterator

  override def equals(that: Any): Boolean = this.underlying equals that

  override def hashCode(): Int = this.underlying.hashCode()

  override def diff(that: collection.Set[A]): Set[A] = this.underlying.diff(that)

  override def removedAll(that: IterableOnce[A]): Set[A] = this.underlying.removedAll(that)

  override def subsetOf(that: collection.Set[A]): Boolean = this.underlying.subsetOf(that)

  override def subsets(len: Int): Iterator[Set[A]] = this.underlying.subsets(len)

  override def subsets(): Iterator[Set[A]] = this.underlying.subsets

  override def intersect(that: collection.Set[A]): Set[A] = this.underlying.intersect(that)

  override def concat(that: IterableOnce[A]): NonEmptySet[A] = new NonEmptySet(this.underlying.concat(that))

  override def isTraversableAgain: Boolean = this.underlying.isTraversableAgain

  override def head: A = this.underlying.head

  override def headOption: Option[A] = Some(this.underlying.head)

  override def last: A = this.underlying.last

  override def lastOption: Option[A] = Some(this.underlying.last)

  override def view: View[A] = this.underlying.view

  override def sizeCompare(otherSize: Int): Int = this.underlying.sizeCompare(otherSize)

  override def sizeCompare(that: Iterable[_]): Int = this.underlying.sizeCompare(that)

  override def toString: String = mkString("NonEmptySet(", ", ", ")")
}

object NonEmptySet {

  def of[A](head: A, tail: A*): NonEmptySet[A] = {
    val builder = Set.newBuilder[A].addOne(head)
    builder.addAll(tail)
    new NonEmptySet(builder.result())
  }

  def one[A](head: A): NonEmptySet[A] = new NonEmptySet(Set(head))

  def fromSet[A](set: Set[A]): Option[NonEmptySet[A]] =
    if (set.isEmpty) None else Some(new NonEmptySet(set))

  def fromSetUnsafe[A](set: Set[A]): NonEmptySet[A] =
    if (set.isEmpty)
      throw new IllegalArgumentException("Cannot create NonEmptySet from empty set")
    else
      new NonEmptySet(set)

}