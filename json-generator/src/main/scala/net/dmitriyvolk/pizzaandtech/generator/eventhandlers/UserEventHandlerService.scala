package net.dmitriyvolk.pizzaandtech.generator.eventhandlers

import net.chrisrichardson.eventstore.subscriptions.{CompoundEventHandler, DispatchedEvent, EventHandlerMethod, EventSubscriber}
import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import net.dmitriyvolk.pizzaandtech.domain.user.events.{NewUserRegisteredEvent, GroupListOfUserUpdatedEvent}
import net.dmitriyvolk.pizzaandtech.generator.StateUpdater
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

@Component
@EventSubscriber(id = "userJsonGeneratorHandlers")
class UserEventHandlerService @Autowired() (stateUpdater: StateUpdater) extends CompoundEventHandler {

  @EventHandlerMethod
  def userCreated(de: DispatchedEvent[NewUserRegisteredEvent]) = Future {
    stateUpdater.createOrUpdateUser(UserId(de.entityId), de.event.briefInfo)
  }

  @EventHandlerMethod
  def groupListUpdated(de: DispatchedEvent[GroupListOfUserUpdatedEvent]) = Future {
    stateUpdater.updateGroupListForUser(UserId(de.entityId), de.event.groups)
  }

}
