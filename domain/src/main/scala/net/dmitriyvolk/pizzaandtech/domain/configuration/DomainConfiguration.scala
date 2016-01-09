package net.dmitriyvolk.pizzaandtech.domain.configuration

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.subscriptions.EnableEventHandlers
import net.chrisrichardson.utils.config.MetricRegistryConfiguration
import net.dmitriyvolk.pizzaandtech.domain.authentication.{FakeAuthenticationService, AuthenticationService}
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupEventHandlers, GroupService}
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingService
import net.dmitriyvolk.pizzaandtech.domain.user.{UserEventHandlers, UserService}
import org.springframework.context.annotation.{Import, Bean, Configuration}

@Configuration
@Import(Array(classOf[MetricRegistryConfiguration]))
@EnableEventHandlers
class DomainConfiguration {

  @Bean
  def groupService(authenticationService: AuthenticationService, eventStore: EventStore) = new GroupService(authenticationService)(eventStore)

  @Bean
  def groupEventHandlers(eventStore: EventStore) = new GroupEventHandlers()(eventStore)

  @Bean
  def userEventHandlers(eventStore: EventStore) = new UserEventHandlers()(eventStore)

  @Bean
  def meetingService(eventStore: EventStore) = new MeetingService()(eventStore)

  @Bean
  def userService(eventStore: EventStore) = new UserService()(eventStore)

}
