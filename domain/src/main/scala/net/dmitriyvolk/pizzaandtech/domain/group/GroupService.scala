package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.util.ServiceUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.common.{UserInfoResolver, UserIdHolder}
import net.dmitriyvolk.pizzaandtech.domain.group.commands._
import net.dmitriyvolk.pizzaandtech.domain.user.UserIdAndBriefInfo
import org.joda.time.DateTime

class GroupService()(implicit val eventStore: EventStore) {

  def createGroup(groupDetails: GroupDetails, currentUser: UserIdAndBriefInfo) = newEntity[Group] <== RegisterNewGroupCommand(currentUser, groupDetails)

  def updateGroupInfo(groupId: GroupId, groupDetails: GroupDetails) = existingEntity[Group](groupId) <== UpdateGroupDetailsCommand(groupDetails)

  def commentOnGroup(groupId: GroupId, commentText: String)(implicit commenter:UserIdAndBriefInfo) =
    existingEntity[Group](groupId) <== CommentOnGroupCommand(CommentDetails(commenter.userId, commenter.briefInfo.fullName, commentText, new DateTime()))


}
