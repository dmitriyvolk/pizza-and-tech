package net.dmitriyvolk.pizzaandtech.domain.user

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.subscriptions.{EventSubscriber, EventHandler}
import net.chrisrichardson.eventstore.util.EventHandlingUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.group.events.UserAcceptedIntoGroupEvent
import net.dmitriyvolk.pizzaandtech.domain.user.commands.RecordAcceptanceIntoGroupCommand

@EventSubscriber(id="userEventHandlers")
class UserEventHandlers(implicit eventStore: EventStore) {

  @EventHandler
  val recordGroupAcceptance = handlerForEvent[UserAcceptedIntoGroupEvent] { de =>
    existingEntity[User](de.event.userId) <== RecordAcceptanceIntoGroupCommand(GroupId(de.entityId), de.event.groupDetails)
  }

}
