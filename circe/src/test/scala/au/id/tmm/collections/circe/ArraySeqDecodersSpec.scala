package au.id.tmm.collections.circe

import io.circe.{Decoder, Json}
import io.circe.syntax.EncoderOps
import org.scalatest.flatspec.AnyFlatSpec
import au.id.tmm.utilities.testing.syntax._

import scala.collection.immutable.ArraySeq
import scala.reflect.ClassTag

class ArraySeqDecodersSpec extends AnyFlatSpec {

  private def assertSpecialisedAs[T : ClassTag](nonEmptyArraySeq: ArraySeq[_]): Unit =
    assert(nonEmptyArraySeq.getClass === implicitly[ClassTag[T]].runtimeClass)

  "the ArraySeq decoder" should "return a ref ArraySeq if its usage is untagged" in {
    val array = Json.arr(
      1.asJson,
    )

    def decodeForceUntagged[A : Decoder](json: Json): Decoder.Result[ArraySeq[A]] = json.as[ArraySeq[A]]

    assertSpecialisedAs[ArraySeq.ofRef[_]](decodeForceUntagged[Int](array).get)
  }

  it should "return a ref ArraySeq for a tagged reference type" in {
    val array = Json.arr(
      "array".asJson,
    )

    assertSpecialisedAs[ArraySeq.ofRef[_]](array.as[ArraySeq[String]].get)
  }

  it should "return a specialised ArraySeq for a tagged primative type" in {
    val array = Json.arr(
      1.asJson,
    )

    assertSpecialisedAs[ArraySeq.ofInt](array.as[ArraySeq[Int]].get)
  }

}
