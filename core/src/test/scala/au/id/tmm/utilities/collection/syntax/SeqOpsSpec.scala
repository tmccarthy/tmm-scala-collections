package au.id.tmm.utilities.collection.syntax

import org.scalatest.FlatSpec

class SeqOpsSpec extends FlatSpec {

  "finding every nth element" should "return empty for an empty seq" in {
    assert(List.empty[Int].everyNth(5) === List.empty)
  }

  it should "return the first element if there are less than n elements" in {
    assert(List(1, 2, 3).everyNth(5) === List(1))
  }

  it should "return every nth element" in {
    assert(Range.inclusive(1, 11).toList.everyNth(5) === List(1, 6, 11))
  }

  it should "drop any extra elements" in {
    assert(Range.inclusive(1, 20).toList.everyNth(5) === List(1, 6, 11, 16))
  }

}
