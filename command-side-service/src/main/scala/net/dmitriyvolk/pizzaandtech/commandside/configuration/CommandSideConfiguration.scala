package net.dmitriyvolk.pizzaandtech.commandside.configuration

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.{ComponentScan, Configuration}

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"net.dmitriyvolk.pizzaandtech.commandside"})
class CommandSideConfiguration {

}
