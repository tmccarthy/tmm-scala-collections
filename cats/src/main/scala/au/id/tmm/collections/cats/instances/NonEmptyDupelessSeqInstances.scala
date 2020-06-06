package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptyDupelessSeq
import au.id.tmm.collections.cats.instances.dupelessSeq._
import cats.kernel.{Band, Eq, Hash}
import cats.syntax.show.toShow
import cats.{Apply, Eval, Foldable, NonEmptyTraverse, SemigroupK, Show}

trait NonEmptyDupelessSeqInstances extends NonEmptyDupelessSeqInstances1 {

  implicit def tmmUtilsHashForNonEmptyDupelessSeq[A : Hash]: Hash[NonEmptyDupelessSeq[A]] = Hash.by(_.underlying)

  implicit def tmmUtilsShowForNonEmptyDupelessSeq[A : Show]: Show[NonEmptyDupelessSeq[A]] =
    s => s"NonEmpty${s.underlying.show}"

  implicit def tmmUtilsSemigroupForNonEmptyDupelessSeq[A]: Band[NonEmptyDupelessSeq[A]] = _ concat _

  implicit val tmmUtilsInstancesForNonEmptyDupelessSeq
    : SemigroupK[NonEmptyDupelessSeq] with NonEmptyTraverse[NonEmptyDupelessSeq] =
    new SemigroupK[NonEmptyDupelessSeq] with NonEmptyTraverse[NonEmptyDupelessSeq] {
      override def combineK[A](x: NonEmptyDupelessSeq[A], y: NonEmptyDupelessSeq[A]): NonEmptyDupelessSeq[A] =
        x concat y

      override def nonEmptyTraverse[G[_], A, B](
        fa: NonEmptyDupelessSeq[A],
      )(
        f: A => G[B],
      )(implicit
        G: Apply[G],
      ): G[NonEmptyDupelessSeq[B]] =
        reduceRightTo[A, G[NonEmptyDupelessSeq[B]]](fa)(a =>
          G.map[B, NonEmptyDupelessSeq[B]](f(a))(NonEmptyDupelessSeq.one),
        ) {
          case (a, evalGNesB) => {
            G.map2Eval[B, NonEmptyDupelessSeq[B], NonEmptyDupelessSeq[B]](f(a), evalGNesB) {
              case (b, nesB) => nesB.prepended(b)
            }
          }
        }.value

      override def reduceLeftTo[A, B](fa: NonEmptyDupelessSeq[A])(f: A => B)(g: (B, A) => B): B =
        fa.tail.foldLeft[B](f(fa.head))(g)

      override def reduceRightTo[A, B](fa: NonEmptyDupelessSeq[A])(f: A => B)(g: (A, Eval[B]) => Eval[B]): Eval[B] =
        fa.init.foldRight[Eval[B]](Eval.now[A](fa.last).map(f))(g)

      override def foldLeft[A, B](fa: NonEmptyDupelessSeq[A], b: B)(f: (B, A) => B): B =
        fa.foldLeft(b)(f)

      override def foldRight[A, B](fa: NonEmptyDupelessSeq[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
        Foldable.iterateRight(fa.toIterable, lb)(f)
    }

}

private[instances] trait NonEmptyDupelessSeqInstances1 {

  implicit def tmmUtilsEqForNonEmptyDupelessSeq[A : Eq]: Eq[NonEmptyDupelessSeq[A]] =
    Eq.by(s => (s.toArraySeq, s.toSet))

}
