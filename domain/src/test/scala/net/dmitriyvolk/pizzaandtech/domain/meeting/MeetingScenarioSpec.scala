package net.dmitriyvolk.pizzaandtech.domain.meeting

import net.chrisrichardson.eventstore.EntityWithIdAndVersion
import net.dmitriyvolk.pizzaandtech.domain.GroupMother
import net.dmitriyvolk.pizzaandtech.domain.group.{Group, GroupId, GroupDetails}
import net.dmitriyvolk.pizzaandtech.domain.test.util.{MeetingDto, SpringContextForFeatureTests}
import org.scalactic.{Equality, Equivalence}
import org.scalatest.{Matchers, GivenWhenThen, FeatureSpec}
import org.scalatest.concurrent.Eventually._
import scala.concurrent.ExecutionContext.Implicits.global

class MeetingScenarioSpec extends FeatureSpec with GivenWhenThen with Matchers with SpringContextForFeatureTests {

  implicit val meetingDetailsEquivalent = new Equivalence[MeetingDetails] {
    def areEquivalent(a: MeetingDetails, b: MeetingDetails): Boolean = {
      a.name == b.name && a.description == b.description
    }
  }

  implicit val meetingDetailsEqual = new Equality[MeetingDetails] {
    override def areEqual(a: MeetingDetails, bAny: Any): Boolean = {
      val b = bAny.asInstanceOf[MeetingDetails]
      a.name == b.name && a.description == b.description
    }
  }

  import GroupMother._
  import MeetingMother._

  feature("Scheduling and changing meetings") {

    scenario("new meeting is scheduled") {

      Given("a new group")
      val groupId = await {
        groupService.createGroup(groupFixtureOne)
      }

      When("scheduling new meeting")
      val meetingId = await {
        meetingService.scheduleMeeting(GroupId(groupId.entityId), meetingFixtureOne)
      }

      Then("meeting should be scheduled")
      eventually {
        meeting(meetingId).meetingDetails should have (
          'name (meetingFixtureOne.name),
          'description (meetingFixtureOne.description)
        )
      }(longWait)

      And("group should be notified")
      eventually {
        group(groupId).meetings should contain only meetingFixtureOne
      }(longWait)
    }

    scenario("existing meeting is updated") {

      Given("an existing meeting")
      val (groupId: EntityWithIdAndVersion[Group], meetingId: EntityWithIdAndVersion[Meeting]) = await {
        for {
          groupId <- groupService.createGroup(makeGroupDetails("2"))
          meetingId <- meetingService.scheduleMeeting(GroupId(groupId.entityId), makeMeetingDetails("2"))
        } yield (groupId, meetingId)
      }

      When("updating meeting details")
      val meetingFixtureThree = makeMeetingDetails("3")
      await {
        meetingService.updateMeetingDetails(MeetingId(meetingId.entityId), meetingFixtureThree)
      }

      Then("meeting entity should be updated")
      eventually {
        meeting(meetingId).meetingDetails should equal(meetingFixtureThree)
      }(longWait)

      And("group should be notified")
      eventually {
        group(groupId).meetings should contain only meetingFixtureThree
      }(longWait)
    }
  }

}
