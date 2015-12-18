package net.dmitriyvolk.pizzaandtech.domain.user

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.util.ServiceUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.user.commands.{LeaveGroupCommand, ApplyToJoinGroupCommand, RegisterNewUserCommand}

class UserService(implicit eventStore: EventStore) {

  def registerUser(briefInfo: UserBriefInfo) = newEntity[User] <== RegisterNewUserCommand(briefInfo)

  def joinGroup(userId: UserId, groupId: GroupId) = existingEntity[User](userId) <== ApplyToJoinGroupCommand(groupId)

  def leaveGroup(userId: UserId, groupId: GroupId) = existingEntity[User](userId) <== LeaveGroupCommand(groupId)
}
