package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.subscriptions.{EventHandler, EventSubscriber}
import net.chrisrichardson.eventstore.util.EventHandlingUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.group.commands.{RecordMeetingDetailsUpdatedCommand, AcceptUserIntoGroupCommand, ExpellUserFromGroupCommand, RecordMeetingScheduledCommand}
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingId
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.{MeetingDetailsUpdatedEvent, MeetingScheduledEvent}
import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import net.dmitriyvolk.pizzaandtech.domain.user.events.{UserAppliedToJoinGroupEvent, UserWantsToLeaveGroupEvent}

@EventSubscriber(id="groupEventHandlers")
class GroupEventHandlers(implicit eventStore: EventStore) {

  @EventHandler
  val recordNewMeeting = handlerForEvent[MeetingScheduledEvent] { de =>
    existingEntity[Group](de.event.groupId.entityId) <== RecordMeetingScheduledCommand(MeetingId(de.entityId), de.event.meetingDetails)
  }

  @EventHandler
  val meetingInfoChanged = handlerForEvent[MeetingDetailsUpdatedEvent] { de =>
    existingEntity[Group](de.event.groupId) <== RecordMeetingDetailsUpdatedCommand(MeetingId(de.entityId), de.event.meetingDetails)
  }

  @EventHandler
  val userAppliesToJoinGroup = handlerForEvent[UserAppliedToJoinGroupEvent] { de =>
    existingEntity[Group](de.event.groupId) <== AcceptUserIntoGroupCommand(UserId(de.entityId), de.event.briefInfo)
  }

  @EventHandler
  val userWantsToLeaveGroup = handlerForEvent[UserWantsToLeaveGroupEvent] { de =>
    existingEntity[Group](de.event.groupId) <== ExpellUserFromGroupCommand(UserId(de.entityId))
  }

}
