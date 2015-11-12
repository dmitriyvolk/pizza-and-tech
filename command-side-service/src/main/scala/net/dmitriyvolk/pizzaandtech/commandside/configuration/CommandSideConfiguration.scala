package net.dmitriyvolk.pizzaandtech.commandside.configuration

import net.chrisrichardson.eventstore.json.EventStoreCommonObjectMapping
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.web.HttpMessageConverters
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = Array("net.dmitriyvolk.pizzaandtech.domain", "net.dmitriyvolk.pizzaandtech.commandside"))
class CommandSideConfiguration {

  @Bean
  def scalaJsonConverter: HttpMessageConverters = {
    val additional  = new MappingJackson2HttpMessageConverter
    additional.setObjectMapper(EventStoreCommonObjectMapping.getObjectMapper)
    new HttpMessageConverters(additional)
  }

}
