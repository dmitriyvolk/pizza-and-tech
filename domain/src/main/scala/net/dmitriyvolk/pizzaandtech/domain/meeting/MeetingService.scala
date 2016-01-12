package net.dmitriyvolk.pizzaandtech.domain.meeting

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.util.ServiceUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.authentication.AuthenticationService
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.meeting.commands.{CommentOnMeetingCommand, RsvpToMeetingCommand, ScheduleMeetingCommand, UpdateMeetingDetailsCommand}
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.RsvpDetails
import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import org.joda.time.DateTime

class MeetingService(authenticationService: AuthenticationService)(implicit eventStore: EventStore) {

  val currentUser = authenticationService.getCurrentUser

  def scheduleMeeting(groupId: GroupId, meetingDetails: MeetingDetails) = newEntity[Meeting] <== ScheduleMeetingCommand(groupId, meetingDetails)

  def updateMeetingDetails(meetingId: MeetingId, meetingDetails: MeetingDetails) =
    existingEntity[Meeting](meetingId) <== UpdateMeetingDetailsCommand(meetingDetails)

  def commentOnMeeting(meetingId: MeetingId, commentText: String) =
    existingEntity[Meeting](meetingId) <== CommentOnMeetingCommand(CommentDetails(currentUser.userId, currentUser.briefInfo.fullName, commentText, new DateTime()))

  def rsvpToMeeting(meetingId: MeetingId, userId: UserId, rsvpDetails: RsvpDetails) =
    existingEntity(meetingId) <== RsvpToMeetingCommand(rsvpDetails, userId)
}
