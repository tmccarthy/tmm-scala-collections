package au.id.tmm.collections.cats.instances.unlawful

import au.id.tmm.collections.NonEmptySet
import cats.{Apply, Eval, Foldable, Monad, NonEmptyTraverse}

import scala.collection.mutable

trait NonEmptySetUnlawfulInstances {

  implicit val catsUnlawfulInstancesForTmmUtilsNonEmptySet: NonEmptyTraverse[NonEmptySet] with Monad[NonEmptySet] = {
    new NonEmptyTraverse[NonEmptySet] with Monad[NonEmptySet] {

      override def nonEmptyTraverse[G[_], A, B](
        fa: NonEmptySet[A],
      )(
        f: A => G[B],
      )(implicit
        G: Apply[G],
      ): G[NonEmptySet[B]] =
        reduceRightTo[A, G[NonEmptySet[B]]](fa)(a => G.map[B, NonEmptySet[B]](f(a))(NonEmptySet.one)) {
          case (a, evalGNesB) => {
            G.map2Eval[B, NonEmptySet[B], NonEmptySet[B]](f(a), evalGNesB) {
              case (b, nesB) => nesB.incl(b)
            }
          }
        }.value

      override def reduceLeftTo[A, B](fa: NonEmptySet[A])(f: A => B)(g: (B, A) => B): B =
        fa.tail.foldRight[B](f(fa.head)) {
          case (a, b) => g(b, a)
        }

      override def reduceRightTo[A, B](fa: NonEmptySet[A])(f: A => B)(g: (A, Eval[B]) => Eval[B]): Eval[B] =
        fa.init.foldRight[Eval[B]](Eval.now[A](fa.last).map(f))(g)

      override def foldLeft[A, B](fa: NonEmptySet[A], b: B)(f: (B, A) => B): B =
        fa.foldLeft(b)(f)

      override def foldRight[A, B](fa: NonEmptySet[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
        Foldable.iterateRight[A, B](fa.to(Iterable), lb)(f)

      override def flatMap[A, B](fa: NonEmptySet[A])(f: A => NonEmptySet[B]): NonEmptySet[B] =
        fa.flatMap(f)

      override def tailRecM[A, B](a: A)(f: A => NonEmptySet[Either[A, B]]): NonEmptySet[B] = {
        val resultBuilder            = Set.newBuilder[B]
        val aQueue: mutable.Queue[A] = mutable.Queue(a)

        while (aQueue.nonEmpty) {
          f(aQueue.dequeue()).foreach {
            case Right(b) => resultBuilder.addOne(b)
            case Left(a)  => aQueue.addOne(a)
          }
        }

        NonEmptySet.fromSetUnsafe(resultBuilder.result())
      }

      override def pure[A](x: A): NonEmptySet[A] = NonEmptySet.one(x)
    }
  }
}
