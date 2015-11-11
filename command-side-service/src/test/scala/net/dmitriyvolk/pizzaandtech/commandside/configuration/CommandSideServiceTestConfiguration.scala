package net.dmitriyvolk.pizzaandtech.commandside.configuration

import net.chrisrichardson.eventstore.json.EventStoreCommonObjectMapping
import org.springframework.context.annotation.{Bean, Import, Configuration}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import scala.collection.JavaConversions._

@Configuration
@Import(Array(classOf[CommandSideConfiguration]))
class CommandSideServiceTestConfiguration {

  @Bean
  def restTemplate() = {
    val restTemplate = new RestTemplate()
    restTemplate.getMessageConverters foreach {
      case mc: MappingJackson2HttpMessageConverter =>
        mc.setObjectMapper(EventStoreCommonObjectMapping.getObjectMapper)
      case _ =>
    }
  }

}
