package net.dmitriyvolk.pizzaandtech.domain.group.commands

import net.chrisrichardson.eventstore.{EntityId, Command}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupDetails
import net.dmitriyvolk.pizzaandtech.domain.meeting.{MeetingId, MeetingDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserIdAndBriefInfo, UserId, UserBriefInfo}

/**
  * Created by xbo on 11/3/15.
  */
sealed trait GroupCommand extends Command
case class RegisterNewGroupCommand(creator: UserIdAndBriefInfo, groupDetails: GroupDetails) extends GroupCommand
case class UpdateGroupDetailsCommand(groupDetails: GroupDetails) extends GroupCommand
case class CommentOnGroupCommand(commentDetails: CommentDetails) extends GroupCommand
case class AcceptUserIntoGroupCommand(userId: UserId, userInfo: UserBriefInfo) extends GroupCommand
case class ExpellUserFromGroupCommand(userId: UserId) extends GroupCommand
case class RecordMeetingScheduledCommand(meetingId: MeetingId, meetingDetails: MeetingDetails) extends GroupCommand
case class RecordMeetingDetailsUpdatedCommand(meetingId: MeetingId, meetingDetails: MeetingDetails) extends GroupCommand
