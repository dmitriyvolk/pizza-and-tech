package net.dmitriyvolk.pizzaandtech.test.generator

import net.dmitriyvolk.pizzaandtech.generator.eventhandlers.GroupEventHandlerService
import net.dmitriyvolk.pizzaandtech.generator.{FilesystemDataWriter, JacksonJsonSerializer, JsonFileStateUpdater}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class JsonFileStateUpdaterSpec extends FeatureSpec with Matchers with GivenWhenThen {

  val contentRoot = java.io.File.createTempFile("p-a-t-", "-json")
  contentRoot.mkdir()

  val dataWriter = new FilesystemDataWriter(contentRoot)
  val ser = new JacksonJsonSerializer

  val service = new GroupEventHandlerService(new JsonFileStateUpdater(ser, dataWriter))

}
