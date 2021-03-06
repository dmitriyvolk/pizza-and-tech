package net.dmitriyvolk.pizzaandtech.generator.eventhandlers

import net.chrisrichardson.eventstore.subscriptions.{EventHandlerMethod, DispatchedEvent, CompoundEventHandler, EventSubscriber}
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingId
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.{RsvpsForMeetingUpdatedEvent, MeetingDetailsUpdatedEvent, CommentListForMeetingUpdatedEvent, MeetingScheduledEvent}
import net.dmitriyvolk.pizzaandtech.generator.StateUpdater
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.concurrent.Future

@Component
@EventSubscriber(id = "meetingJsonGeneratorHandlers")
class MeetingEventHandlerService @Autowired() (stateUpdater: StateUpdater) extends CompoundEventHandler {
  import scala.concurrent.ExecutionContext.Implicits.global

  @EventHandlerMethod
  def meetingScheduled(de: DispatchedEvent[MeetingScheduledEvent]) = Future {
    stateUpdater.createOrUpdateMeeting(MeetingId(de.entityId), de.event.groupId, de.event.meetingDetails)
  }

  @EventHandlerMethod
  def meetingUpdated(de: DispatchedEvent[MeetingDetailsUpdatedEvent]) = Future {
    stateUpdater.createOrUpdateMeeting(MeetingId(de.entityId), de.event.groupId, de.event.meetingDetails)
  }

  @EventHandlerMethod
  def commentListUpdated(de: DispatchedEvent[CommentListForMeetingUpdatedEvent]) = Future {
    stateUpdater.updateCommentListForMeeting(MeetingId(de.entityId), de.event.commentList)
  }

  @EventHandlerMethod
  def rsvpsChanged(de: DispatchedEvent[RsvpsForMeetingUpdatedEvent]) = Future {
    stateUpdater.updateRsvps(MeetingId(de.entityId), de.event.rsvps)
  }
}
