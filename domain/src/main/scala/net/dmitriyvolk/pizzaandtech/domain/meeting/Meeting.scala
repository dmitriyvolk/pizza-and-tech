package net.dmitriyvolk.pizzaandtech.domain.meeting

import net.chrisrichardson.eventstore.{EntityId, Event, PatternMatchingCommandProcessingAggregate}
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.meeting.commands._
import net.dmitriyvolk.pizzaandtech.domain.meeting.events._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._



case class Meeting(groupId: GroupId, meetingDetails: MeetingDetails, rsvps: Rsvps, comments: Seq[CommentDetails])
 extends PatternMatchingCommandProcessingAggregate[Meeting, MeetingCommand] {

  def this() = this(null, null, null, null)

  def addComment(commentDetails: CommentDetails) = comments :+ commentDetails

  override def processCommand: PartialFunction[MeetingCommand, Seq[Event]] = {
    case ScheduleMeetingCommand(groupId, meetingDetails) => Seq(MeetingScheduledEvent(groupId, meetingDetails))
    case UpdateMeetingDetailsCommand(meetingDetails) => Seq(MeetingDetailsUpdatedEvent(groupId, meetingDetails))
    case CommentOnMeetingCommand(commentDetails) => Seq(CommentAddedToMeetingEvent(commentDetails), CommentListForMeetingUpdatedEvent(addComment(commentDetails)))
    case RsvpToMeetingCommand(user, rsvpDetails) =>
      rsvpDetails.going match {
        case Yes => Seq(MemberIsComingToMeeting(user, rsvpDetails.comment), RsvpsForMeetingUpdatedEvent(rsvps.respondYes(user, rsvpDetails.comment)))
        case No => Seq(MemberRefusedToComeToMeeting(user, rsvpDetails.comment), RsvpsForMeetingUpdatedEvent(rsvps.respondNo(user, rsvpDetails.comment)))
        case Maybe => Seq(MemberWillDecideLaterWhetherToComeToMeeting(user, rsvpDetails.comment), RsvpsForMeetingUpdatedEvent(rsvps.respondMaybe(user, rsvpDetails.comment)))
      }
  }

  override def applyEvent: PartialFunction[Event, Meeting] = {
    case MeetingScheduledEvent(parentGroupId, initialMeetingDetails) => copy(groupId = parentGroupId, meetingDetails = initialMeetingDetails, rsvps = new Rsvps(Seq(), Seq(), Seq()), Seq())
    case MeetingDetailsUpdatedEvent(groupId, updatedMeetingDetails) => copy(meetingDetails = updatedMeetingDetails)
    case CommentListForMeetingUpdatedEvent(updatedCommentList) => copy(comments = updatedCommentList)
    case RsvpsForMeetingUpdatedEvent(updatedRsvps: Rsvps) => copy(rsvps = updatedRsvps)
    case _ => this
  }
}

