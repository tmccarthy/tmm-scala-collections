package au.id.tmm.collections.cats.instances.unlawful

import au.id.tmm.collections.NonEmptyMap
import au.id.tmm.collections.cats.instances.nonEmptyArraySeq._
import cats.syntax.functor.toFunctorOps
import cats.syntax.traverse.toTraverseOps
import cats.{Applicative, Eval, Foldable, Traverse}

trait NonEmptyMapUnlawfulInstances {

  implicit def tmmUtilsTraverseForNonEmptyMap[K]: Traverse[NonEmptyMap[K, *]] =
    new Traverse[NonEmptyMap[K, *]] {
      override def traverse[G[_], A, B](
        fa: NonEmptyMap[K, A],
      )(
        f: A => G[B],
      )(implicit
        G: Applicative[G],
      ): G[NonEmptyMap[K, B]] =
        fa.toNonEmptyArraySeq
          .traverse {
            case (k, a) => f(a).map(b => k -> b)
          }
          .map(NonEmptyMap.fromIterableUnsafe)

      override def foldLeft[A, B](fa: NonEmptyMap[K, A], b: B)(f: (B, A) => B): B =
        fa.foldLeft(b) {
          case (b, (k, a)) => f(b, a)
        }

      override def foldRight[A, B](fa: NonEmptyMap[K, A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
        Foldable.iterateRight(fa.values, lb)(f)
    }

}
