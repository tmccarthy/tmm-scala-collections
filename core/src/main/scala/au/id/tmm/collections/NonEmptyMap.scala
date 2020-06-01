package au.id.tmm.collections

import scala.collection.{Factory, MapView, View, immutable, mutable}
import scala.reflect.ClassTag

final class NonEmptyMap[K, +V] private (val underlying: Map[K, V])
    extends PartialFunction[K, V]
    with IterableOnce[(K, V)] {

  private def nonEmptyArraySeqFrom[X](view: View[X]): NonEmptyArraySeq[X] =
    NonEmptyArraySeq.untagged.fromIterableUnsafe(view)

  def removed(key: K): Map[K, V] = underlying.removed(key)

  def updated[V1 >: V](key: K, value: V1): NonEmptyMap[K, V1] = new NonEmptyMap(underlying.updated(key, value))

  def get(key: K): Option[V] = underlying.get(key)

  def toIterable: Iterable[(K, V)] = underlying

  def iterator: Iterator[(K, V)] = underlying.iterator

  override def unapply(a: K): Option[V] = underlying.unapply(a)

  def removedAll(keys: IterableOnce[K]): Map[K, V] = underlying.removedAll(keys)

  def updatedWith[V1 >: V](key: K)(remappingFunction: Option[V] => Option[V1]): NonEmptyMap[K, V1] =
    new NonEmptyMap(underlying.updatedWith[V1](key)(remappingFunction))

  def +[V1 >: V](kv: (K, V1)): NonEmptyMap[K, V1] = new NonEmptyMap(underlying.+(kv))

  def transform[W](f: (K, V) => W): NonEmptyMap[K, W] = new NonEmptyMap(underlying.transform(f))

  def keySet: NonEmptySet[K] = NonEmptySet.fromSetUnsafe(underlying.keySet)

  def view: MapView[K, V] = underlying.view

  def getOrElse[V1 >: V](key: K, default: => V1): V1 = underlying.getOrElse(key, default)

  override def apply(key: K): V = underlying.apply(key)

  override def applyOrElse[K1 <: K, V1 >: V](x: K1, default: K1 => V1): V1 = underlying.applyOrElse(x, default)

  def keys: Iterable[K] = underlying.keys

  def values: Iterable[V] = underlying.values

  def keysIterator: Iterator[K] = underlying.keysIterator

  def valuesIterator: Iterator[V] = underlying.valuesIterator

  def foreachEntry[U](f: (K, V) => U): Unit = underlying.foreachEntry(f)

  def contains(key: K): Boolean = underlying.contains(key)

  def isDefinedAt(key: K): Boolean = underlying.isDefinedAt(key)

  def map[K2, V2](f: ((K, V)) => (K2, V2)): NonEmptyMap[K2, V2] = new NonEmptyMap(underlying.map(f))

  def collect[K2, V2](pf: PartialFunction[(K, V), (K2, V2)]): Map[K2, V2] = underlying.collect(pf)

  def flatMap[K2, V2](f: ((K, V)) => NonEmptyMap[K2, V2]): NonEmptyMap[K2, V2] =
    new NonEmptyMap(underlying.flatMap[K2, V2](f))

  def flatMap[K2, V2](f: ((K, V)) => IterableOnce[(K2, V2)]): Map[K2, V2] = underlying.flatMap(f)

  def concat[V2 >: V](suffix: IterableOnce[(K, V2)]): NonEmptyMap[K, V2] = new NonEmptyMap(underlying.concat(suffix))

  def ++[V2 >: V](xs: IterableOnce[(K, V2)]): NonEmptyMap[K, V2] = new NonEmptyMap(underlying.++(xs))

  def head: (K, V) = underlying.head

  def last: (K, V) = underlying.last

  def sizeCompare(otherSize: Int): Int = underlying.sizeCompare(otherSize)

  def sizeCompare(that: Iterable[_]): Int = underlying.sizeCompare(that)

  def filter(pred: ((K, V)) => Boolean): Map[K, V] = underlying.filter(pred)

  def filterNot(pred: ((K, V)) => Boolean): Map[K, V] = underlying.filterNot(pred)

  def partition(p: ((K, V)) => Boolean): (Map[K, V], Map[K, V]) = underlying.partition(p)

  def splitAt(n: Int): (Map[K, V], Map[K, V]) = underlying.splitAt(n)

  def take(n: Int): Map[K, V] = underlying.take(n)

  def takeRight(n: Int): Map[K, V] = underlying.takeRight(n)

  def takeWhile(p: ((K, V)) => Boolean): Map[K, V] = underlying.takeWhile(p)

  def span(p: ((K, V)) => Boolean): (Map[K, V], Map[K, V]) = underlying.span(p)

  def drop(n: Int): Map[K, V] = underlying.drop(n)

  def dropRight(n: Int): Map[K, V] = underlying.dropRight(n)

  def dropWhile(p: ((K, V)) => Boolean): Map[K, V] = underlying.dropWhile(p)

  def grouped(size: Int): Iterator[Map[K, V]] = underlying.grouped(size)

  def sliding(size: Int): Iterator[Map[K, V]] = underlying.sliding(size)

  def sliding(size: Int, step: Int): Iterator[Map[K, V]] = underlying.sliding(size, step)

  def tail: Map[K, V] = underlying.tail

  def init: Map[K, V] = underlying.init

  def slice(from: Int, until: Int): Map[K, V] = underlying.slice(from, until)

  def groupBy[K2](f: ((K, V)) => K2): Map[K2, Iterable[(K, V)]] = underlying.groupBy(f)

  def groupMap[K2, B](key: ((K, V)) => K2)(f: ((K, V)) => B): Map[K2, immutable.Iterable[B]] =
    underlying.groupMap(key)(f)

  def groupMapReduce[K2, B](key: ((K, V)) => K2)(f: ((K, V)) => B)(reduce: (B, B) => B): Map[K2, B] =
    underlying.groupMapReduce(key)(f)(reduce)

  def scan[B >: (K, V)](z: B)(op: (B, B) => B): immutable.Iterable[B] = underlying.scan(z)(op)

  def scanLeft[B](z: B)(op: (B, (K, V)) => B): immutable.Iterable[B] = underlying.scanLeft(z)(op)

  def scanRight[B](z: B)(op: ((K, V), B) => B): immutable.Iterable[B] = underlying.scanRight(z)(op)

  def map[B](f: ((K, V)) => B): NonEmptyArraySeq[B] = nonEmptyArraySeqFrom(new View.Map(underlying, f))

  def flatMap[B](f: ((K, V)) => IterableOnce[B]): immutable.Iterable[B] = underlying.flatMap(f)

  def flatten[B](implicit asIterable: ((K, V)) => IterableOnce[B]): immutable.Iterable[B] = underlying.flatten

  def collect[B](pf: PartialFunction[(K, V), B]): immutable.Iterable[B] = underlying.collect(pf)

  def partitionMap[A1, A2](f: ((K, V)) => Either[A1, A2]): (immutable.Iterable[A1], immutable.Iterable[A2]) =
    underlying.partitionMap(f)

  def concat[B >: (K, V)](suffix: IterableOnce[B]): NonEmptyArraySeq[B] =
    nonEmptyArraySeqFrom(underlying.view.concat(suffix))

  def zip[B](that: IterableOnce[B]): NonEmptyArraySeq[((K, V), B)] =
    nonEmptyArraySeqFrom(underlying.view.zip(that))

  def zipWithIndex: NonEmptyArraySeq[((K, V), Int)] = nonEmptyArraySeqFrom(underlying.view.zipWithIndex)

  def zipAll[A1 >: (K, V), B](
    that: Iterable[B],
    thisElem: A1,
    thatElem: B,
  ): NonEmptyArraySeq[(A1, B)] = nonEmptyArraySeqFrom(underlying.view.zipAll(that, thisElem, thatElem))

  def unzip[A1, A2](implicit asPair: ((K, V)) => (A1, A2)): (NonEmptyArraySeq[A1], NonEmptyArraySeq[A2]) =
    underlying.view.unzip(asPair) match {
      case (l, r) => (nonEmptyArraySeqFrom(l), nonEmptyArraySeqFrom(r))
    }

  def unzip3[A1, A2, A3](
    implicit
    asTriple: ((K, V)) => (A1, A2, A3),
  ): (NonEmptyArraySeq[A1], NonEmptyArraySeq[A2], NonEmptyArraySeq[A3]) =
    underlying.view.unzip3 match {
      case (l, c, r) =>
        (
          nonEmptyArraySeqFrom(l),
          nonEmptyArraySeqFrom(c),
          nonEmptyArraySeqFrom(r),
        )
    }

  def tails: Iterator[Map[K, V]] = underlying.tails

  def inits: Iterator[Map[K, V]] = underlying.inits

  def tapEach[U](f: ((K, V)) => U): NonEmptyMap[K, V] = new NonEmptyMap(underlying.tapEach(f))

  def foreach[U](f: ((K, V)) => U): Unit = underlying.foreach(f)

  def forall(p: ((K, V)) => Boolean): Boolean = underlying.forall(p)

  def exists(p: ((K, V)) => Boolean): Boolean = underlying.exists(p)

  def count(p: ((K, V)) => Boolean): Int = underlying.count(p)

  def find(p: ((K, V)) => Boolean): Option[(K, V)] = underlying.find(p)

  def foldLeft[B](z: B)(op: (B, (K, V)) => B): B = underlying.foldLeft(z)(op)

  def foldRight[B](z: B)(op: ((K, V), B) => B): B = underlying.foldRight(z)(op)

  def fold[A1 >: (K, V)](z: A1)(op: (A1, A1) => A1): A1 = underlying.fold(z)(op)

  def reduce[B >: (K, V)](op: (B, B) => B): B = underlying.reduce(op)

  def reduceLeft[B >: (K, V)](op: (B, (K, V)) => B): B = underlying.reduceLeft(op)

  def reduceRight[B >: (K, V)](op: ((K, V), B) => B): B = underlying.reduceRight(op)

  def isEmpty: Boolean = false

  def nonEmpty: Boolean = true

  def size: Int = underlying.size

  def copyToArray[B >: (K, V)](xs: Array[B]): Int = underlying.copyToArray(xs)

  def copyToArray[B >: (K, V)](xs: Array[B], start: Int): Int = underlying.copyToArray(xs, start)

  def copyToArray[B >: (K, V)](
    xs: Array[B],
    start: Int,
    len: Int,
  ): Int = underlying.copyToArray(xs, start, len)

  def sum[B >: (K, V)](implicit num: Numeric[B]): B = underlying.sum[B]

  def product[B >: (K, V)](implicit num: Numeric[B]): B = underlying.product[B]

  def min[B >: (K, V)](implicit ord: Ordering[B]): (K, V) = underlying.min[B]

  def max[B >: (K, V)](implicit ord: Ordering[B]): (K, V) = underlying.max[B]

  def maxBy[B](f: ((K, V)) => B)(implicit cmp: Ordering[B]): (K, V) = underlying.maxBy(f)

  def minBy[B](f: ((K, V)) => B)(implicit cmp: Ordering[B]): (K, V) = underlying.minBy(f)

  def collectFirst[B](pf: PartialFunction[(K, V), B]): Option[B] = underlying.collectFirst(pf)

  def corresponds[B](that: IterableOnce[B])(p: ((K, V), B) => Boolean): Boolean = underlying.corresponds(that)(p)

  def to[C1](factory: Factory[(K, V), C1]): C1 = underlying.to(factory)

  def toList: List[(K, V)] = underlying.toList

  def toVector: Vector[(K, V)] = underlying.toVector

  def toMap[K2, V2](implicit ev: (K, V) <:< (K2, V2)): Map[K2, V2] = underlying.toMap

  def toSet[B >: (K, V)]: Set[B] = underlying.toSet

  def toSeq: Seq[(K, V)] = underlying.toSeq

  def toIndexedSeq: IndexedSeq[(K, V)] = underlying.toIndexedSeq

  def toArray[B >: (K, V)](implicit evidence$2: ClassTag[B]): Array[B] = underlying.toArray

  def toNonEmptyArraySeq: NonEmptyArraySeq[(K, V)] = NonEmptyArraySeq.fromIterableUnsafe(this.underlying)

  def toNonEmptySet[B >: (K, V)]: NonEmptySet[B] = NonEmptySet.fromIterableUnsafe(this.underlying)

  override def toString: String = underlying.mkString(s"$NonEmptyMap(", ", ", ")")

  override def equals(other: Any): Boolean =
    other match {
      case that: NonEmptyMap[_, _] =>
        this.underlying == that.underlying
      case _ => false
    }

  override def hashCode(): Int = underlying.hashCode()

}

