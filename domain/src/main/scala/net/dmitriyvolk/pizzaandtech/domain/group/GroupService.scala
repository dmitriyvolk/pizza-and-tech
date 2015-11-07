package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.util.ServiceUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.commands._
import net.dmitriyvolk.pizzaandtech.domain.user.UserId

class GroupService(implicit eventStore: EventStore) {

  def createGroup(groupDetails: GroupDetails) = newEntity[Group] <== RegisterNewGroupCommand(groupDetails)

  def updateGroupInfo(groupId: GroupId, groupDetails: GroupDetails) = existingEntity[Group](groupId) <== UpdateGroupDetailsCommand(groupDetails)

  def commentOnGroup(groupId: GroupId, commentDetails: CommentDetails) = existingEntity[Group](groupId) <== CommentOnGroupCommand(commentDetails)

  def joinGroup(groupId: GroupId, userId: UserId) = existingEntity[Group](groupId) <== JoinGroupCommand(userId)

  def leaveGroup(groupId: GroupId, userId: UserId) = existingEntity[Group](groupId) <== LeaveGroupCommand(userId)

}
