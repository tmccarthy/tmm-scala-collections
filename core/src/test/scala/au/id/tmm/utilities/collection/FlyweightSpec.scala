package au.id.tmm.utilities.collection

import java.time.LocalDate

import org.scalatest.FlatSpec

class FlyweightSpec extends FlatSpec {

  private val testFlyweight = Flyweight(LocalDate.parse)

  "a flyweight" should "construct its element from the supplied function" in {
    assert(testFlyweight("2019-08-18") === LocalDate.parse("2019-08-18"))
  }

  it should "reuse previous values" in {
    assert(testFlyweight("2019-08-18") eq testFlyweight("2019-08-18"))
  }
}
