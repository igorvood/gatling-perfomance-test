package ru.vood.gatling.test

import io.gatling.app.Gatling
import io.gatling.core.config.GatlingPropertiesBuilder
import ru.vood.gatling.test.util.IDEPathHelper

object RunTest extends App {

  val props = new GatlingPropertiesBuilder()
    .resourcesDirectory(IDEPathHelper.mavenResourcesDirectory.toString)
    .resultsDirectory(IDEPathHelper.resultsDirectory.toString)
    .binariesDirectory(IDEPathHelper.mavenBinariesDirectory.toString)
    .simulationClass("ru.vood.gatling.test.tests.TestScriptGenerator")

  Gatling.fromMap(props.build)
}
