package ru.vood.gatling.test.utils

import ru.vood.gatling.test.entity.Config

object ConfigUtil {

  val groupId = 24

  implicit val sysEnv: Map[String, String] = sys.env
  val config: Config = ConfigUtil.getConf(sysEnv)

  val COUNT_MSG: Int = sys.env.getOrElse("COUNT_USERS", "10000").toInt
//    val COUNT_MSG: Int = sys.env.getOrElse("COUNT_USERS", "2").toInt

  val COUNT_TRANSACTION: Int = sys.env.getOrElse("COUNT_TRANSACTION", "1000").toInt


  def getConf(implicit sysEnv: Map[String, String]): Config = {
    val config: Config = Config(
      bootstrapServers = sysEnv.getOrElse("bootstrapServers", "d5uasp-kfc001lk.corp.dev.vtb:9092,d5uasp-kfc002lk.corp.dev.vtb:9092,d5uasp-kfc003lk.corp.dev.vtb:9092,d5uasp-kfc004lk.corp.dev.vtb:9092,d5uasp-kfc005lk.corp.dev.vtb:9092,d5uasp-kfc006lk.corp.dev.vtb:9092"),
      //      bootstrapServers = sysEnv.getOrElse("bootstrapServers", "172.19.128.77:9092,172.19.128.77:9091"),

      sslTruststoreLocation = sysEnv.getOrElse("sslTruststoreLocation", "C:\\Work\\SSL\\new_APD75-None-kafka-d5-client-uasp-truststore.pfx"),
      sslTruststorePassword = sysEnv.getOrElse("sslTruststorePassword", "p9HrxNIXS4ekSBKKD0Dh<zL7a2!"),
      sslKeystoreLocation = sysEnv.getOrElse("sslKeystoreLocation", "C:\\Work\\SSL\\new_APD75-None-kafka-d5-client-uasp.pfx"),
      sslKeystorePassword = sysEnv.getOrElse("sslKeystorePassword", "p9HrxNIXS4ekSBKKD0Dh<zL7a2!"),
      sslKeyPassword = sysEnv.getOrElse("sslKeyPassword", "p9HrxNIXS4ekSBKKD0Dh<zL7a2!"),
    )

    config
  }

  implicit  val cfg = getConf
}
