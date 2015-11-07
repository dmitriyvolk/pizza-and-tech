package net.dmitriyvolk.pizzaandtech.domain.configuration

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.subscriptions.EnableEventHandlers
import net.dmitriyvolk.pizzaandtech.domain.group.GroupService
import org.springframework.context.annotation.{Bean, Configuration}

@Configuration
@EnableEventHandlers
class DomainConfiguration {

  @Bean
  def groupService(eventStore: EventStore) = new GroupService(eventStore)
}
