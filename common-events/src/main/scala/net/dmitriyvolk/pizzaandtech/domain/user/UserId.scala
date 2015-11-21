package net.dmitriyvolk.pizzaandtech.domain.user

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper

case class UserId(entityId: EntityId) extends EntityIdWrapper
