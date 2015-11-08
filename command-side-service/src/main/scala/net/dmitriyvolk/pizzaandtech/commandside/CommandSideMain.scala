package net.dmitriyvolk.pizzaandtech.commandside

import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideConfiguration
import org.springframework.boot.SpringApplication

object CommandSideMain extends App {

  SpringApplication.run(classOf[CommandSideConfiguration])

}
