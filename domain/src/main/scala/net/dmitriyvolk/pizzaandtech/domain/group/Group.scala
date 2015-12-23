package net.dmitriyvolk.pizzaandtech.domain.group

import net.chrisrichardson.eventstore.{EntityId, Event, PatternMatchingCommandProcessingAggregate}
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.commands._
import net.dmitriyvolk.pizzaandtech.domain.group.events._
import net.dmitriyvolk.pizzaandtech.domain.meeting.{MeetingIdAndDetails, MeetingDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserIdAndBriefInfo, UserBriefInfo, UserId}

case class Group(groupDetails: GroupDetails, comments: Seq[CommentDetails], meetings: Seq[MeetingIdAndDetails], users: Seq[UserIdAndBriefInfo])
  extends PatternMatchingCommandProcessingAggregate[Group, GroupCommand] {

  def this() = this(null, Seq(), Seq(), Seq())

  def appendMeetingList(newMeeting: MeetingIdAndDetails) = meetings :+ newMeeting
  def updateMeetingList(updatedMeeting: MeetingIdAndDetails) = meetings map { case MeetingIdAndDetails(updatedMeeting.id, _) => updatedMeeting; case x => x}
  def userJoins(userId: UserId, userInfo: UserBriefInfo) = users:+ UserIdAndBriefInfo(userId, userInfo)
  def userLeaves(userId: UserId) = users.filter(_.userId != userId)

  override def processCommand: PartialFunction[GroupCommand, Seq[Event]] = {
    case RegisterNewGroupCommand(groupDetails) =>
      Seq(GroupCreatedEvent(groupDetails))
    case UpdateGroupDetailsCommand(groupDetails) =>
      Seq(GroupDetailsUpdatedEvent(groupDetails))
    case CommentOnGroupCommand(commentDetails) =>
      Seq(CommentAddedEvent(commentDetails))
    case RecordMeetingScheduledCommand(meetingId, meetingDetails) =>
      Seq(MeetingListUpdatedEvent(appendMeetingList(MeetingIdAndDetails(meetingId, meetingDetails))))
    case RecordMeetingDetailsUpdatedCommand(meetingId, meetingDetails) =>
      Seq(MeetingListUpdatedEvent(updateMeetingList(MeetingIdAndDetails(meetingId, meetingDetails))))
    case AcceptUserIntoGroupCommand(userId, userInfo) =>
      Seq(UserListForGroupUpdatedEvent(userJoins(userId, userInfo)), UserAcceptedIntoGroupEvent(userId, groupDetails))
    case ExpellUserFromGroupCommand(userId) =>
      Seq(UserListForGroupUpdatedEvent(userLeaves(userId)), UserLeftGroupEvent(userId))
  }

  override def applyEvent: PartialFunction[Event, Group] = {
    case GroupCreatedEvent(initialGroupDetails) => copy(groupDetails = initialGroupDetails)
    case GroupDetailsUpdatedEvent(updatedGroupDetails) => copy(groupDetails = updatedGroupDetails)
    case CommentAddedEvent(commentDetails) => copy(comments = comments :+ commentDetails)
    case MeetingListUpdatedEvent(updatedMeetingList) => copy(meetings = updatedMeetingList)
  }
}
