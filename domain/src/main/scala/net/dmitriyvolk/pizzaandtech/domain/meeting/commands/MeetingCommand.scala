package net.dmitriyvolk.pizzaandtech.domain.meeting.commands

import net.chrisrichardson.eventstore.{EntityId, Command}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingDetails
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.RsvpDetails
import net.dmitriyvolk.pizzaandtech.domain.user.UserId

sealed trait MeetingCommand extends Command
case class ScheduleMeetingCommand(groupId: GroupId, meetingDetails: MeetingDetails) extends MeetingCommand
case class UpdateMeetingDetailsCommand(meetingDetails: MeetingDetails) extends MeetingCommand
case class RsvpToMeetingCommand(rsvpDetails: RsvpDetails, userId: UserId) extends MeetingCommand
case class CommentOnMeetingCommand(commentDetails: CommentDetails) extends MeetingCommand