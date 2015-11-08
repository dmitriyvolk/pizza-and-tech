package net.dmitriyvolk.pizzaandtech.commandside

import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideServiceTestConfiguration
import org.scalatest.{Matchers, WordSpec}
import org.springframework.boot.SpringApplication

class CommandSideServiceSpec extends WordSpec with Matchers {

  val ctx = new SpringApplication(classOf[CommandSideServiceTestConfiguration]).run()

}
