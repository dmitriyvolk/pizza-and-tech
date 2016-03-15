package net.dmitriyvolk.pizzaandtech.commandside.configuration

import net.chrisrichardson.eventstore.json.EventStoreCommonObjectMapping
import net.dmitriyvolk.pizzaandtech.domain.common.{PizzaAndTechCommonModule, ThreadLocalUserIdHolder, UserIdHolder}
import net.dmitriyvolk.pizzaandtech.domain.configuration.DomainConfiguration
import org.springframework.boot.autoconfigure.web.HttpMessageConverters
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration, Import}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.{CorsRegistry, WebMvcConfigurer, WebMvcConfigurerAdapter}

@Configuration
@Import(Array(classOf[DomainConfiguration]))
@ComponentScan(basePackages = Array("net.dmitriyvolk.pizzaandtech.commandside.web"))
class CommandSideConfiguration {

  @Bean
  def scalaJsonConverter: HttpMessageConverters = {
    val additional  = new MappingJackson2HttpMessageConverter
    val mapper = EventStoreCommonObjectMapping.getObjectMapper
    additional.setObjectMapper(mapper)
    new HttpMessageConverters(additional)
  }

  @Bean
  def corsConfigurer: WebMvcConfigurer = {
    new WebMvcConfigurerAdapter {
      override def addCorsMappings(registry: CorsRegistry): Unit =
        registry
          .addMapping("/**")
            .allowedOrigins("http://localhost:9000", "http://pizza-and-tech-test.s3-website-us-east-1.amazonaws.com", "http://pizza-and-tech.s3-website-us-east-1.amazonaws.com/")
            .allowedMethods("GET", "POST", "OPTIONS", "DELETE", "HEAD")
    }
  }

  @Bean
  def userIdHolder: UserIdHolder = new ThreadLocalUserIdHolder()

}
