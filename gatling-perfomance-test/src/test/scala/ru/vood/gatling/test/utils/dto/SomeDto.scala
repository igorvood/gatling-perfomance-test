package ru.vood.gatling.test.utils.dto

import com.sksamuel.avro4s._
import org.apache.avro.Schema
import org.apache.avro.generic.{GenericDatumReader, GenericDatumWriter, GenericRecord}
import play.api.libs.json.{Json, OWrites, Reads}
import ru.vood.gatling.test.dto.BigDecimalConst

import java.io.ByteArrayOutputStream

case class SomeDto(id: String,
                   dataInt: Map[String, Int],
                   dataLong: Map[String, Long],
                   dataFloat: Map[String, Float],
                   dataDouble: Map[String, Double],
                   dataDecimal: Map[String, BigDecimal],
                   dataString: Map[String, String],
                   dataBoolean: Map[String, Boolean],
                   uuid: String,
                   process_timestamp: Long) extends Identity


object SomeDto{

  implicit val sp: ScalePrecision = BigDecimalConst.sp

  val decoderUasp = Decoder[SomeDto]

  val encoderUasp = Encoder[SomeDto]

  val schemaUasp = AvroSchema[SomeDto]

  val genericDatumReader = new GenericDatumReader[GenericRecord](schemaUasp)

  implicit val uaspJsonReads: Reads[SomeDto] = Json.reads[SomeDto]
  implicit val uaspJsonWrites: OWrites[SomeDto] = Json.writes[SomeDto]
}