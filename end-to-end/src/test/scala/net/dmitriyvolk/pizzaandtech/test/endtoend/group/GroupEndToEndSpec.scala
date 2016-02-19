package net.dmitriyvolk.pizzaandtech.test.endtoend.group

import net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration.CustomUserIdFilter
import net.dmitriyvolk.pizzaandtech.commandside.web.{CreateGroupResponse, CreateGroupRequest, RegisterUserRequest, RegisterUserResponse}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserIdAndBriefInfo, UserBriefInfo, UserService}
import net.dmitriyvolk.pizzaandtech.test.endtoend.configuration.EndToEndTestConfiguration
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import org.springframework.boot.SpringApplication
import org.springframework.http.{HttpEntity, HttpHeaders}
import org.springframework.web.client.RestTemplate
import org.scalatest.concurrent.Eventually._
import org.scalatest.time.SpanSugar._

class GroupEndToEndSpec extends FeatureSpec with Matchers with GivenWhenThen {
  val ctx = new SpringApplication(classOf[EndToEndTestConfiguration]).run()
  val restTemplate = ctx.getBean(classOf[RestTemplate])
  val port = 8080
  val baseUrl = s"http://localhost:$port"
  val usersUrl = s"$baseUrl/users"
  val groupsUrl = s"$baseUrl/groups"

  import LocalMatchers._
  val createdUserId = restTemplate.postForEntity(usersUrl, RegisterUserRequest("testOne", "Test One"), classOf[RegisterUserResponse])
  eventually (timeout(scaled(3 seconds)), interval(scaled(1 seconds))) {
    createdUserId.getBody.id should beARegisteredUserId
  }



  feature("Group functionality") {

    scenario("Authorized user creates a group") {
      When("new group call is issued")
      val authHeaders = new HttpHeaders()
      authHeaders.add(CustomUserIdFilter.USERID_HEADER_NAME, createdUserId.getBody().id)
      val createGroupRequest = new HttpEntity[CreateGroupRequest](
        CreateGroupRequest("my group", "my description"),
        authHeaders)
      val createdGroup = restTemplate.postForObject(groupsUrl, createGroupRequest, classOf[Map[String, String]])

      Then("group should be created")
      createdGroup("groupId") should not be null

//      And("user should be a member of this group")
      

    }
  }
  class UserShouldExist extends Matcher[String] {
    val listOfUsers = restTemplate.getForObject(usersUrl, classOf[List[Map[String, String]]])
    println(listOfUsers)
    override def apply(idToCheck: String): MatchResult =
      MatchResult(
        listOfUsers.map(_("id")).contains(idToCheck),
        s"User Id $idToCheck should have appeared in the user list",
        s"User Id $idToCheck should not have appeared in the user list"
      )

  }

  object LocalMatchers {
    def beARegisteredUserId = new UserShouldExist
  }
}

