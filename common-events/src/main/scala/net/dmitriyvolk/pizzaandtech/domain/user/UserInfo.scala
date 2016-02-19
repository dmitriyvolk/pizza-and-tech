package net.dmitriyvolk.pizzaandtech.domain.user

case class UserBriefInfo(username: String, fullName: String)
case class UserIdAndBriefInfo(userId: UserId, briefInfo: UserBriefInfo)