object NonEmptyMap {

  // TODO ops to convert non-empty iterables with tuple elements to a non-empty map

  def one[K, V](k: K, v: V): NonEmptyMap[K, V] = new NonEmptyMap(Map(k -> v))

  def fromHeadTail[K, V](head: (K, V), tail: Iterable[(K, V)]): NonEmptyMap[K, V] = {
    val builder = Map.newBuilder[K, V].addOne(head)
    builder.addAll(tail)
    new NonEmptyMap(builder.result())
  }

  def of[K, V](head: (K, V), tail: (K, V)*): NonEmptyMap[K, V] = fromHeadTail(head, tail)

  def fromMap[K, V](map: Map[K, V]): Option[NonEmptyMap[K, V]] =
    if (map.isEmpty) None else Some(new NonEmptyMap(map))

  def fromMapUnsafe[K, V](map: Map[K, V]): NonEmptyMap[K, V] =
    if (map.isEmpty)
      throw new IllegalArgumentException("Cannot create NEC from empty set")
    else
      new NonEmptyMap(map)

  def fromIterable[K, V](iterable: IterableOnce[(K, V)]): Option[NonEmptyMap[K, V]] = {
    val builder = Map.newBuilder[K, V]
    builder.addAll(iterable)
    fromMap(builder.result())
  }

  def fromIterableUnsafe[K, V](iterable: IterableOnce[(K, V)]): NonEmptyMap[K, V] =
    fromIterable(iterable).getOrElse(
      throw new IllegalArgumentException("Cannot create NEC from empty set"),
    )

  def unsafeBuilder[K, V]: mutable.Builder[(K, V), NonEmptyMap[K, V]] =
    new mutable.Builder[(K, V), NonEmptyMap[K, V]] {
      private val underlyingBuilder: mutable.Builder[(K, V), Map[K, V]] = Map.newBuilder[K, V]
      override def clear(): Unit                                        = underlyingBuilder.clear()
      override def result(): NonEmptyMap[K, V]                          = fromMapUnsafe(underlyingBuilder.result())
      override def addOne(elem: (K, V)): this.type = {
        underlyingBuilder.addOne(elem)
        this
      }
      override def addAll(xs: IterableOnce[(K, V)]): this.type = {
        underlyingBuilder.addAll(xs)
        this
      }
      override def sizeHint(size: Int): Unit = underlyingBuilder.sizeHint(size)
      override def knownSize: Int            = underlyingBuilder.knownSize
    }

}
