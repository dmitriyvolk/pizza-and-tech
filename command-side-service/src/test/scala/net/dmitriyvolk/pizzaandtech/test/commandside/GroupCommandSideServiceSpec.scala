package net.dmitriyvolk.pizzaandtech.test.commandside

import net.dmitriyvolk.pizzaandtech.commandside.web.{CreateGroupRequest, CreateGroupResponse}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}

class GroupCommandSideServiceSpec extends FeatureSpec with GivenWhenThen with Matchers with BackendServiceTest {

  val groupsUrl = s"$baseUrl/groups"

  feature("A group can be created") {
    scenario("User can register a group with complete and valid information") {
      When("API is invoked")
      userIdHolder.set("user1")
      val createGroupRequest = CreateGroupRequest(name = "new group", description = "new group description")
      val response = restTemplate.postForEntity[CreateGroupResponse](groupsUrl, createGroupRequest, classOf[CreateGroupResponse])
      Then("a new GroupId should be returned")
      response.getBody().groupId should not be null
    }

    scenario("User provides invalid initial group information and group creation fails") {
      pending
    }
  }


}
