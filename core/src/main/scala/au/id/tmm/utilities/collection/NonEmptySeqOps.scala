package au.id.tmm.utilities.collection

import scala.collection.immutable.SeqOps
import scala.collection.{IterableOnce, Searching, SeqView}

trait NonEmptySeqOps[C[+X] <: SeqOps[X, C, C[X]], NEC[+_], +A] extends NonEmptyIterableOps[C, NEC, A] {
  def apply(i: Int): A = underlying.apply(i)

  override def view: SeqView[A] = underlying.view

  def prepended[B >: A](elem: B): NEC[B] = constructor(underlying.prepended(elem))

  @inline final def +:[B >: A](elem: B): NEC[B] = prepended(elem)

  def appended[B >: A](elem: B): NEC[B] = constructor(underlying.appended(elem))

  @inline final def :+[B >: A](elem: B): NEC[B] = appended(elem)

  def prependedAll[B >: A](prefix: IterableOnce[B]): NEC[B] = constructor(underlying.prependedAll(prefix))

  def prependedAll[B >: A](prefix: NEC[B]): NEC[B] = constructor(underlying.prependedAll(unwrap(prefix)))

  @inline final def ++:[B >: A](prefix: IterableOnce[B]): NEC[B] = prependedAll(prefix)

  @inline final def ++:[B >: A](prefix: NEC[B]): NEC[B] = prependedAll(prefix)

  def appendedAll[B >: A](suffix: IterableOnce[B]): NEC[B] = constructor(underlying.appendedAll(suffix))

  def appendedAll[B >: A](suffix: NEC[B]): NEC[B] = constructor(underlying.appendedAll(unwrap(suffix)))

  @inline final def :++[B >: A](suffix: IterableOnce[B]): NEC[B] = appendedAll(suffix)

  @inline final def :++[B >: A](suffix: NEC[B]): NEC[B] = appendedAll(suffix)

  @inline override final def concat[B >: A](suffix: IterableOnce[B]): NEC[B] = appendedAll(suffix)

  @inline override final def concat[B >: A](suffix: NEC[B]): NEC[B] = appendedAll(suffix)

  def distinct: NEC[A] = constructor(underlying.distinct)

  def distinctBy[B](f: A => B): NEC[A] = constructor(underlying.distinctBy[B](f))

  def reverse: NEC[A] = constructor(underlying.reverse)

  def reverseIterator: Iterator[A] = underlying.reverseIterator

  def startsWith[B >: A](that: IterableOnce[B], offset: Int): Boolean = underlying.startsWith(that, offset)

  def endsWith[B >: A](that: Iterable[B]): Boolean = underlying.endsWith(that)

  def isDefinedAt(idx: Int): Boolean = underlying.isDefinedAt(idx)

  def padTo[B >: A](len: Int, elem: B): C[B] = underlying.padTo(len, elem)

  def segmentLength(p: A => Boolean, from: Int): Int = underlying.segmentLength(p, from)

  def indexWhere(p: A => Boolean, from: Int): Int = underlying.indexWhere(p, from)

  def indexWhere(p: A => Boolean): Int = underlying.indexWhere(p)

  def indexOf[B >: A](elem: B, from: Int): Int = underlying.indexOf(elem, from)

  def indexOf[B >: A](elem: B): Int = underlying.indexOf(elem)

  def lastIndexOf[B >: A](elem: B, end: Int): Int = underlying.lastIndexOf(elem, end)

  def lastIndexWhere(p: A => Boolean, end: Int): Int = underlying.lastIndexWhere(p, end)

  def lastIndexWhere(p: A => Boolean): Int = underlying.lastIndexWhere(p)

  def indexOfSlice[B >: A](that: collection.Seq[B], from: Int): Int = underlying.indexOfSlice(that, from)

  def indexOfSlice[B >: A](that: collection.Seq[B]): Int = underlying.indexOfSlice(that)

  def lastIndexOfSlice[B >: A](that: collection.Seq[B], end: Int): Int = underlying.lastIndexOfSlice(that, end)

  def lastIndexOfSlice[B >: A](that: collection.Seq[B]): Int = underlying.lastIndexOfSlice(that)

  def findLast(p: A => Boolean): Option[A] = underlying.findLast(p)

  def containsSlice[B](that: collection.Seq[B]): Boolean = underlying.containsSlice(that)

  def contains[A1 >: A](elem: A1): Boolean = underlying.contains(elem)

  def permutations: Iterator[NEC[A]] = underlying.permutations.map(constructor)

  def combinations(n: Int): Iterator[C[A]] = underlying.combinations(n)

  def sorted[B >: A](implicit ord: Ordering[B]): NEC[A] = constructor(underlying.sorted[B])

  def sortWith(lt: (A, A) => Boolean): NEC[A] = constructor(underlying.sortWith(lt))

  def sortBy[B](f: A => B)(implicit ord: Ordering[B]): NEC[A] = constructor(underlying.sortBy(f))

  def indices: Range = underlying.indices

  def lengthCompare(len: Int): Int = underlying.lengthCompare(len)

  def lengthCompare(that: Iterable[_]): Int = underlying.lengthCompare(that)

  def sameElements[B >: A](that: IterableOnce[B]): Boolean = underlying.sameElements(that)

  def corresponds[B](that: collection.Seq[B])(p: (A, B) => Boolean): Boolean = underlying.corresponds(that)(p)

  def diff[B >: A](that: collection.Seq[B]): C[A] = underlying.diff(that)

  def diff[B >: A](that: NEC[B]): C[A] = underlying.diff[B](unwrap(that).toSeq)

  def intersect[B >: A](that: collection.Seq[B]): C[A] = underlying.intersect(that)

  def intersect[B >: A](that: NEC[B]): C[A] = underlying.intersect[B](unwrap(that).toSeq)

  def patch[B >: A](
    from: Int,
    other: IterableOnce[B],
    replaced: Int,
  ): C[B] = underlying.patch(from, other, replaced)

  def updated[B >: A](index: Int, elem: B): NEC[B] = constructor(underlying.updated(index, elem))

  def search[B >: A](elem: B)(implicit ord: Ordering[B]): Searching.SearchResult = underlying.search(elem)

  def search[B >: A](
    elem: B,
    from: Int,
    to: Int,
  )(implicit
    ord: Ordering[B],
  ): Searching.SearchResult = underlying.search(elem, from, to)

}
