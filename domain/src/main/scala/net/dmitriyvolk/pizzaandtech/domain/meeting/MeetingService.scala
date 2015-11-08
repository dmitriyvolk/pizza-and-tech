package net.dmitriyvolk.pizzaandtech.domain.meeting

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.util.ServiceUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.meeting.commands.{CommentOnMeetingCommand, RsvpToMeetingCommand, ScheduleMeetingCommand, UpdateMeetingDetailsCommand}
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.RsvpDetails
import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import org.springframework.stereotype.Service

@Service
class MeetingService(implicit eventStore: EventStore) {

  def scheduleMeeting(meetingDetails: MeetingDetails) = newEntity[Meeting] <== ScheduleMeetingCommand(meetingDetails)

  def updateMeetingDetails(meetingId: MeetingId, meetingDetails: MeetingDetails) =
    existingEntity[Meeting](meetingId) <== UpdateMeetingDetailsCommand(meetingDetails)

  def commentOnMeeting(meetingId: MeetingId, commentDetails: CommentDetails) =
    existingEntity[Meeting](meetingId) <== CommentOnMeetingCommand(commentDetails)

  def rsvpToMeeting(meetingId: MeetingId, userId: UserId, rsvpDetails: RsvpDetails) =
    existingEntity(meetingId) <== RsvpToMeetingCommand(rsvpDetails, userId)
}