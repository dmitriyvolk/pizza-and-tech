package net.dmitriyvolk.pizzaandtech.domain.common

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.user.UserId

trait UserIdHolder {
  def set(userId: String): Unit
  def get: UserId
}
class ThreadLocalUserIdHolder extends UserIdHolder {
  val holder = new ThreadLocal[String]()
  override def set(userId: String): Unit = {
    holder.set(userId)
  }
  override def get: UserId = UserId(new EntityId(holder.get()))
}
