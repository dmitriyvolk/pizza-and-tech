package net.dmitriyvolk.pizzaandtech.domain.user.events

import net.chrisrichardson.eventstore.Event
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupIdAndDetails, GroupId, GroupDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.UserBriefInfo

sealed trait UserEvent extends Event
case class JoinedGroupEvent(groupDetails: GroupDetails) extends UserEvent
case class LeftGroupEvent(groupId: GroupId) extends UserEvent
case class GroupListOfUserUpdatedEvent(groups: Seq[GroupIdAndDetails]) extends UserEvent
case class NewUserRegisteredEvent(briefInfo: UserBriefInfo) extends UserEvent
case class UserAppliedToJoinGroupEvent(groupId: GroupId, briefInfo: UserBriefInfo) extends UserEvent
case class UserWantsToLeaveGroupEvent(groupId: GroupId) extends UserEvent

