package net.dmitriyvolk.pizzaandtech.generator

import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupIdAndDetails, GroupId, GroupDetails}
import net.dmitriyvolk.pizzaandtech.domain.meeting.{MeetingId, MeetingIdAndDetails, MeetingDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserIdAndBriefInfo, UserBriefInfo, UserId}

trait StateUpdater {
  def updateCommentListForMeeting(meetingId: MeetingId, commentList: Seq[CommentDetails])

  def updateCommentListForGroup(groupId: GroupId, commentList: Seq[CommentDetails])

  def createOrUpdateUser(userId: UserId, briefInfo: UserBriefInfo)

  def createOrUpdateMeeting(meetingId: MeetingId, groupId: GroupId, meetingDetails: MeetingDetails)

  def updateMemberListForGroup(groupId: GroupId, members: Seq[UserIdAndBriefInfo])

  def updateGroupListForUser(userId: UserId, groups: Seq[GroupIdAndDetails])

  def createOrUpdateGroup(groupId: GroupId, groupDetails: GroupDetails)

  def updateMeetingListForGroup(groupId: GroupId, meetingList: Seq[MeetingIdAndDetails])

  def updateMembersListForGroup(groupId: GroupId, members: Seq[UserBriefInfo])
}
