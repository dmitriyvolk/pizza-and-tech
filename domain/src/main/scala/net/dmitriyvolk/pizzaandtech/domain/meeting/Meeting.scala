package net.dmitriyvolk.pizzaandtech.domain.meeting

import net.chrisrichardson.eventstore.{EntityId, Event, PatternMatchingCommandProcessingAggregate}
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.meeting.commands._
import net.dmitriyvolk.pizzaandtech.domain.meeting.events._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._



case class Meeting(meetingDetails: MeetingDetails, rsvps: Rsvps, comments: Seq[CommentDetails])
 extends PatternMatchingCommandProcessingAggregate[Meeting, MeetingCommand] {

  def this() = this(null, null, null)
  override def processCommand: PartialFunction[MeetingCommand, Seq[Event]] = {
    case ScheduleMeetingCommand(groupId, meetingDetails) => Seq(MeetingScheduledEvent(groupId, meetingDetails))
    case UpdateMeetingDetailsCommand(meetingDetails) => Seq(MeetingDetailsUpdatedEvent(meetingDetails))
    case CommentOnMeetingCommand(commentDetails) => Seq(CommentAddedToMeetingEvent(commentDetails))
    case RsvpToMeetingCommand(rsvpDetails, userId) =>
      rsvpDetails.going match {
        case Yes => Seq(MemberIsComingToMeeting(userId, rsvpDetails.comment))
        case No => Seq(MemberRefusedToComeToMeeting(userId, rsvpDetails.comment))
        case Maybe => Seq(MemberWillDecideLaterWhetherToComeToMeeting(userId, rsvpDetails.comment))
      }
  }

  override def applyEvent: PartialFunction[Event, Meeting] = {
    case MeetingScheduledEvent(groupId, initialMeetingDetails) => copy(meetingDetails = initialMeetingDetails, rsvps = new Rsvps(Seq(), Seq(), Seq()), Seq())
    case MeetingDetailsUpdatedEvent(updatedMeetingDetails) => copy(meetingDetails = updatedMeetingDetails)
    case CommentAddedToMeetingEvent(commentDetails) => copy(comments = comments :+ commentDetails)
    case MemberIsComingToMeeting(userId, comment) => copy(rsvps = rsvps.respondYes(userId, comment))
    case MemberRefusedToComeToMeeting(userId, comment) => copy(rsvps = rsvps.respondNo(userId, comment))
    case MemberWillDecideLaterWhetherToComeToMeeting(userId, comment) => copy(rsvps = rsvps.respondMaybe(userId, comment))
  }
}

case class UserAndComment(userId: EntityId, comment: String)
case class Rsvps(yes: Seq[UserAndComment], no: Seq[UserAndComment], maybe: Seq[UserAndComment]) {

  def respondYes(userId: EntityId, comment: String): Rsvps = copy(yes = yes :+ UserAndComment(userId, comment))
  def respondNo(userId: EntityId, comment: String): Rsvps = copy(no = no :+ UserAndComment(userId, comment))
  def respondMaybe(userId: EntityId, comment: String): Rsvps = copy(maybe = maybe :+ UserAndComment(userId, comment))

  def containsRsvpOf(userId: EntityId): Boolean = {
    def containsUserId(seq: Seq[UserAndComment], userId: EntityId) = seq.exists(_.userId == userId)
    List(yes, no, maybe).foldLeft(false)((previous, s) => previous || containsUserId(s, userId))
  }

}
object Rsvps {
  def apply() = {
    new Rsvps(Seq(), Seq(), Seq())
  }
}
