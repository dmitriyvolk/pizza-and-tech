package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper

case class GroupId(entityId: EntityId) extends EntityIdWrapper
