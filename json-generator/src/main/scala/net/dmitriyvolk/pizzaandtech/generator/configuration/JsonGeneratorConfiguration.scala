package net.dmitriyvolk.pizzaandtech.generator.configuration

import org.springframework.context.annotation.{ComponentScan, Configuration}

@Configuration
@ComponentScan(basePackages = Array("net.dmitriyvolk.pizzaandtech.generator"))
class JsonGeneratorConfiguration {

}
