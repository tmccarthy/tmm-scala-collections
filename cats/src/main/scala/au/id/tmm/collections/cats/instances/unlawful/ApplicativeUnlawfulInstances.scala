package au.id.tmm.collections.cats.instances.unlawful

import cats.{Applicative, CommutativeApplicative}

trait ApplicativeUnlawfulInstances {

  implicit def tmmUtilsCommutativeApplicativeForApplicative[F[_]](implicit F: Applicative[F]): CommutativeApplicative[F] =
    new CommutativeApplicative[F] {
      override def pure[A](x: A): F[A]                     = F.pure(x)
      override def ap[A, B](ff: F[A => B])(fa: F[A]): F[B] = F.ap(ff)(fa)
    }

}
