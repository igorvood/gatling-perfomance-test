package ru.vood.gatling.test.dao

import ru.vood.gatling.test.utils.dto.SomeDto

import java.util.Calendar

object DataGeneratorDao {


  def generateHA(userIdLocal: String) = {
    val random = new scala.util.Random
    SomeDto(
      id = userIdLocal,
      dataInt = Map(),
      dataLong = Map(
      ),
      dataFloat = Map(),
      dataDouble = Map(),
      dataDecimal = Map(),
      dataString = Map(
        "tcmt_account_num" -> userIdLocal,
      ),
      dataBoolean = Map(),
      uuid = java.util.UUID.randomUUID().toString,
      process_timestamp = Calendar.getInstance().getTimeInMillis
    )
  }


  def generateCAId(userIdLocal: String) = {
    SomeDto(
      userIdLocal,
      Map(),
      Map(),
      Map(),
      Map(),
      Map(),
      Map(
        "global_id" -> s"global_id_$userIdLocal",
      ),
      Map(),
      java.util.UUID.randomUUID().toString,
      Calendar.getInstance().getTimeInMillis
    )
  }

  def generateCAOther(userIdLocal: String) = {
    SomeDto(
      s"global_id_$userIdLocal",
      Map(),
      Map(),
      Map(),
      Map(),
      Map(),
      Map(
        "global_id" -> s"global_id_$userIdLocal",
      ),
      Map("is_mortgage" -> false),
      java.util.UUID.randomUUID().toString,
      Calendar.getInstance().getTimeInMillis
    )
  }


}
