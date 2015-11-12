package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.{EntityId, Event, PatternMatchingCommandProcessingAggregate}
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.commands._
import net.dmitriyvolk.pizzaandtech.domain.group.events.{CommentAddedEvent, GroupDetailsUpdatedEvent, GroupCreatedEvent}

case class GroupId(entityId: EntityId) extends EntityIdWrapper

case class Group(groupDetails: GroupDetails, comments: Seq[CommentDetails])
  extends PatternMatchingCommandProcessingAggregate[Group, GroupCommand] {

  def this() = this(null, Seq())

  override def processCommand: PartialFunction[GroupCommand, Seq[Event]] = {
    case RegisterNewGroupCommand(groupDetails) =>
      Seq(GroupCreatedEvent(groupDetails))
    case UpdateGroupDetailsCommand(groupDetails) =>
      Seq(GroupDetailsUpdatedEvent(groupDetails))
    case CommentOnGroupCommand(commentDetails) =>
      Seq(CommentAddedEvent(commentDetails))
  }

  override def applyEvent: PartialFunction[Event, Group] = {
    case GroupCreatedEvent(initialGroupDetails) => copy(groupDetails = initialGroupDetails)
    case GroupDetailsUpdatedEvent(updatedGroupDetails) => copy(groupDetails = updatedGroupDetails)
    case CommentAddedEvent(commentDetails) => copy(comments = comments :+ commentDetails)
  }
}
