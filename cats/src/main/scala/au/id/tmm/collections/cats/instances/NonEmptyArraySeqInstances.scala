package au.id.tmm.collections.cats.instances

import au.id.tmm.collections.NonEmptyArraySeq
import cats.data.Ior
import cats.kernel.{Eq, Hash, Semigroup}
import cats.{
  Align,
  Applicative,
  Apply,
  Bimonad,
  CoflatMap,
  Eval,
  Foldable,
  Functor,
  Monad,
  NonEmptyTraverse,
  SemigroupK,
  Show,
}
import cats.instances.arraySeq._
import cats.syntax.traverse.toTraverseOps
import cats.syntax.functor.toFunctorOps

import scala.collection.immutable.ArraySeq

trait NonEmptyArraySeqInstances extends NonEmptyArraySeqInstances1 {

  implicit def tmmUtilsHashForTmmUtilsNonEmptyArraySeq[A : Hash]: Hash[NonEmptyArraySeq[A]] = Hash.by(_.underlying)

  implicit def tmmUtilsShowForTmmUtilsNonEmptyArraySeq[A : Show]: Show[NonEmptyArraySeq[A]] =
    new Show[NonEmptyArraySeq[A]] {
      override def show(t: NonEmptyArraySeq[A]): String = ???
    }

  implicit val tmmUtilsInstancesForTmmUtilsNonEmptyArraySeq: NonEmptyTraverse[NonEmptyArraySeq]
    with Bimonad[NonEmptyArraySeq]
    with SemigroupK[NonEmptyArraySeq]
    with Align[NonEmptyArraySeq] = new NonEmptyTraverse[NonEmptyArraySeq]
    with Bimonad[NonEmptyArraySeq]
    with SemigroupK[NonEmptyArraySeq]
    with Align[NonEmptyArraySeq] {

    override def traverse[G[_], A, B](
      fa: NonEmptyArraySeq[A],
    )(
      f: A => G[B],
    )(implicit
      G: Applicative[G],
    ): G[NonEmptyArraySeq[B]] =
      fa.underlying.traverse(f).map(NonEmptyArraySeq.fromArraySeqUnsafe)

    override def nonEmptyTraverse[G[_], A, B](
      fa: NonEmptyArraySeq[A],
    )(
      f: A => G[B],
    )(implicit
      G: Apply[G],
    ): G[NonEmptyArraySeq[B]] =
      reduceRightTo[A, G[NonEmptyArraySeq[B]]](fa)(a =>
        G.map[B, NonEmptyArraySeq[B]](f(a))(NonEmptyArraySeq.untagged.one),
      ) {
        case (a, evalGNesB) => {
          G.map2Eval[B, NonEmptyArraySeq[B], NonEmptyArraySeq[B]](f(a), evalGNesB) {
            case (b, nesB) => nesB.prepended(b)
          }
        }
      }.value

    override def combineK[A](x: NonEmptyArraySeq[A], y: NonEmptyArraySeq[A]): NonEmptyArraySeq[A] = x concat y

    override def functor: Functor[NonEmptyArraySeq] = this

    override def align[A, B](fa: NonEmptyArraySeq[A], fb: NonEmptyArraySeq[B]): NonEmptyArraySeq[Ior[A, B]] =
      NonEmptyArraySeq.untagged.fromArraySeqUnsafe(Align[ArraySeq].align(fa.underlying, fb.underlying))

    override def foldLeft[A, B](fa: NonEmptyArraySeq[A], b: B)(f: (B, A) => B): B = fa.foldLeft(b)(f)

    override def foldRight[A, B](fa: NonEmptyArraySeq[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
      Foldable.iterateRight(fa.toIterable, lb)(f)

    override def flatMap[A, B](fa: NonEmptyArraySeq[A])(f: A => NonEmptyArraySeq[B]): NonEmptyArraySeq[B] =
      fa.flatMap(f)

    override def tailRecM[A, B](a: A)(f: A => NonEmptyArraySeq[Either[A, B]]): NonEmptyArraySeq[B] =
      NonEmptyArraySeq.untagged.fromArraySeqUnsafe(Monad[ArraySeq].tailRecM(a)(f.andThen(_.underlying)))

    override def pure[A](x: A): NonEmptyArraySeq[A] = NonEmptyArraySeq.untagged.one(x)

    override def coflatMap[A, B](fa: NonEmptyArraySeq[A])(f: NonEmptyArraySeq[A] => B): NonEmptyArraySeq[B] =
      NonEmptyArraySeq.untagged.fromArraySeqUnsafe(
        CoflatMap[ArraySeq].coflatMap(fa.underlying)(f.compose(NonEmptyArraySeq.untagged.fromArraySeqUnsafe[A])),
      )

    override def reduceLeftTo[A, B](fa: NonEmptyArraySeq[A])(f: A => B)(g: (B, A) => B): B =
      fa.tail.foldLeft[B](f(fa.head))(g)

    override def reduceRightTo[A, B](fa: NonEmptyArraySeq[A])(f: A => B)(g: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa.init.foldRight[Eval[B]](Eval.now[A](fa.last).map(f))(g)

    override def extract[A](x: NonEmptyArraySeq[A]): A = x.head
  }

  implicit def tmmUtilsSemigroupForTmmUtilsNonEmptyArraySeq[A]: Semigroup[NonEmptyArraySeq[A]] =
    tmmUtilsInstancesForTmmUtilsNonEmptyArraySeq.algebra[A]

}

trait NonEmptyArraySeqInstances1 {
  implicit def tmmUtilsEqForTmmUtilsNonEmptyArraySeq[A : Eq]: Eq[NonEmptyArraySeq[A]] = Eq.by(_.underlying)
}
