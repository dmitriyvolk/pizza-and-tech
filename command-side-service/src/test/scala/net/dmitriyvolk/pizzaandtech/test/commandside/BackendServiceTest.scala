package net.dmitriyvolk.pizzaandtech.test.commandside

import net.dmitriyvolk.pizzaandtech.domain.common.UserIdHolder
import net.dmitriyvolk.pizzaandtech.test.commandside.configuration.CommandSideServiceTestConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.web.client.RestTemplate

trait BackendServiceTest {

  val ctx = new SpringApplication(classOf[CommandSideServiceTestConfiguration]).run()
  val restTemplate = ctx.getBean(classOf[RestTemplate])
  val port = 8080
  val baseUrl = s"http://localhost:$port"
  val userIdHolder = ctx.getBean(classOf[UserIdHolder])

}
