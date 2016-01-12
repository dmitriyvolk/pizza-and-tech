package net.dmitriyvolk.pizzaandtech.generator.eventhandlers

import net.chrisrichardson.eventstore.subscriptions.{CompoundEventHandler, DispatchedEvent, EventHandlerMethod, EventSubscriber}
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.group.events._
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
  def memberListUpdated(de: DispatchedEvent[UserListForGroupUpdatedEvent]) = Future {
    stateUpdater.updateMemberListForGroup(GroupId(de.entityId), de.event.users)
  }

  @EventHandlerMethod
  def meetingListUpdated(de: DispatchedEvent[MeetingListUpdatedEvent]) = Future {
    val groupId: GroupId = GroupId(de.entityId)
    stateUpdater.updateMeetingListForGroup(groupId, de.event.meetingList)
  }

  @EventHandlerMethod
  def commentListUpdated(de: DispatchedEvent[CommentListForGroupUpdatedEvent]) = Future {
    stateUpdater.updateCommentListForGroup(GroupId(de.entityId), de.event.commentList)
  }
}
