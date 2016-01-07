package net.dmitriyvolk.pizzaandtech.generator

import net.dmitriyvolk.pizzaandtech.generator.eventhandlers.GroupEventHandlerService
import org.scalatest.{GivenWhenThen, Matchers, FeatureSpec}

class JsonFileStateUpdaterSpec extends FeatureSpec with Matchers with GivenWhenThen {

  val contentRoot = java.io.File.createTempFile("p-a-t-", "-json")
  contentRoot.mkdir()

  val jsonWriter:JsonWriter = ???

  val service = new GroupEventHandlerService(new JsonFileStateUpdater(jsonWriter))

}
