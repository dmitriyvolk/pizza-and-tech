package net.dmitriyvolk.pizzaandtech.commandside

import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideServiceTestConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.web.client.RestTemplate

trait BackendServiceTest {

  val ctx = new SpringApplication(classOf[CommandSideServiceTestConfiguration]).run()
  val restTemplate = ctx.getBean(classOf[RestTemplate])
  val port = 8080
  val baseUrl = s"http://localhost:$port"

}
