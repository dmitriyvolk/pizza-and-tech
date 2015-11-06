package net.dmitriyvolk.pizzaandtech.domain.event.events

import net.chrisrichardson.eventstore.{Event, EntityId}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.event.EventDetails

/**
  * Yes, very unfortunate name. In this case I'm calling group meetings "events".
  */
sealed trait EventEvents extends Event
case class EventCreatedEvent(eventDetails: EventDetails) extends EventEvents
case class EventDetailsUpdatedEvent(eventDetails: EventDetails) extends EventEvents
case class MemberRsvpedEvent(rsvp: RsvpDetails, memberId: EntityId) extends EventEvents
case class MemberChangedRsvpEvent(rsvpDetails: RsvpDetails, memberId: EntityId) extends EventEvents
case class CommentAddedToEventEvent(comment: CommentDetails) extends EventEvents
case class MemberIsComingToEvent(userId: EntityId, comment: String) extends EventEvents
case class MemberRefusedToComeToEvent(userId: EntityId, comment: String) extends EventEvents
case class MemberWillDecideLaterWhetherToComeToEvent(userId: EntityId, comment: String) extends EventEvents
