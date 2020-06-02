package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.DupelessSeq
import cats.kernel.{Band, Hash, Monoid}
import cats.syntax.functor.toFunctorOps
import cats.{Applicative, Eval, MonoidK, Show, Traverse}

import scala.collection.immutable.ArraySeq

trait DupelessSeqInstances extends DupelessSeqInstances1 {

  implicit def catsStdHashForDupelessSeq[A : Hash]: Hash[DupelessSeq[A]] =
    new Hash[DupelessSeq[A]] {
      override def hash(x: DupelessSeq[A]): Int                       = Hash.hash((x.toArraySeq, x.toSet))
      override def eqv(x: DupelessSeq[A], y: DupelessSeq[A]): Boolean = x == y
    }

  implicit def catsStdShowForDupelessSeq[A : Show]: Show[DupelessSeq[A]] =
    s => s.iterator.map(Show[A].show).mkString("DupelessSeq(", ", ", ")")

  implicit def catsStdMonoidForDupelessSeq[A]: Monoid[DupelessSeq[A]] =
    new Monoid[DupelessSeq[A]] {
      override def empty: DupelessSeq[A] = DupelessSeq.empty

      override def combine(x: DupelessSeq[A], y: DupelessSeq[A]): DupelessSeq[A] = x.appendedAll(y)
    }

  implicit val catsStdInstancesForDupelessSeq: MonoidK[DupelessSeq] with Traverse[DupelessSeq] =
    new MonoidK[DupelessSeq] with Traverse[DupelessSeq] {
      override def empty[A]: DupelessSeq[A] = DupelessSeq.empty

      override def traverse[G[_], A, B](
        fa: DupelessSeq[A],
      )(
        f: A => G[B],
      )(implicit
        evidence$1: Applicative[G],
      ): G[DupelessSeq[B]] =
        Traverse[ArraySeq].traverse(fa.toArraySeq)(f).map(arraySeq => DupelessSeq.from(arraySeq))

      override def foldLeft[A, B](fa: DupelessSeq[A], b: B)(f: (B, A) => B): B =
        Traverse[ArraySeq].foldLeft(fa.toArraySeq, b)(f)

      override def foldRight[A, B](fa: DupelessSeq[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
        Traverse[ArraySeq].foldRight(fa.toArraySeq, lb)(f)

      override def combineK[A](x: DupelessSeq[A], y: DupelessSeq[A]): DupelessSeq[A] =
        x concat y

      override def algebra[A]: Monoid[DupelessSeq[A]] = catsStdMonoidForDupelessSeq
    }

}

private[instances] trait DupelessSeqInstances1 {

  // TODO need an Eq here

  implicit def catsStdBandForDupelessSeq[A]: Band[DupelessSeq[A]] = _ appendedAll _

}
