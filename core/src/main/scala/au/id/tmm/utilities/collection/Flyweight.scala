package au.id.tmm.utilities.collection

import scala.collection.mutable

/**
  * A simple implementation of the <a href="https://en.wikipedia.org/wiki/Flyweight_pattern">flyweight pattern</a>
  */
final class Flyweight[A, B] private (generator: A => B) {
  private val map: mutable.Map[A, B] = mutable.Map()

  def apply(key: A): B =
    map.synchronized {
      if (map.contains(key)) {
        map(key)
      } else {
        val value = generator(key)
        map.put(key, value)
        value
      }
    }
}

object Flyweight {
  def apply[A, B](generator: A => B): Flyweight[A, B] = new Flyweight[A, B](generator)
}
