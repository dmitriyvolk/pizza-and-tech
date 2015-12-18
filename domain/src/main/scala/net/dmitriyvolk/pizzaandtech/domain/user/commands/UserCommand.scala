package net.dmitriyvolk.pizzaandtech.domain.user.commands

import net.chrisrichardson.eventstore.Command
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupId, GroupDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.UserBriefInfo

sealed trait UserCommand extends Command
case class RegisterNewUserCommand(briefInfo: UserBriefInfo) extends UserCommand
case class ApplyToJoinGroupCommand(groupId: GroupId) extends UserCommand
case class RecordAcceptanceIntoGroupCommand(groupId: GroupId, groupDetails: GroupDetails) extends UserCommand
case class LeaveGroupCommand(groupId: GroupId) extends UserCommand
case class RecordLeavingGroupCommand(groupId: GroupId) extends UserCommand
