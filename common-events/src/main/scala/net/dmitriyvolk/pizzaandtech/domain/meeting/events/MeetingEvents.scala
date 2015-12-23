package net.dmitriyvolk.pizzaandtech.domain.meeting.events

import net.chrisrichardson.eventstore.{Event, EntityId}
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingDetails
import net.dmitriyvolk.pizzaandtech.domain.user.UserId

sealed trait MeetingEvents extends Event
case class MeetingScheduledEvent(groupId: GroupId, meetingDetails: MeetingDetails) extends MeetingEvents
case class MeetingDetailsUpdatedEvent(groupId: GroupId, meetingDetails: MeetingDetails) extends MeetingEvents
case class MemberRsvpedEvent(rsvp: RsvpDetails, memberId: UserId) extends MeetingEvents
case class MemberChangedRsvpEvent(rsvpDetails: RsvpDetails, memberId: EntityId) extends MeetingEvents
case class CommentAddedToMeetingEvent(comment: CommentDetails) extends MeetingEvents
case class MemberIsComingToMeeting(userId: UserId, comment: String) extends MeetingEvents
case class MemberRefusedToComeToMeeting(userId: UserId, comment: String) extends MeetingEvents
case class MemberWillDecideLaterWhetherToComeToMeeting(userId: UserId, comment: String) extends MeetingEvents
