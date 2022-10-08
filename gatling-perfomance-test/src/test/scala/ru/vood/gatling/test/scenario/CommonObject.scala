package ru.vood.gatling.test.scenario

import com.sksamuel.avro4s.{AvroSchema, Decoder, Encoder}
import org.apache.avro.Schema
import org.apache.avro.generic.{GenericDatumReader, GenericDatumWriter, GenericRecord}
import ru.vood.gatling.test.dto.SomeDto

object CommonObject {
  val schemaUaspDto: Schema = AvroSchema[SomeDto]
  val decoderUaspDto = Decoder[SomeDto]
  val encoderUaspDto: Encoder[SomeDto] = Encoder[SomeDto]
  val genericDatumWriterUaspDto = new GenericDatumWriter[GenericRecord](schemaUaspDto)
  val genericDatumReaderUaspDto = new GenericDatumReader[GenericRecord](decoderUaspDto.schema)

}
