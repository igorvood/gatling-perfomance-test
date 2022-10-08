package ru.vood.gatling.test.tests

import com.github.mnogu.gatling.kafka.protocol.KafkaProtocol
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import ru.vood.gatling.test.dao.DataGeneratorDao.{generateCrossLinkMdm, generateWay4}
import ru.vood.gatling.test.dto.SomeDto
import ru.vood.gatling.test.scenario.{CountId, SendScenarioBuilder}
import ru.vood.gatling.test.util.KafkaPreDef.PreDef
import ru.vood.gatling.test.utils.ConfigUtil._



class TestScriptGenerator extends Simulation {
  implicit val countId = if (COUNT_MSG / 10000<=0) CountId(1) else CountId(COUNT_MSG / 10000)

  implicit val convertToBytes: (String, SomeDto) => (Array[Byte], Array[Byte]) = {
    (id, data) => (id.getBytes(), data.serializeToAvro._2)
  }

  val COUNT_MESSAGES: Int = COUNT_MSG * COUNT_TRANSACTION
  val CASE_NUMBER: Int = sys.env.getOrElse("CASE_NUMBER", "1").toInt

  private val HA: KafkaProtocol = "dev_bevents__realtime__input_converter__prof__transactions__uaspdto".kafkaProtocol

  private val CA: KafkaProtocol = "dev_rto_batch_ca_deposit_account_case_71_uaspdto".kafkaProtocol

  val caScenario  = SendScenarioBuilder("CA", generateCrossLinkMdm)
  val haScenario = SendScenarioBuilder("HA", generateWay4)


  if (CASE_NUMBER == 1) {
    // Передач холодного и горячего
    setUp(
      caScenario.inject(atOnceUsers(COUNT_MSG)).protocols(CA).andThen(
        haScenario.inject(atOnceUsers(COUNT_MSG)).protocols(HA))
    )
  }
  else if (CASE_NUMBER == 2) {
    // Передач холодного и горячего

    setUp(
      caScenario.inject(atOnceUsers(COUNT_MSG)).protocols(CA),
      haScenario.inject(atOnceUsers(COUNT_MSG)).protocols(HA)
    )

  }

}
