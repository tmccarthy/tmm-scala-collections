package au.id.tmm.utilities.codec.binarycodecs

import org.apache.commons.codec.DecoderException
import org.apache.commons.codec.binary.{Base64 => CommonsBase64}

import scala.collection.immutable.ArraySeq

object Base64 {

  def asBase64String(bytes: Array[Byte]): String    = CommonsBase64.encodeBase64String(bytes)
  def asBase64String(bytes: ArraySeq[Byte]): String = asBase64String(bytes.unsafeArray.asInstanceOf[Array[Byte]])
  def asBase64String(bytes: Iterable[Byte]): String = asBase64String(bytes.toArray)

  private def decodeToBytes(string: String): Array[Byte] =
    if (CommonsBase64.isBase64(string)) CommonsBase64.decodeBase64(string)
    else throw new DecoderException("Invalid base64")
  def parseBase64OrThrow(string: String): ArraySeq[Byte] = ArraySeq.unsafeWrapArray(decodeToBytes(string))
  def parseBase64(string: String): Either[DecoderException, ArraySeq[Byte]] =
    try Right(parseBase64OrThrow(string))
    catch {
      case e: DecoderException => Left(e)
    }

  trait Syntax {

    implicit class Base64StringContext(private val stringContext: StringContext) {
      def base64(subs: Any*): ArraySeq[Byte] = parseBase64OrThrow(stringContext.s(subs: _*))
    }

    implicit class Base64StringOps(private val s: String) {
      def parseBase64: Either[DecoderException, ArraySeq[Byte]] = Base64.parseBase64(s)

      def parseBase64Unsafe: ArraySeq[Byte] = parseBase64OrThrow(s)
    }

    implicit class Base64ByteArrayOps(private val bytes: ArraySeq[Byte]) {
      def asBase64String: String = Base64.asBase64String(bytes)
    }

    implicit class Base64IterableOps(private val bytes: Iterable[Byte]) {
      def asBase64String: String = Base64.asBase64String(bytes)
    }

    implicit class Base64PrimitiveByteArrayOps(private val bytes: Array[Byte]) {
      def asBase64String: String = Base64.asBase64String(bytes)
    }

  }

}
