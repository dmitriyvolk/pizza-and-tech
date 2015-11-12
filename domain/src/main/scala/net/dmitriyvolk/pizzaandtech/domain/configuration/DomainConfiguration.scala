package net.dmitriyvolk.pizzaandtech.domain.configuration

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.subscriptions.EnableEventHandlers
import net.chrisrichardson.utils.config.MetricRegistryConfiguration
import net.dmitriyvolk.pizzaandtech.domain.group.GroupService
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingService
import org.springframework.context.annotation.{Import, Bean, Configuration}

@Configuration
@Import(Array(classOf[MetricRegistryConfiguration]))
@EnableEventHandlers
class DomainConfiguration {

  @Bean
  def groupService(eventStore: EventStore) = new GroupService()(eventStore)

  @Bean
  def meetingService(eventStore: EventStore) = new MeetingService()(eventStore)
}
