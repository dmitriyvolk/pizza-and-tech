package net.dmitriyvolk.pizzaandtech.domain.test.util

import net.chrisrichardson.eventstore.EntityWithIdAndVersion
import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration
import net.dmitriyvolk.pizzaandtech.domain.configuration.DomainConfiguration
import net.dmitriyvolk.pizzaandtech.domain.group.{Group, GroupService}
import net.dmitriyvolk.pizzaandtech.domain.meeting.{Meeting, MeetingService}
import org.scalatest.time.{Millis, Span}
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.{Import, Bean, Configuration}
import scala.concurrent.duration._
import org.scalatest.concurrent.Eventually._

import scala.concurrent.{Await, Future}

trait SpringContextForFeatureTests {

  val ctx = new SpringApplication(classOf[FeatureTestConfiguration]).run()
  def await[T](body : => Future[T]) = Await.result(body, 500 milliseconds)
  val longWait = PatienceConfig(Span(5000, Millis), Span(50, Millis))
  val groupService = ctx.getBean(classOf[GroupService])
  val groupHolder = ctx.getBean(classOf[GroupStateHolder])
  def group(id: EntityWithIdAndVersion[Group]) = groupHolder.entities(id.entityId)
  val meetingService = ctx.getBean(classOf[MeetingService])
  val meetingHolder = ctx.getBean(classOf[MeetingStateHolder])
  def meeting(id: EntityWithIdAndVersion[Meeting]) = meetingHolder.entities(id.entityId)

}

@Configuration
@Import(Array(classOf[JdbcEventStoreConfiguration], classOf[DomainConfiguration]))
class FeatureTestConfiguration {

  @Bean
  def groupHolder = new GroupStateHolder

  @Bean
  def meetingHolder = new MeetingStateHolder
}
