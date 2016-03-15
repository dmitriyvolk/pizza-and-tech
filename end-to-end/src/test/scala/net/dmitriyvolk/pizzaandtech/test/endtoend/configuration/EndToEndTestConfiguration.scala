package net.dmitriyvolk.pizzaandtech.test.endtoend.configuration

import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration
import net.chrisrichardson.eventstore.json.EventStoreCommonObjectMapping
import net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration.{InMemoryUserMap, HalfBakedAuthenticationConfiguration}
import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideConfiguration
import net.dmitriyvolk.pizzaandtech.generator.DataWriter
import net.dmitriyvolk.pizzaandtech.generator.DataWriter.DataPath
import net.dmitriyvolk.pizzaandtech.generator.configuration.JsonGeneratorConfiguration
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate
import scala.collection.JavaConversions._

@Configuration
@Import(Array(
  classOf[CommandSideConfiguration],
  classOf[HalfBakedAuthenticationConfiguration],
  classOf[JdbcEventStoreConfiguration],
  classOf[JsonGeneratorConfiguration]
))
class EndToEndTestConfiguration {

  @Bean
  def restTemplate() = {
    val restTemplate = new RestTemplate()
    restTemplate.getMessageConverters foreach {
      case mc: MappingJackson2HttpMessageConverter =>
        mc.setObjectMapper(EventStoreCommonObjectMapping.getObjectMapper)
      case _ =>
    }
    restTemplate
  }

  @Bean
  def fakeDataWriter = new InMemoryDataHolder
}

class InMemoryDataHolder extends DataWriter {
  val data = scala.collection.mutable.Map[String, String]()
  override def writeJsonData(folder: DataPath, filename: String, json: String): Unit = {
    val pathString = s"${folder.path}/$filename"
    data.put(pathString, json)
  }

}
