package net.dmitriyvolk.pizzaandtech.domain.user

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper

/**
  * Created by xbo on 11/3/15.
  */
class User {

}

case class UserId(entityId: EntityId) extends EntityIdWrapper
