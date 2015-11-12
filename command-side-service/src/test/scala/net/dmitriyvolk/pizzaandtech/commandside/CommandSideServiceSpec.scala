package net.dmitriyvolk.pizzaandtech.commandside

import net.dmitriyvolk.pizzaandtech.commandside.configuration.CommandSideServiceTestConfiguration
import net.dmitriyvolk.pizzaandtech.commandside.web.{CreateGroupRequest, CreateGroupResponse}
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import org.springframework.boot.SpringApplication
import org.springframework.web.client.RestTemplate

class CommandSideServiceSpec extends FeatureSpec with GivenWhenThen with Matchers {

  val ctx = new SpringApplication(classOf[CommandSideServiceTestConfiguration]).run()
  val restTemplate = ctx.getBean(classOf[RestTemplate])
  val port = 8080
  val baseUrl = s"http://localhost:$port"
  val groupsUrl = s"$baseUrl/groups"

  feature("A group can be created") {
    scenario("User can register a group with complete and valid information") {
      When("API is invoked")
      val createGroupRequest = CreateGroupRequest(name = "new group", description = "new group description")
      val response = restTemplate.postForEntity[CreateGroupResponse](groupsUrl, createGroupRequest, classOf[CreateGroupResponse])
      Then("a new GroupId should be returned")
      response.getBody().groupId should not be null
    }
  }

}
