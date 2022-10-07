package ru.vtb.uasp.streaming.mdm.enrichment.itest.scenario

import com.github.mnogu.gatling.kafka.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import ru.vtb.uasp.common.dto.UaspDto
import ru.vtb.uasp.common.utils.avro.AvroSerializeUtil
import ru.vtb.uasp.mdm.enrichment.dao.UaspDtoPredef
import ru.vtb.uasp.streaming.mdm.enrichment.itest.common.Finisheable
import ru.vtb.uasp.streaming.mdm.enrichment.itest.dao.DataGeneratorDao
import ru.vtb.uasp.streaming.mdm.enrichment.itest.entity.Config
import ru.vtb.uasp.streaming.mdm.enrichment.itest.scenario.CommonObject.{encoderUaspDto, genericDatumWriterUaspDto}
import ru.vtb.uasp.streaming.mdm.enrichment.itest.scenario.SendMdmCrossLinksScenarioBuilder._
import ru.vtb.uasp.streaming.mdm.enrichment.itest.scenario.TestEnrichProperty.globalIdEnrichPropertyTest
import ru.vtb.uasp.streaming.mdm.enrichment.itest.service.IdConvertorService

import scala.math.abs

class SendMdmCrossLinksScenarioBuilder(val countUsers: Int, val config: Config) extends Finisheable {

  def isFinished: Boolean = true

  def getSendMdmCrossLinksScenario: ScenarioBuilder = {
    val sendMdmCrossLinksScenario =
      scenario("Mdm Cross Links")
        .exec(session => {
          val localUserId = abs(java.util.UUID.randomUUID().toString.hashCode % 10000).toString
          //          val localUserId =  "1"

          val updateSession = session
            .set(localUserIdSessionName, localUserId)
            .set(globalUserIdSessionName, IdConvertorService.localToGlobal(localUserId))
          updateSession
        })
        .exec(session => {
          val localUserId = session(localUserIdSessionName).as[String]
          val globalUserId = session(globalUserIdSessionName).as[String]

          val localCrossLinkUaspDto = DataGeneratorDao.generateCrossLinkMdm(localUserId, globalUserId)

          val bytesLocalUserId = localUserId.getBytes()
          val bytesCrossLinkUaspDto = AvroSerializeUtil.encode[UaspDto](localCrossLinkUaspDto, encoderUaspDto, genericDatumWriterUaspDto)

          val out = UaspDtoPredef.PreDef(localCrossLinkUaspDto)
            .enrichGlobalId(globalUserId, globalIdEnrichPropertyTest)

          session.set(localCrossLinkUaspDtoSessionName, out)
            .set(bytesLocalUserIdSessionName, bytesLocalUserId)
            .set(bytesCrossLinkUaspDtoSessionName, bytesCrossLinkUaspDto)
        })
        .exec(kafka("kafkaInMdmCrossLinkMessages").send[Array[Byte], Array[Byte]]("${" + bytesLocalUserIdSessionName + "}", "${" + bytesCrossLinkUaspDtoSessionName + "}"))
    //        .asLongAs(_ => consumerMdmStatusDto.getCountMessages < countUsers, "CheckCountMdmStatusMessages") {
    //          exec(session => session).pause(1)
    //        }
    //        .exec(session => {
    //          val globalUserId = session(globalUserIdSessionName).as[String]
    //          val clusterCrossLink: UaspDto = consumerMdmStatusDto.get(globalUserId)
    //          session.set(clusterСrossLinkUaspDtoSessionName, clusterCrossLink)
    //        })
    //        .exec(new CheckAction("CheckerOfCrossLinkMdm", new CheckerOfCrossLinkMdm()))


    sendMdmCrossLinksScenario
  }

}

object SendMdmCrossLinksScenarioBuilder {

  val localCrossLinkUaspDtoSessionName = "localCrossLinkUaspDto"
  val clusterСrossLinkUaspDtoSessionName = "clusterСrossLinkUaspDto"
  private val localUserIdSessionName = "localUserId"
  private val globalUserIdSessionName = "globalUserId"
  private val bytesLocalUserIdSessionName = "bytesLocalUserId"
  private val bytesCrossLinkUaspDtoSessionName = "bytesCrossLinkUaspDto"
}