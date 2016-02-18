package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.EntityWithIdAndVersion
import net.chrisrichardson.eventstore.jdbc.config.JdbcEventStoreConfiguration
import net.dmitriyvolk.pizzaandtech.domain.configuration.DomainConfiguration
import net.dmitriyvolk.pizzaandtech.domain.test.util.GroupStateHolder
import net.dmitriyvolk.pizzaandtech.domain.user.UserMother
import org.scalatest.concurrent.Eventually._
import org.scalatest.time.{Millis, Span}
import org.scalatest.{GivenWhenThen, Matchers, WordSpec}
import org.springframework.boot.SpringApplication
import org.springframework.context.annotation.{Bean, Configuration, Import}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class GroupServiceSpec extends WordSpec with Matchers with GivenWhenThen {

  val ctx = new SpringApplication(classOf[ServiceTestConfiguration]).run()
  def await[T](body : => Future[T]) = Await.result(body, 500 milliseconds)
  val longWait = PatienceConfig(Span(5000, Millis), Span(50, Millis))

  "Group Service" should {

    val groupService = ctx.getBean(classOf[GroupService])
    val groupHolder = ctx.getBean(classOf[GroupStateHolder])
    val groupOwner = UserMother("user-1", "First", "User")

    def group(id: EntityWithIdAndVersion[Group]) = groupHolder.entities(id.entityId)

    "create group" in {

      val groupId = await {
        groupService.createGroup(GroupDetails("group", "description"), groupOwner)
      }
      eventually {
        group(groupId) should have(
          'groupDetails (GroupDetails("group", "description")),
          'meetings (Seq())
        )
      }(longWait)
    }

    "update group details" in {
      val groupId = await {
        groupService.createGroup(GroupDetails("group", "description"), groupOwner)
      }
      await {
        groupService.updateGroupInfo(GroupId(groupId.entityId), GroupDetails("group_upd", "description_upd"))
      }
      eventually {
        group(groupId) should have (
          'groupDetails (GroupDetails("group_upd", "description_upd")),
          'meetings (Seq())
        )
      }(longWait)
    }

  }

}



@Configuration
@Import(Array(classOf[JdbcEventStoreConfiguration], classOf[DomainConfiguration]))
class ServiceTestConfiguration {
  @Bean
  def groupStateHolder = new GroupStateHolder

}

