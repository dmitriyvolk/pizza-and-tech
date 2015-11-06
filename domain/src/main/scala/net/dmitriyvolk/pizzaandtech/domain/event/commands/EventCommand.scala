package net.dmitriyvolk.pizzaandtech.domain.event.commands

import net.chrisrichardson.eventstore.{EntityId, Command}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.event.EventDetails
import net.dmitriyvolk.pizzaandtech.domain.event.events.RsvpDetails


sealed trait EventCommand extends Command
case class ScheduleEventCommand(eventDetails: EventDetails) extends EventCommand
case class UpdateEventDetailsCommand(eventDetails: EventDetails) extends EventCommand
case class RsvpToEventCommand(rsvpDetails: RsvpDetails, userId: EntityId) extends EventCommand
case class CommentOnEventCommand(commentDetails: CommentDetails) extends EventCommand