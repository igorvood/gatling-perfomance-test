package ru.vood.gatling.test.utils

import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import ru.vood.gatling.test.utils.dto.{Identity, OutDtoWithErrors}

import java.nio.charset.StandardCharsets
import scala.util.{Failure, Success, Try}

object JsonConvertInService extends Serializable {

  def deselialize[T <: Identity](value: Array[Byte])(implicit reads: Reads[T]): Either[OutDtoWithErrors, T] = {
    val valueStr = Option(value).map(k => new String(k, StandardCharsets.UTF_8)).orNull

    val tryData = Try {
      Json.parse(valueStr).validate[T]
      match {
        case JsSuccess(dto, _) => Right(dto)
        case JsError(errors) =>
          val errStr = errors
            .map(err => "error by path " + (err._1 -> err._2.map(e => e.message).mkString(",")))
            .mkString("\n")
          Left(OutDtoWithErrors(
            sourceValue = valueStr,
            errors = List(errStr)))
      }
    }

    val errorsOrDto: Either[OutDtoWithErrors, T] = tryData match {
      case Success(d) => d
      case Failure(exception) => Left(OutDtoWithErrors(
        sourceValue = valueStr,
        errors = List(exception.getMessage)))

    }
    errorsOrDto

  }
}
