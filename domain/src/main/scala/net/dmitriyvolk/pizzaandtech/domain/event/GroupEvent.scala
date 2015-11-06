package net.dmitriyvolk.pizzaandtech.domain.event

import net.chrisrichardson.eventstore.{EntityId, Event, PatternMatchingCommandProcessingAggregate}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.event.commands._
import net.dmitriyvolk.pizzaandtech.domain.event.events._

case class GroupEvent(eventDetails: EventDetails, rsvps: Rsvps, comments: Seq[CommentDetails])
 extends PatternMatchingCommandProcessingAggregate[GroupEvent, EventCommand] {
  override def processCommand: PartialFunction[EventCommand, Seq[Event]] = {
    case ScheduleEventCommand(eventDetails) => Seq(EventCreatedEvent(eventDetails))
    case UpdateEventDetailsCommand(eventDetails) => Seq(EventDetailsUpdatedEvent(eventDetails))
    case CommentOnEventCommand(commentDetails) => Seq(CommentAddedToEventEvent(commentDetails))
    case RsvpToEventCommand(rsvpDetails, userId) =>
      rsvpDetails.going match {
        case Yes => Seq(MemberIsComingToEvent(userId, rsvpDetails.comment))
        case No => Seq(MemberRefusedToComeToEvent(userId, rsvpDetails.comment))
        case Maybe => Seq(MemberWillDecideLaterWhetherToComeToEvent(userId, rsvpDetails.comment))
      }
  }

  override def applyEvent: PartialFunction[Event, GroupEvent] = {
    case EventCreatedEvent(initialEventDetails) => copy(eventDetails = initialEventDetails)
    case EventDetailsUpdatedEvent(updatedEventDetails) => copy(eventDetails = updatedEventDetails)
    case CommentAddedToEventEvent(commentDetails) => copy(comments = comments :+ commentDetails)
    case MemberIsComingToEvent(userId, comment) => copy(rsvps = rsvps.respondYes(userId, comment))
    case MemberRefusedToComeToEvent(userId, comment) => copy(rsvps = rsvps.respondNo(userId, comment))
    case MemberWillDecideLaterWhetherToComeToEvent(userId, comment) => copy(rsvps = rsvps.respondMaybe(userId, comment))
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
