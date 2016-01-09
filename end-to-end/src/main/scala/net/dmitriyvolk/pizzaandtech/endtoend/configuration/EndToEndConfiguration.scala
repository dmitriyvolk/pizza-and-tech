package net.dmitriyvolk.pizzaandtech.endtoend.configuration

import java.io.File

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration
import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideConfiguration
import net.dmitriyvolk.pizzaandtech.domain.authentication.{FakeAuthenticationService, AuthenticationService}
import net.dmitriyvolk.pizzaandtech.generator.{FilesystemDataWriter, DataWriter}
import net.dmitriyvolk.pizzaandtech.generator.configuration.JsonGeneratorConfiguration
import net.dmitriyvolk.pizzaandtech.generator.s3.S3DataWriter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.{Profile, Bean, Configuration, Import}

@Configuration
@Import(Array(classOf[CommandSideConfiguration], classOf[JdbcEventStoreConfiguration], classOf[JsonGeneratorConfiguration]))
class EndToEndConfiguration {

  @Value("${app.config.dataBucket.name}")
  var dataBucketName = "pizza-and-tech-test-data"

  @Bean
  @Profile(Array("s3"))
  def s3DataWriter: DataWriter = new S3DataWriter(bucketName = dataBucketName)

  @Bean
  @Profile(Array("localdata"))
  def localDataWriter: DataWriter = new FilesystemDataWriter(new File("/home/xbo/projects/pizza-and-tech/ui/monolith/data"))

  @Bean
  def fakeAuthenticationService(eventStore: EventStore): AuthenticationService = new FakeAuthenticationService()(eventStore)

}

object EndToEndMain extends App {
  val app = new SpringApplication(classOf[EndToEndConfiguration])
  app.setAdditionalProfiles("localdata")
  app.run()
}
