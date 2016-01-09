package net.dmitriyvolk.pizzaandtech.domain.user

import net.chrisrichardson.eventstore.{Event, PatternMatchingCommandProcessingAggregate, EntityId}
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupIdAndDetails, GroupId, GroupDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.commands._
import net.dmitriyvolk.pizzaandtech.domain.user.events._

case class User(briefInfo: UserBriefInfo, groups: Seq[GroupIdAndDetails])
  extends PatternMatchingCommandProcessingAggregate[User, UserCommand] {

  def this() = this(null, Seq())

  def joinGroup(groupId: GroupId, groupDetails: GroupDetails) = groups :+ GroupIdAndDetails(groupId, groupDetails)
  def leaveGroup(groupId: GroupId) = groups.filter(_.groupId != groupId)

  override def processCommand: PartialFunction[UserCommand, Seq[Event]] = {
    case RegisterNewUserCommand(briefInfo) =>
      Seq(NewUserRegisteredEvent(briefInfo))
    case ApplyToJoinGroupCommand(groupId) =>
      Seq(UserAppliedToJoinGroupEvent(groupId, briefInfo))
    case RecordAcceptanceIntoGroupCommand(groupId, groupDetails) =>
      Seq(JoinedGroupEvent(groupDetails), GroupListOfUserUpdatedEvent(joinGroup(groupId, groupDetails)))
    case LeaveGroupCommand(groupId) =>
      Seq(UserWantsToLeaveGroupEvent(groupId))
    case RecordLeavingGroupCommand(groupId) =>
      Seq(LeftGroupEvent(groupId), GroupListOfUserUpdatedEvent(leaveGroup(groupId)))
  }

  override def applyEvent: PartialFunction[Event, User] = {
    case NewUserRegisteredEvent(newUserBriefInfo) => copy(briefInfo = newUserBriefInfo)
    case GroupListOfUserUpdatedEvent(updatedGroups) => copy(groups = updatedGroups)
    case _ => this
  }
}


