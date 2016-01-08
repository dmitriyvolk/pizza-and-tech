package net.dmitriyvolk.pizzaandtech.endtoend.configuration

import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration
import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideConfiguration
import net.dmitriyvolk.pizzaandtech.generator.DataWriter
import net.dmitriyvolk.pizzaandtech.generator.configuration.JsonGeneratorConfiguration
import net.dmitriyvolk.pizzaandtech.generator.s3.S3DataWriter
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.{Bean, Configuration, Import}

@Configuration
@Import(Array(classOf[CommandSideConfiguration], classOf[JdbcEventStoreConfiguration], classOf[JsonGeneratorConfiguration]))
class EndToEndConfiguration {

  val dataBucketName = "pizza-and-tech-test-data"

  @Bean
  def s3DataWriter: DataWriter = new S3DataWriter(dataBucketName)

}

object EndToEndMain extends App {
  SpringApplication.run(classOf[EndToEndConfiguration])
}
