package net.dmitriyvolk.pizzaandtech.domain.meeting.events

import net.chrisrichardson.eventstore.{Event, EntityId}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingDetails
import net.dmitriyvolk.pizzaandtech.domain.user.{UserIdAndBriefInfo, UserId}

sealed trait MeetingEvent extends Event
case class MeetingScheduledEvent(groupId: GroupId, meetingDetails: MeetingDetails) extends MeetingEvent
case class MeetingDetailsUpdatedEvent(groupId: GroupId, meetingDetails: MeetingDetails) extends MeetingEvent
case class MemberRsvpedEvent(rsvp: RsvpDetails, memberId: UserId) extends MeetingEvent
case class MemberChangedRsvpEvent(rsvpDetails: RsvpDetails, memberId: EntityId) extends MeetingEvent
case class CommentAddedToMeetingEvent(comment: CommentDetails) extends MeetingEvent
case class CommentListForMeetingUpdatedEvent(commentList: Seq[CommentDetails]) extends MeetingEvent
case class MemberIsComingToMeeting(user: UserIdAndBriefInfo, comment: String) extends MeetingEvent
case class MemberRefusedToComeToMeeting(user: UserIdAndBriefInfo, comment: String) extends MeetingEvent
case class MemberWillDecideLaterWhetherToComeToMeeting(user: UserIdAndBriefInfo, comment: String) extends MeetingEvent
case class RsvpsForMeetingUpdatedEvent(rsvps: Rsvps) extends MeetingEvent