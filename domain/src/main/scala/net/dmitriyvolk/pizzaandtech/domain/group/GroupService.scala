package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.util.ServiceUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.commands._
import net.dmitriyvolk.pizzaandtech.domain.user.UserIdAndBriefInfo
import org.joda.time.DateTime

class GroupService()(implicit eventStore: EventStore) {

  def createGroup(groupDetails: GroupDetails, creator: UserIdAndBriefInfo) = newEntity[Group] <== RegisterNewGroupCommand(creator, groupDetails)

  def updateGroupInfo(groupId: GroupId, groupDetails: GroupDetails) = existingEntity[Group](groupId) <== UpdateGroupDetailsCommand(groupDetails)

  def commentOnGroup(groupId: GroupId, commenter:UserIdAndBriefInfo, commentText: String) =
    existingEntity[Group](groupId) <== CommentOnGroupCommand(CommentDetails(commenter.userId, commenter.briefInfo.fullName, commentText, new DateTime()))


}
