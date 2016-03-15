package net.dmitriyvolk.pizzaandtech.domain.meeting

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.util.ServiceUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.meeting.commands.{CommentOnMeetingCommand, RsvpToMeetingCommand, ScheduleMeetingCommand, UpdateMeetingDetailsCommand}
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.RsvpDetails
import net.dmitriyvolk.pizzaandtech.domain.user.UserIdAndBriefInfo
import org.joda.time.DateTime

class MeetingService()(implicit eventStore: EventStore) {


  def scheduleMeeting(groupId: GroupId, meetingDetails: MeetingDetails) = newEntity[Meeting] <== ScheduleMeetingCommand(groupId, meetingDetails)

  def updateMeetingDetails(meetingId: MeetingId, meetingDetails: MeetingDetails) =
    existingEntity[Meeting](meetingId) <== UpdateMeetingDetailsCommand(meetingDetails)

  def commentOnMeeting(meetingId: MeetingId, commenter: UserIdAndBriefInfo, commentText: String) =
    existingEntity[Meeting](meetingId) <== CommentOnMeetingCommand(CommentDetails(commenter.userId, commenter.briefInfo.fullName, commentText, new DateTime()))

  def rsvpToMeeting(meetingId: MeetingId, respondent: UserIdAndBriefInfo, rsvpDetails: RsvpDetails) =
    existingEntity[Meeting](meetingId) <== RsvpToMeetingCommand(respondent, rsvpDetails)
}
