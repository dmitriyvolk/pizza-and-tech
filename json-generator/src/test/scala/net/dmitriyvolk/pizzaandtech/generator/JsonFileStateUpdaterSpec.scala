package net.dmitriyvolk.pizzaandtech.generator

import net.dmitriyvolk.pizzaandtech.generator.eventhandlers.GroupEventHandlerService
import org.scalatest.{GivenWhenThen, Matchers, FeatureSpec}

class JsonFileStateUpdaterSpec extends FeatureSpec with Matchers with GivenWhenThen {

  val contentRoot = java.io.File.createTempFile("p-a-t-", "-json")
  contentRoot.mkdir()

  val dataWriter = new FilesystemDataWriter(contentRoot)
  val ser = new JacksonJsonSerializer

  val service = new GroupEventHandlerService(new JsonFileStateUpdater(ser, dataWriter))

}
