package au.id.tmm.utilities.collection.cats.instances

import au.id.tmm.utilities.collection.DupelessSeq
import cats.kernel.{Band, Hash, Monoid}
import cats.syntax.functor.toFunctorOps
import cats.{Applicative, Eval, Monad, MonoidK, Show, Traverse}

import scala.collection.immutable.ArraySeq
import scala.collection.mutable

trait DupelessSeqInstances extends DupelessSeqInstances1 {

  implicit def catsStdHashForDupelessSeq[A : Hash]: Hash[DupelessSeq[A]] = new Hash[DupelessSeq[A]] {
    override def hash(x: DupelessSeq[A]): Int                       = Hash.hash((x.toArraySeq, x.toSet))
    override def eqv(x: DupelessSeq[A], y: DupelessSeq[A]): Boolean = x == y
  }

  implicit def catsStdShowForDupelessSeq[A : Show]: Show[DupelessSeq[A]] =
    s => s.iterator.map(Show[A].show).mkString("DupelessSeq(", ", ", ")")

  implicit def catsStdMonoidForDupelessSeq[A]: Monoid[DupelessSeq[A]] = new Monoid[DupelessSeq[A]] {
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

  object unlawful {
    implicit val catsUnlawfulInstancesForDupelessSeq: Monad[DupelessSeq] = new Monad[DupelessSeq] {
      override def flatMap[A, B](fa: DupelessSeq[A])(f: A => DupelessSeq[B]): DupelessSeq[B] =
        fa.flatMap(f)

      override def tailRecM[A, B](a: A)(f: A => DupelessSeq[Either[A, B]]): DupelessSeq[B] = {
        val resultBuilder: DupelessSeq.DupelessSeqBuilder[B] = DupelessSeq.newBuilder[B]
        val aQueue: mutable.Queue[A]                         = mutable.Queue(a)

        while (aQueue.nonEmpty) {
          f(aQueue.dequeue()) foreach {
            case Right(b) => resultBuilder.addOne(b)
            case Left(a)  => aQueue.append(a)
          }
        }

        resultBuilder.result()
      }

      override def pure[A](x: A): DupelessSeq[A] = DupelessSeq(x)
    }
  }

}

private[instances] trait DupelessSeqInstances1 {

  implicit def catsStdBandForDupelessSeq[A]: Band[DupelessSeq[A]] = _ appendedAll _

}
