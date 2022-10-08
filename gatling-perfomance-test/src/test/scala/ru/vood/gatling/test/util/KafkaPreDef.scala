package ru.vood.gatling.test.util

import com.github.mnogu.gatling.kafka.Predef.kafka
import com.github.mnogu.gatling.kafka.protocol.KafkaProtocol
import io.gatling.core.Predef.configuration
import ru.vood.gatling.test.entity.Config
import ru.vood.gatling.test.utils.KafkaPropertiesUtil

object KafkaPreDef {

  implicit class PreDef(val self: String) extends AnyVal {

    def kafkaProtocol(implicit config: Config): KafkaProtocol =
      getKafkaProtocol(self, config)

    private def getKafkaProtocol(topicName: String, config: Config): KafkaProtocol = kafka.topic(topicName)
      .properties(KafkaPropertiesUtil.getProducerKafkaProperties(config))

  }

}
