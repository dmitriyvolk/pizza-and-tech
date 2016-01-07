package net.dmitriyvolk.pizzaandtech.generator.configuration

import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideServiceTestConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.{PropertySource, Import, Configuration}

@Configuration
@Import(Array(classOf[CommandSideServiceTestConfiguration], classOf[JsonGeneratorConfiguration]))
class MonolithConfiguration {

}

object MonolithMain extends App {
  System.setProperty("jsonRoot", "/home/xbo/projects/pizza-and-tech/ui/monolith/data")
  val springApp = new SpringApplication(classOf[MonolithConfiguration])
//  springApp.setAdditionalProfiles("monolith")
  springApp.run()
}
