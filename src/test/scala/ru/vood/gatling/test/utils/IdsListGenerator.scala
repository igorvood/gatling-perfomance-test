package ru.vood.gatling.test.utils

import ru.vood.gatling.test.entity.Config

object IdsListGenerator {
  val sysEnv: Map[String, String] = sys.env
  val config: Config = ConfigUtil.getConf(sysEnv)

  val COUNT_USERS: Int = sys.env.getOrElse("COUNT_USERS", "2000000").toInt
  val COUNT_TRANSACTION: Int = sys.env.getOrElse("COUNT_TRANSACTION", "1").toInt


}
