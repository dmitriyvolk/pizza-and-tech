package net.dmitriyvolk.pizzaandtech.test.endtoend

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupMother
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingMother
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.Yes
import net.dmitriyvolk.pizzaandtech.domain.user.{UserId, UserMother}
import org.joda.time.DateTime
import org.scalatest.{GivenWhenThen, Matchers, FeatureSpec}

class MeetingEndToEndSpec extends FeatureSpec with Matchers with GivenWhenThen with TestBase {

  feature("Meetings are scheduled and responded to") {

    Given("an authorized user")
    val groupOwnerId = createdUserId

    Given("An existing group")
    val group = createGroup(GroupMother.makeGroupDetails("theGroup"), groupOwnerId)
    val groupId = group("groupId")

    scenario("Authorized user schedules a meeting") {

      When("user schedules meeting")
      val meeting = createMeeting(groupId, MeetingMother.makeMeetingDetails("meeting1"), groupOwnerId)

      Then("valid meeting id is returned")
      meeting("meetingId") should not be null

    }

    scenario("User comments to meeting") {

      Given("an existing meeting")
      val meetingId = createMeeting(groupId, MeetingMother.makeMeetingDetails("meetingTwo"), groupOwnerId)("meetingId")

      Given("an existing user other than the meeting creator")
      val commenter = createUser(UserMother.userInfo("Two"))

      When("user comments on meeting")
      val result = commentOnMeeting(meetingId, CommentDetails(UserId(EntityId(commenter)), "FIXME!", "Meeting comment", new DateTime))

      Then("call should succeed")
      result("meetingId") should not be null
    }

    scenario("User responds to the meeting") {

      Given("an existing meeting")
      val meeting = createMeeting(groupId, MeetingMother.makeMeetingDetails("meeting2"), groupOwnerId)

      And("and an existing user")
      val responderId = createUser(UserMother.userInfo("Three"))

      When("a different user RSVPs to the meeting")
      val result = respondToMeeting(meeting("meetingId"), Yes, responderId)

      Then("something")
      result("meetingId") should not be null
    }

  }
}
