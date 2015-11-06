package net.dmitriyvolk.pizzaandtech.domain.group.commands

import net.chrisrichardson.eventstore.{EntityId, Command}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupDetails

/**
  * Created by xbo on 11/3/15.
  */
sealed trait GroupCommand extends Command
case class RegisterNewGroupCommand(groupDetails: GroupDetails) extends GroupCommand
case class UpdateGroupDetailsCommand(groupDetails: GroupDetails) extends GroupCommand
case class CommentOnGroupCommand(commentDetails: CommentDetails) extends GroupCommand
case class JoinGroupCommand(userId: EntityId) extends GroupCommand
case class LeaveGroupCommand(userId: EntityId) extends GroupCommand
