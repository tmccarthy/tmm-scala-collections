package au.id.tmm.utilities.collection.classes

import au.id.tmm.utilities.collection.{DupelessSeq, NonEmptyDupelessSeq, NonEmptyIterableCompanion, NonEmptySet}

import scala.collection.{IterableOps, mutable}

trait SafeGroupBy[C[_], NEC[_]] {

  def safeGroupMap[A, K, V](ca: C[A])(key: A => K)(f: A => V): Map[K, NEC[V]]

  def safeGroupBy[A, K](ca: C[A])(f: A => K): Map[K, NEC[A]] = safeGroupMap[A, K, A](ca)(f)(identity)

  def safeGroupByKey[A, K, V](ca: C[A])(implicit ev: A <:< (K, V)): Map[K, NEC[V]] =
    safeGroupMap(ca)(_._1)(_._2)

}

object SafeGroupBy extends SafeGroupByInstances {

  def apply[C[_], NEC[_]](implicit safeGroupBy: SafeGroupBy[C, NEC]): SafeGroupBy[C, NEC] = implicitly

  class Ops[C[_], NEC[_], A](ca: C[A])(implicit safeGroupBy: SafeGroupBy[C, NEC]) {
    def safeGroupMap[K, V](key: A => K)(f: A => V): Map[K, NEC[V]] =
      safeGroupBy.safeGroupMap[A, K, V](ca)(key)(f)

    def safeGroupBy[K](key: A => K): Map[K, NEC[A]] =
      safeGroupBy.safeGroupBy[A, K](ca)(key)

    def safeGroupByKey[K, V](implicit ev: A <:< (K, V)): Map[K, NEC[V]] =
      safeGroupBy.safeGroupByKey(ca)
  }

  trait ToSafeGroupByOps {
    implicit def toSafeGroupByOps[C[_], NEC[_], A](
      ca: C[A],
    )(implicit
      safeGroupBy: SafeGroupBy[C, NEC],
    ): Ops[C, NEC, A] =
      new Ops(ca)
  }

  trait ForScalaIterable[C[X] <: IterableOps[X, C, C[X]], NEC[_]] extends SafeGroupBy[C, NEC] {
    private type CBuilder[X] = mutable.Builder[X, C[X]]

    def makeNecUnsafe[A](ca: C[A]): NEC[A]

    override def safeGroupMap[A, K, V](ca: C[A])(key: A => K)(f: A => V): Map[K, NEC[V]] = {
      val interim = mutable.Map[K, CBuilder[V]]()

      ca.foreach { a: A =>
        val k: K = key(a)
        val v: V = f(a)

        val cBuilder: CBuilder[V] = interim.getOrElseUpdate(k, ca.iterableFactory.newBuilder)

        cBuilder.addOne(v)
      }

      val result = Map.newBuilder[K, NEC[V]]

      result.sizeHint(interim.size)

      interim.foreach {
        case (k, cBuilder) =>
          val c: C[V]     = cBuilder.result()
          val nec: NEC[V] = makeNecUnsafe(c)
          result.addOne(k -> nec)
      }

      result.result()
    }
  }

  object ForScalaIterable {
    class UsingTmmUtilsNonEmpty[C[X] <: IterableOps[X, C, C[X]], NEC[_]] private[classes] (
      necCompanion: NonEmptyIterableCompanion[C, NEC],
    ) extends SafeGroupBy.ForScalaIterable[C, NEC] {
      override def makeNecUnsafe[A](ca: C[A]): NEC[A] = necCompanion.fromIterableUnsafe(ca)
    }
  }

}

trait SafeGroupByInstances {

  implicit val safeGroupByForSetUsingTmmUtilsNonEmptySet: SafeGroupBy[Set, NonEmptySet] =
    new SafeGroupBy.ForScalaIterable.UsingTmmUtilsNonEmpty[Set, NonEmptySet](NonEmptySet)

  implicit val safeGroupByForDupelessSeqUsingTmmUtilsNonEmptySet: SafeGroupBy[DupelessSeq, NonEmptyDupelessSeq] =
    new SafeGroupBy.ForScalaIterable.UsingTmmUtilsNonEmpty[DupelessSeq, NonEmptyDupelessSeq](NonEmptyDupelessSeq)

}
