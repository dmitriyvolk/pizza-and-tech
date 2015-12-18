package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.subscriptions.{EventHandler, EventSubscriber}
import net.chrisrichardson.eventstore.util.EventHandlingUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.group.commands.{ExpellUserFromGroupCommand, AcceptUserIntoGroupCommand, RecordMeetingScheduledCommand}
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.{MeetingDetailsUpdatedEvent, MeetingScheduledEvent}
import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import net.dmitriyvolk.pizzaandtech.domain.user.events.{UserWantsToLeaveGroupEvent, UserAppliedToJoinGroupEvent}

@EventSubscriber(id="groupEventHandlers")
class GroupEventHandlers(implicit eventStore: EventStore) {

  @EventHandler
  val recordNewMeeting = handlerForEvent[MeetingScheduledEvent] { de =>
    existingEntity(de.event.groupId) <== RecordMeetingScheduledCommand(de.event.meetingDetails)
  }

  @EventHandler
  val meetingInfoChanged = handlerForEvent[MeetingDetailsUpdatedEvent] { de =>
    existingEntity(de.event.gr)
  }

  @EventHandler
  val userAppliesToJoinGroup = handlerForEvent[UserAppliedToJoinGroupEvent] { de =>
    existingEntity(de.event.groupId) <== AcceptUserIntoGroupCommand(UserId(de.entityId), de.event.briefInfo)
  }

  @EventHandler
  val userWantsToLeaveGroup = handlerForEvent[UserWantsToLeaveGroupEvent] { de =>
    existingEntity(de.event.groupId) <== ExpellUserFromGroupCommand(UserId(de.entityId))
  }

}
