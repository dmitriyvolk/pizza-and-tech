package net.dmitriyvolk.pizzaandtech.test.commandside.configuration

import net.chrisrichardson.eventstore.EntityId
import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration
import net.chrisrichardson.eventstore.json.EventStoreCommonObjectMapping
import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideConfiguration
import net.dmitriyvolk.pizzaandtech.domain.common.{UserIdHolder, UserInfoResolver}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserBriefInfo, UserId, UserIdAndBriefInfo}
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

import scala.collection.JavaConversions._

@Configuration
@Import(Array(classOf[CommandSideConfiguration], classOf[JdbcEventStoreConfiguration]))
class CommandSideServiceTestConfiguration {

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
  def userInfoResolver = new UserInfoResolver {
    override def resolveUserId(userId: UserId): Option[UserIdAndBriefInfo] = userId.entityId.id match {
      case "user1" => Some(UserIdAndBriefInfo(userId, UserBriefInfo("One", "First One")))
      case _ => None
    }
  }

  @Bean
  def userIdHolder: UserIdHolder = new UserIdHolder {

    var id: String = null

    override def set(userId: String): Unit = id = userId

    override def get: UserId = UserId(EntityId(id))
  }
}

object CommandSideServiceTestMain extends App {
  SpringApplication.run(classOf[CommandSideServiceTestConfiguration])
}
