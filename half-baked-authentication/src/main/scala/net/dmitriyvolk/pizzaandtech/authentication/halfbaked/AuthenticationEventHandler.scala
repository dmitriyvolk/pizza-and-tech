package net.dmitriyvolk.pizzaandtech.authentication.halfbaked

import net.chrisrichardson.eventstore.subscriptions.{DispatchedEvent, EventHandlerMethod, EventSubscriber}
import net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration.CustomUserDetailsService
import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import net.dmitriyvolk.pizzaandtech.domain.user.events.NewUserRegisteredEvent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Component
@EventSubscriber(id = "halfBakedAuthenticationService")
class AuthenticationEventHandler @Autowired()(customUserDetailsSerivce: CustomUserDetailsService) {


  @EventHandlerMethod
  def newUserCreated(de: DispatchedEvent[NewUserRegisteredEvent]) = Future {
    customUserDetailsSerivce.createUser(UserId(de.entityId), de.event.briefInfo.username, de.event.briefInfo.password)
  }

}
