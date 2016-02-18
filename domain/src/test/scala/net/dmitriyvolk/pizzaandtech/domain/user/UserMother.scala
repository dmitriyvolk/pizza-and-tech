package net.dmitriyvolk.pizzaandtech.domain.user

import net.chrisrichardson.eventstore.EntityId

/**
  * Created by xbo on 1/15/16.
  */
object UserMother {

  def apply(userId: String): UserIdAndBriefInfo = apply(userId, s"user-$userId", s"User $userId")

  def apply(userId: String, firstName: String, lastName: String) = UserIdAndBriefInfo(UserId(EntityId(userId)), UserBriefInfo(s"$firstName.$lastName", s"$firstName $lastName", s"pass$firstName$lastName"))

  val scottTiger = UserMother("1", "Scott", "Tiger")
}
