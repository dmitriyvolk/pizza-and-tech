package net.dmitriyvolk.pizzaandtech.commandside

import net.dmitriyvolk.pizzaandtech.commandside.web.{ScheduleMeetingResponse, ScheduleMeetingRequest, CreateGroupResponse, CreateGroupRequest}
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingDetails
import org.joda.time.DateTime
import org.scalatest.{Matchers, GivenWhenThen, FeatureSpec}

class MeetingCommandSideServiceSpec extends FeatureSpec with GivenWhenThen with Matchers with BackendServiceTest {

  val groupsUrl = s"$baseUrl/groups"
  val meetingsUrl = s"$baseUrl/meetings"

  feature("Meetings can be scheduled and updated") {
    scenario("User schedules a new meeting") {
      Given("an existing group")
      val groupId = restTemplate.postForEntity(groupsUrl, CreateGroupRequest(name = "groupname", description = "group description"), classOf[CreateGroupResponse]).getBody.groupId

      When("calling scheduleMeetingAPI with complete details")
      val meetingDetails = MeetingDetails("myfirstmeeting", "My First Meeting", DateTime.now())
      val meetingId = restTemplate.postForEntity(meetingsUrl, ScheduleMeetingRequest(groupsUrl, meetingDetails), classOf[ScheduleMeetingResponse]).getBody.meetingId

      Then("returned meetingId sholdn't be null")
      meetingId should not be null

    }
  }
}
