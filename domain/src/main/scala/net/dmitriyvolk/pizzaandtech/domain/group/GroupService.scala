package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.EventStore
import net.chrisrichardson.eventstore.util.ServiceUtil._
import net.dmitriyvolk.pizzaandtech.domain.Implicits._
import net.dmitriyvolk.pizzaandtech.domain.authentication.AuthenticationService
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.commands._
import org.joda.time.DateTime

class GroupService(authenticationService: AuthenticationService)(implicit eventStore: EventStore) {

  val currentUser = authenticationService.getCurrentUser

  def createGroup(groupDetails: GroupDetails) = newEntity[Group] <== RegisterNewGroupCommand(currentUser, groupDetails)

  def updateGroupInfo(groupId: GroupId, groupDetails: GroupDetails) = existingEntity[Group](groupId) <== UpdateGroupDetailsCommand(groupDetails)

  def commentOnGroup(groupId: GroupId, commentText: String) = existingEntity[Group](groupId) <== CommentOnGroupCommand(CommentDetails(currentUser.userId, currentUser.briefInfo.fullName, commentText, new DateTime()))


}
