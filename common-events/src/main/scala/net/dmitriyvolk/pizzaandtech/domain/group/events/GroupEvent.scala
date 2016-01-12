package net.dmitriyvolk.pizzaandtech.domain.group.events

import net.chrisrichardson.eventstore.{EntityId, Event}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupId, GroupDetails}
import net.dmitriyvolk.pizzaandtech.domain.meeting.{MeetingIdAndDetails, MeetingDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserIdAndBriefInfo, UserId, UserBriefInfo}

sealed trait GroupEvent extends Event
case class GroupCreatedEvent(groupDetails: GroupDetails) extends GroupEvent
case class GroupDetailsUpdatedEvent(groupDetails: GroupDetails) extends GroupEvent
case class MemberJoinedEvent(memberId: EntityId) extends GroupEvent
case class EventScheduledEvent(eventId: EntityId) extends GroupEvent
case class CommentAddedEvent(comment: CommentDetails) extends GroupEvent
case class CommentListForGroupUpdatedEvent(commentList: Seq[CommentDetails]) extends GroupEvent
case class NewMeetingRecordedEvent(meetingDetails: MeetingDetails) extends GroupEvent
case class MeetingListUpdatedEvent(meetingList: Seq[MeetingIdAndDetails]) extends GroupEvent
case class UserListForGroupUpdatedEvent(users: Seq[UserIdAndBriefInfo]) extends GroupEvent
case class UserAcceptedIntoGroupEvent(userId: UserId, groupDetails: GroupDetails) extends GroupEvent
case class UserLeftGroupEvent(userId: UserId) extends GroupEvent