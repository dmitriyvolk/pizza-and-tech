package net.dmitriyvolk.pizzaandtech.generator.configuration

import java.io.File

import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideServiceTestConfiguration
import net.dmitriyvolk.pizzaandtech.generator.{DataWriter, FilesystemDataWriter}
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.{Bean, Configuration, Import}

@Configuration
@Import(Array(classOf[CommandSideServiceTestConfiguration], classOf[JsonGeneratorConfiguration]))
class MonolithConfiguration {

  @Bean
  def filesystemDataWriter: DataWriter = new FilesystemDataWriter(new File("/home/xbo/projects/pizza-and-tech/ui/monolith/data"))

}

object MonolithMain extends App {
  val springApp = new SpringApplication(classOf[MonolithConfiguration])
  springApp.run()
}
