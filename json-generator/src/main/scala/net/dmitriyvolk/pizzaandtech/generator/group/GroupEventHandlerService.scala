package net.dmitriyvolk.pizzaandtech.generator.group

import net.chrisrichardson.eventstore.subscriptions.{EventHandlerMethod, DispatchedEvent, CompoundEventHandler, EventSubscriber}
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.group.events.{MemberJoinedEvent, GroupDetailsUpdatedEvent, GroupCreatedEvent}
import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import net.dmitriyvolk.pizzaandtech.generator.StateUpdater

import scala.concurrent.Future

@EventSubscriber(id = "groupJsonGeneratorHandlers")
class GroupEventHandlerService(stateUpdater: StateUpdater) extends CompoundEventHandler {

  @EventHandlerMethod
  def groupCreated(de: DispatchedEvent[GroupCreatedEvent]) = Future {
    stateUpdater.createOrUpdateGroup(GroupId(de.entityId), de.event.groupDetails)
  }

  @EventHandlerMethod
  def groupUpdated(de: DispatchedEvent[GroupDetailsUpdatedEvent]) = Future {
    stateUpdater.createOrUpdateGroup(GroupId(de.entityId), de.event.groupDetails)
  }

  @EventHandlerMethod
  def userJoinedGroup(de: DispatchedEvent[MemberJoinedEvent]) = Future {
    val groupId: GroupId = GroupId(de.entityId)
    val userId: UserId = UserId(de.event.memberId)
    stateUpdater.addMemberToGroup(groupId, userId)
    stateUpdater.addGroupToMembersList(userId, groupId)
  }
}
