package net.dmitriyvolk.pizzaandtech.domain.group.events

import net.chrisrichardson.eventstore.{EntityId, Event}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupDetails

sealed trait GroupEvents extends Event
case class GroupCreatedEvent(groupDetails: GroupDetails) extends GroupEvents
case class GroupDetailsUpdatedEvent(groupDetails: GroupDetails) extends GroupEvents
case class MemberJoinedEvent(memberId: EntityId) extends GroupEvents
case class EventScheduledEvent(eventId: EntityId) extends GroupEvents
case class CommentAddedEvent(comment: CommentDetails) extends GroupEvents