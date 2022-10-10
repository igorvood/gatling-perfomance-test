package ru.vood.gatling.test.tests

import com.github.mnogu.gatling.kafka.protocol.KafkaProtocol
import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import ru.vood.gatling.test.dao.DataGeneratorDao.{generateCAId, generateCAOther, generateHA}
import ru.vood.gatling.test.scenario.{CountId, SendScenarioBuilder}
import ru.vood.gatling.test.util.KafkaPreDef.PreDef
import ru.vood.gatling.test.utils.ConfigUtil._
import ru.vood.gatling.test.utils.JsonConvertOutService
import ru.vood.gatling.test.utils.dto.{KafkaDto, SomeDto}


class TestScriptGenerator extends Simulation {
  private val i = 10000
  implicit val countId = if (COUNT_MSG <= i) CountId(1) else CountId(i)

  implicit val convertToBytes: (String, SomeDto) => KafkaDto = {
    (id, data) => {
      JsonConvertOutService.serializeToBytes(data)
    }
  }

  val COUNT_MESSAGES: Int = COUNT_MSG * COUNT_TRANSACTION
  val CASE_NUMBER: Int = sys.env.getOrElse("CASE_NUMBER", "1").toInt

  private val CAId: KafkaProtocol = "dev_ivr__uasp_realtime__input_converter__mdm_cross_link__uaspdto".kafkaProtocol

  private val CAOther: KafkaProtocol = "dev_ivr__uasp_realtime__input_converter__mortgage__uaspdto".kafkaProtocol

  private val HA: KafkaProtocol = "dev_ivr__uasp_realtime__input_converter__way4_issuing_operation__uaspdto".kafkaProtocol


  val caIdScenario = SendScenarioBuilder("CAId", generateCAId)
  val caOtherScenario = SendScenarioBuilder("CAOther", generateCAOther)
  val haScenario = SendScenarioBuilder("HA", generateHA)


  if (CASE_NUMBER == 1) {
    // Передач холодного и горячего
    setUp(
      /*caIdScenario.inject(atOnceUsers(COUNT_MSG)).protocols(CAId)
        .andThen(caOtherScenario.inject(atOnceUsers(COUNT_MSG)).protocols(CAOther)
          .andThen(haScenario.inject(atOnceUsers(COUNT_MSG)).protocols(HA))
        )*/

      caOtherScenario.inject(atOnceUsers(COUNT_MSG)).protocols(CAOther)
        .andThen(haScenario.inject(atOnceUsers(COUNT_MSG)).protocols(HA))
    )
  }
  else if (CASE_NUMBER == 2) {
    // Передач холодного и горячего

    setUp(
      caIdScenario.inject(atOnceUsers(COUNT_MSG)).protocols(CAId),
      caOtherScenario.inject(atOnceUsers(COUNT_MSG)).protocols(CAOther),
      haScenario.inject(atOnceUsers(COUNT_MSG)).protocols(HA)
    )

  }

}
