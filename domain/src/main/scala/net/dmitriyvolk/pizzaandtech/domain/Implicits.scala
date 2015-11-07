package net.dmitriyvolk.pizzaandtech.domain

import net.chrisrichardson.eventstore.EntityId

object Implicits {
  implicit def toEntityId[T <: EntityIdWrapper](wrapperId : T): EntityId = wrapperId.entityId
}

trait EntityIdWrapper {
  val entityId: EntityId
}
