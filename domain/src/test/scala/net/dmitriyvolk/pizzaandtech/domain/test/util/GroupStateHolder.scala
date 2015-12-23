package net.dmitriyvolk.pizzaandtech.domain.test.util

import net.chrisrichardson.eventstore.subscriptions.{DispatchedEvent, EventHandlerMethod, EventSubscriber}
import net.dmitriyvolk.pizzaandtech.domain.group.GroupDetails
import net.dmitriyvolk.pizzaandtech.domain.group.events.{MeetingListUpdatedEvent, GroupCreatedEvent, GroupDetailsUpdatedEvent}
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingDetails
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

@EventSubscriber(id="testGroupStateHolder")
class GroupStateHolder extends StateHolder[GroupDto] {

  @EventHandlerMethod
  def groupCreated(de: DispatchedEvent[GroupCreatedEvent]) = Future {
    add(de.entityId, GroupDto(de.event.groupDetails, Seq()))
  }

  @EventHandlerMethod
  def groupInfoUpdated(de: DispatchedEvent[GroupDetailsUpdatedEvent]) = Future {
    update(de.entityId, _.copy(groupDetails = de.event.groupDetails))
  }

  @EventHandlerMethod
  def meetingListUpdated(de: DispatchedEvent[MeetingListUpdatedEvent]) = Future {
    update(de.entityId, _.copy(meetings = de.event.meetingList.map( _.meetingDetails)))
  }
}

case class GroupDto(groupDetails: GroupDetails, meetings: Seq[MeetingDetails])

