package net.dmitriyvolk.pizzaandtech.commandside.configuration

import net.chrisrichardson.eventstore.json.EventStoreCommonObjectMapping
import net.dmitriyvolk.pizzaandtech.domain.configuration.DomainConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.web.HttpMessageConverters
import org.springframework.context.annotation.{Import, Bean, ComponentScan, Configuration}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.{CorsRegistry, WebMvcConfigurerAdapter, WebMvcConfigurer}

@Configuration
@EnableAutoConfiguration
@Import(Array(classOf[DomainConfiguration]))
@ComponentScan(basePackages = Array("net.dmitriyvolk.pizzaandtech.commandside.web"))
class CommandSideConfiguration {

  @Bean
  def scalaJsonConverter: HttpMessageConverters = {
    val additional  = new MappingJackson2HttpMessageConverter
    additional.setObjectMapper(EventStoreCommonObjectMapping.getObjectMapper)
    new HttpMessageConverters(additional)
  }

  @Bean
  def corsConfigurer: WebMvcConfigurer = {
    new WebMvcConfigurerAdapter {
      override def addCorsMappings(registry: CorsRegistry): Unit =
        registry.addMapping("/**").allowedOrigins("http://localhost:9000")
    }
  }

}
