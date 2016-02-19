package net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration

import net.dmitriyvolk.pizzaandtech.domain.common.UserInfoResolver
import net.dmitriyvolk.pizzaandtech.domain.user.{UserId, UserIdAndBriefInfo}

class InMemoryUserMap extends UserInfoResolver {

  val userMap = scala.collection.mutable.Map[UserId, UserIdAndBriefInfo]()

  def getAllUsers: Seq[UserIdAndBriefInfo] = userMap.values.toSeq

  def createUser(info: UserIdAndBriefInfo) = {
    userMap.put(info.userId, info)
  }

  override def resolveUserId(userId: UserId): Option[UserIdAndBriefInfo] = userMap.get(userId)

}
