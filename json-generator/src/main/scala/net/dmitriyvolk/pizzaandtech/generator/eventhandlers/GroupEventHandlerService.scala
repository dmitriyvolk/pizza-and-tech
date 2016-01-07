package net.dmitriyvolk.pizzaandtech.generator.eventhandlers

import net.chrisrichardson.eventstore.subscriptions.{EventHandlerMethod, DispatchedEvent, CompoundEventHandler, EventSubscriber}
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.group.events.{MeetingListUpdatedEvent, MemberJoinedEvent, GroupDetailsUpdatedEvent, GroupCreatedEvent}
import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import net.dmitriyvolk.pizzaandtech.generator.StateUpdater
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

@Component
@EventSubscriber(id = "groupJsonGeneratorHandlers")
class GroupEventHandlerService @Autowired() (stateUpdater: StateUpdater) extends CompoundEventHandler {

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

  @EventHandlerMethod
  def meetingListUpdated(de: DispatchedEvent[MeetingListUpdatedEvent]) = Future {
    val groupId: GroupId = GroupId(de.entityId)
    stateUpdater.updateMeetingListForGroup(groupId, de.event.meetingList)
  }
}
