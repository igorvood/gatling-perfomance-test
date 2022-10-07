package ru.vtb.uasp.streaming.mdm.enrichment.itest.scenario

import com.github.mnogu.gatling.kafka.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import ru.vtb.uasp.common.dto.UaspDto
import ru.vtb.uasp.common.utils.avro.AvroSerializeUtil
import ru.vtb.uasp.streaming.mdm.enrichment.itest.common.Finisheable
import ru.vtb.uasp.streaming.mdm.enrichment.itest.dao.DataGeneratorDao.generateCrossLinkMdm
import ru.vtb.uasp.streaming.mdm.enrichment.itest.scenario.CommonObject.{encoderUaspDto, genericDatumWriterUaspDto}

import scala.math.abs

class SendScenarioBuilder(
                           private val senderName: String = "kafkaInMdmCrossLinkMessages",
                           private val cntIds: Int = 10000,
                           private val generateDto: String => UaspDto = generateCrossLinkMdm,
                           private val convertToBytes: (String, UaspDto) => (Array[Byte], Array[Byte]) = {
                             (id, data) => (id.getBytes(), AvroSerializeUtil.encode[UaspDto](data, encoderUaspDto, genericDatumWriterUaspDto))
                           }

                         ) extends Finisheable {


  def isFinished: Boolean = true

  private val byteIDName = "bytesLocalUserId"
  private val bytesDtoName = "bytesCrossLinkUaspDto"


  def sendScenario: ScenarioBuilder = {

    scenario("Mdm Cross Links")
      .exec(session => {
        val localUserId = abs(java.util.UUID.randomUUID().toString.hashCode % cntIds).toString

        val localCrossLinkUaspDto = generateDto(localUserId)

        val tuple = convertToBytes(localUserId, localCrossLinkUaspDto)

        session
          .set(byteIDName, tuple._1)
          .set(bytesDtoName, tuple._2)
      })
      .exec(kafka(senderName).send[Array[Byte], Array[Byte]]("${" + byteIDName + "}", "${" + bytesDtoName + "}"))
  }

}

object SendScenarioBuilder {


  def apply(senderName: String = "kafkaInMdmCrossLinkMessages",
            )(implicit  cntIds: CountId = CountId(10000), generateDto: String => UaspDto = generateCrossLinkMdm, convertToBytes: (String, UaspDto) => (Array[Byte], Array[Byte]) = {
    (id, data) => (id.getBytes(), AvroSerializeUtil.encode[UaspDto](data, encoderUaspDto, genericDatumWriterUaspDto))
  }

           ) = new SendScenarioBuilder(senderName, cntIds.cnt, generateDto, convertToBytes).sendScenario
}
