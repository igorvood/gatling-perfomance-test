package ru.vood.gatling.test.dto

import com.sksamuel.avro4s._
import org.apache.avro.Schema
import org.apache.avro.generic.{GenericDatumReader, GenericDatumWriter, GenericRecord}
import play.api.libs.json.{Json, OWrites, Reads}

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
                   process_timestamp: Long) {

  def serializeToAvro: (Array[Byte], Array[Byte]) = {
    var baos: ByteArrayOutputStream = null
    var output: AvroOutputStream[SomeDto] = null
    try {
      baos = new ByteArrayOutputStream()
      val avroOutputStream = AvroOutputStream.binary[SomeDto]
      output = avroOutputStream.to(baos).build()
      output.write(this)
    } finally {
      if (output != null) {
        output.close()
      }

      if (baos != null) {
        baos.close()
      }
    }

    (this.id.getBytes(), baos.toByteArray)
  }

}

object SomeDto {

  implicit val sp: ScalePrecision = BigDecimalConst.sp

  val decoder = Decoder[SomeDto]

  val encoder = Encoder[SomeDto]

  val genericDatumReader = new GenericDatumReader[GenericRecord](decoder.schema)

  val schema = AvroSchema[SomeDto]


  val schemaUaspDto: Schema = AvroSchema[SomeDto]
  val genericDatumWriterUaspDto = new GenericDatumWriter[GenericRecord](schemaUaspDto)


  implicit val uaspJsonReads: Reads[SomeDto] = Json.reads[SomeDto]
  implicit val uaspJsonWrites: OWrites[SomeDto] = Json.writes[SomeDto]


}