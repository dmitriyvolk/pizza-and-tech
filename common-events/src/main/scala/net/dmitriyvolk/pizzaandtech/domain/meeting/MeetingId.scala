package net.dmitriyvolk.pizzaandtech.domain.meeting

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper

case class MeetingId(entityId: EntityId) extends EntityIdWrapper
