package net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration

import net.dmitriyvolk.pizzaandtech.domain.user.{UserBriefInfo, UserIdAndBriefInfo}

/**
  * Created by xbo on 2/11/16.
  */
object AuthImplicits {

  implicit def toUserIdAndBriefInfo(x: CustomUserDetails): UserIdAndBriefInfo = UserIdAndBriefInfo(x.userId, UserBriefInfo(x.username, "FIXME: Fullname goes here", x.password))
}
