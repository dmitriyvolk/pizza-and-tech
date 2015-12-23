package net.dmitriyvolk.pizzaandtech.domain.test.util

import net.chrisrichardson.eventstore.EntityId
import net.chrisrichardson.eventstore.subscriptions.CompoundEventHandler

trait StateHolder[T] extends CompoundEventHandler {
  var entities = new scala.collection.mutable.HashMap[EntityId, T]
  def add(id: EntityId, entity: T): Unit = entities.put(id, entity)
  def update(id: EntityId, f: (T) => T): Unit = entities.put(id, f(entities(id)))
}
