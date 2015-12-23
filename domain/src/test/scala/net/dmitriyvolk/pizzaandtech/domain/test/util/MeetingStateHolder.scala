package net.dmitriyvolk.pizzaandtech.domain.test.util

import net.chrisrichardson.eventstore.subscriptions.{DispatchedEvent, EventHandlerMethod, EventSubscriber}
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingDetails
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.{MeetingDetailsUpdatedEvent, MeetingScheduledEvent}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@EventSubscriber(id="testMeetingStateHolder")
class MeetingStateHolder extends StateHolder[MeetingDto] {

  @EventHandlerMethod
  def meetingCreated(de: DispatchedEvent[MeetingScheduledEvent]) = Future {
    add(de.entityId, MeetingDto(de.event.meetingDetails))
  }

  @EventHandlerMethod
  def meetingUpdated(de: DispatchedEvent[MeetingDetailsUpdatedEvent]) = Future {
    update(de.entityId, _.copy(meetingDetails = de.event.meetingDetails))
  }
}

case class MeetingDto(meetingDetails: MeetingDetails)
