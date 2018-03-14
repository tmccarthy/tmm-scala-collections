package au.id.tmm.utilities.option

import scala.util.{Failure, Success, Try}

object OptionUtils {
  implicit class ImprovedOption[A](option: Option[A]) {

    /**
      * Converts this `Option` to a `Try` by mapping `None` to a `Failure` containing the given exception.
      */
    def failIfAbsent(throwable: => Throwable): Try[A] = option match {
      case Some(value) => Success(value)
      case None => Failure(throwable)
    }
  }
}
