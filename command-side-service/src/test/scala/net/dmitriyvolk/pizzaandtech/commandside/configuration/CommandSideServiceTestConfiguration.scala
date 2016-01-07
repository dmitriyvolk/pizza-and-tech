package net.dmitriyvolk.pizzaandtech.commandside.configuration

import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration
import net.chrisrichardson.eventstore.json.EventStoreCommonObjectMapping
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

import scala.collection.JavaConversions._

@Configuration
@Import(Array(classOf[CommandSideConfiguration], classOf[JdbcEventStoreConfiguration]))
class CommandSideServiceTestConfiguration {

  @Bean
  def restTemplate() = {
    val restTemplate = new RestTemplate()
    restTemplate.getMessageConverters foreach {
      case mc: MappingJackson2HttpMessageConverter =>
        mc.setObjectMapper(EventStoreCommonObjectMapping.getObjectMapper)
      case _ =>
    }
    restTemplate
  }
}

object CommandSideServiceTestMain extends App {
  SpringApplication.run(classOf[CommandSideServiceTestConfiguration])
}
