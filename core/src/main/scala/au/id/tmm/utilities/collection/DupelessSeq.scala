package au.id.tmm.utilities.collection

import scala.annotation.unchecked.uncheckedVariance
import scala.collection.immutable.{AbstractSeq, ArraySeq, IndexedSeq, IndexedSeqOps, StrictOptimizedSeqOps}
import scala.collection.{IterableFactoryDefaults, SeqFactory, mutable}

/**
  * A sequence without duplicates, and a constant-time `contains` lookup.
  */
class DupelessSeq[+A] private (private val iterationOrder: ArraySeq[A], private val elements: Set[A @uncheckedVariance])
    extends AbstractSeq[A]
    with IndexedSeq[A]
    with IndexedSeqOps[A, DupelessSeq, DupelessSeq[A]]
    with StrictOptimizedSeqOps[A, DupelessSeq, DupelessSeq[A]]
    with IterableFactoryDefaults[A, DupelessSeq] {

  def toNonEmptyDupelessSeq: Option[NonEmptyDupelessSeq[A]] =
    if (nonEmpty) Some(NonEmptyDupelessSeq.fromDupelessSeqUnsafe(this)) else None

  override def iterableFactory: SeqFactory[DupelessSeq] = DupelessSeq

  override def length: Int = iterationOrder.length

  override def apply(idx: Int): A = iterationOrder(idx)

  override def reverse: DupelessSeq[A] = new DupelessSeq(
    iterationOrder = this.iterationOrder.reverse,
    elements = this.elements,
  )

  override def reverseIterator: Iterator[A] = this.iterationOrder.reverseIterator

  override def iterator: Iterator[A] = iterationOrder.iterator

  /**
    * Always returns a reference to this collection, since it will never contain duplicates
    */
  override def distinct: DupelessSeq[A] = this

  /**
    * Tests if some element is contained in this collection. This is a constant time operation.
    */
  @inline
  override def contains[A1 >: A](elem: A1): Boolean = elements.asInstanceOf[Set[Any]].contains(elem)

  /**
    * Appends the given element to this sequence if it is not already in the sequence. Otherwise, returns this sequence.
    */
  override def appended[B >: A](elem: B): DupelessSeq[B] = {
    if (this.contains(elem)) {
      return this
    }

    new DupelessSeq(
      iterationOrder = this.iterationOrder.appended(elem),
      elements = this.elements.asInstanceOf[Set[B]] + elem,
    )
  }

  /**
    * Appends the given element to this sequence if it is not already in the sequence. Otherwise, returns this sequence.
    */
  override def prepended[B >: A](elem: B): DupelessSeq[B] = {
    if (this.contains(elem)) {
      return this
    }

    new DupelessSeq(
      iterationOrder = this.iterationOrder.prepended(elem),
      elements = this.elements.asInstanceOf[Set[B]] + elem,
    )
  }

  override def splitAt(n: Int): (DupelessSeq[A], DupelessSeq[A]) = {
    val splitVectors = iterationOrder.splitAt(n)

    val leftSplit = new DupelessSeq[A](
      iterationOrder = splitVectors._1,
      elements = splitVectors._1.toSet,
    )

    val rightSplit = new DupelessSeq[A](
      iterationOrder = splitVectors._2,
      elements = splitVectors._2.toSet,
    )

    (leftSplit, rightSplit)
  }

  override def padTo[B >: A](len: Int, elem: B): DupelessSeq[B] =
    if (this.contains(elem)) {
      this
    } else {
      this appended elem
    }

  override def sorted[B >: A](implicit ord: Ordering[B]): DupelessSeq[A] = new DupelessSeq(
    iterationOrder = this.iterationOrder.sorted(ord),
    elements = this.elements,
  )

  def -[A1 >: A](elem: A1): DupelessSeq[A] =
    if (this.contains(elem)) {
      new DupelessSeq(
        iterationOrder = this.iterationOrder.filterNot(_ == elem),
        elements = this.elements - elem.asInstanceOf[A],
      )
    } else {
      this
    }

  override def toList: List[A] = this.iterationOrder.toList

  override def toSet[B >: A]: Set[B] = this.elements.asInstanceOf[Set[B]]

  def toArraySeq: ArraySeq[A] = this.iterationOrder

  override protected[this] def className: String = "DupelessSeq"

  override def canEqual(other: Any): Boolean = other.isInstanceOf[DupelessSeq[_]]

  override def equals(other: Any): Boolean = other match {
    case that: DupelessSeq[_] =>
      super.equals(that) &&
        (that canEqual this) &&
        iterationOrder == that.iterationOrder &&
        elements == that.elements
    case _ => false
  }

  override def hashCode(): Int = (iterationOrder, elements).hashCode()
}

object DupelessSeq extends SeqFactory[DupelessSeq] {
  private val EMPTY: DupelessSeq[Any] = new DupelessSeq(ArraySeq.empty, Set.empty)

  override def empty[A]: DupelessSeq[A] = EMPTY.asInstanceOf[DupelessSeq[A]]

  def apply[A](): DupelessSeq[A] = empty

  override def apply[A](elems: A*): DupelessSeq[A] =
    new DupelessSeq(elems.distinct.to(ArraySeq.untagged), elems.toSet)

  override def newBuilder[A]: DupelessSeqBuilder[A] = new DupelessSeqBuilder()

  class DupelessSeqBuilder[A] extends mutable.ReusableBuilder[A, DupelessSeq[A]] {
    private val set: mutable.Set[A]                    = mutable.Set()
    private val iterationOrder: mutable.ArrayBuffer[A] = mutable.ArrayBuffer()

    override def addOne(elem: A): DupelessSeqBuilder.this.type = {
      if (!set.contains(elem)) {
        set += elem
        iterationOrder += elem
      }

      this
    }

    override def clear(): Unit = {
      set.clear()
      iterationOrder.clear()
    }

    override def result(): DupelessSeq[A] =
      if (set.isEmpty) {
        DupelessSeq.empty
      } else {
        new DupelessSeq[A](iterationOrder.to(ArraySeq.untagged), set.toSet)
      }

    override def sizeHint(size: Int): Unit = {
      set.sizeHint(size)
      iterationOrder.sizeHint(size)
    }
  }

  override def from[A](source: IterableOnce[A]): DupelessSeq[A] = source match {
    case set: Set[A] => new DupelessSeq[A](set.to(ArraySeq.untagged), set)
    case i           => newBuilder.addAll(i).result()
  }

}
