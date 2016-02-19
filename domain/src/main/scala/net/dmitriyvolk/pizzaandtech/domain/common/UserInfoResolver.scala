package net.dmitriyvolk.pizzaandtech.domain.common

import net.dmitriyvolk.pizzaandtech.domain.user.{UserId, UserIdAndBriefInfo}

trait UserInfoResolver {

  def resolveUserId(userId: UserId): Option[UserIdAndBriefInfo]

}
