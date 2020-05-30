package au.id.tmm.utilities.collection.cats.syntax.safegroupby

import au.id.tmm.utilities.collection.NonEmptySet
import au.id.tmm.utilities.collection.syntax.toSafeGroupByOps
import au.id.tmm.utilities.collection.cats.instances.list._
import cats.data.NonEmptyList
import org.scalatest.flatspec.AnyFlatSpec

class SafeGroupBySyntaxSpec extends AnyFlatSpec {

  "safe group by for Set" should "work" in {
    val groupedBy = Set("apple", "apricot", "banana").safeGroupBy(_.head)

    val expected = Map(
      'a' -> NonEmptySet.of("apple", "apricot"),
      'b' -> NonEmptySet.of("banana"),
    )

    assert(groupedBy === expected)
  }

  it should "work for groupMap" in {
    val groupedBy = Set("apple", "apricot", "banana").safeGroupMap(_.head)(_.toUpperCase)

    val expected = Map(
      'a' -> NonEmptySet.of("APPLE", "APRICOT"),
      'b' -> NonEmptySet.of("BANANA"),
    )

    assert(groupedBy === expected)
  }

  it should "work for the key value" in {
    val set = Set(
      (1, "hello"),
      (1, "world"),
      (2, "testing"),
    )

    val expected = Map(
      1 -> NonEmptySet.of("hello", "world"),
      2 -> NonEmptySet.of("testing"),
    )

    assert(set.safeGroupByKey === expected)
  }

  "safe group by for List" should "work for a list of tuples" in {
    val l = List(
      "a" -> 1,
      "a" -> 2,
      "b" -> 3,
    )

    val expectedGrouped = Map(
      "a" -> NonEmptyList.of(1, 2),
      "b" -> NonEmptyList.of(3),
    )

    assert(l.safeGroupByKey === expectedGrouped)
  }

}
