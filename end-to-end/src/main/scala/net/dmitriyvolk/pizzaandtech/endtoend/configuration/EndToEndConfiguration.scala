package net.dmitriyvolk.pizzaandtech.endtoend.configuration

import java.io.File

import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration
import net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration.WebSecurityConfiguration
import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideConfiguration
import net.dmitriyvolk.pizzaandtech.domain.user.{UserBriefInfo, UserService}
import net.dmitriyvolk.pizzaandtech.generator.configuration.JsonGeneratorConfiguration
import net.dmitriyvolk.pizzaandtech.generator.s3.S3DataWriter
import net.dmitriyvolk.pizzaandtech.generator.{DataWriter, FilesystemDataWriter}
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.{Bean, Configuration, Import, Profile}
import org.springframework.context.event.ContextRefreshedEvent

@Configuration
@Import(Array(classOf[WebSecurityConfiguration], classOf[CommandSideConfiguration], classOf[JdbcEventStoreConfiguration], classOf[JsonGeneratorConfiguration]))
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
  def runOnStartup(userService: UserService) = new ApplicationListener[ContextRefreshedEvent] {
    override def onApplicationEvent(event: ContextRefreshedEvent): Unit = {
      userService.registerUser(UserBriefInfo("scott", "Scott Tiger", "tiger"))
    }
  }

}

object EndToEndMain extends App {
  val app = new SpringApplication(classOf[EndToEndConfiguration])
  app.setAdditionalProfiles("localdata")
  app.run()
}
