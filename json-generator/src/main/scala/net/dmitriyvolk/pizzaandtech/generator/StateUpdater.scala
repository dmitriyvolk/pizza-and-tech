package net.dmitriyvolk.pizzaandtech.generator

import net.dmitriyvolk.pizzaandtech.domain.group.{GroupId, GroupDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.UserId

trait StateUpdater {

  def addGroupToMembersList(userId: UserId, groupId: GroupId)

  def addMemberToGroup(groupId: GroupId, memberId: UserId)

  def createOrUpdateGroup(groupId: GroupId, groupDetails: GroupDetails)

}
