package net.dmitriyvolk.pizzaandtech.commandside.web

import java.security.Principal

import net.dmitriyvolk.pizzaandtech.commandside.web.WebImplicits._
import net.dmitriyvolk.pizzaandtech.domain.group.GroupId
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.RsvpDetails
import net.dmitriyvolk.pizzaandtech.domain.meeting.{MeetingDetails, MeetingId, MeetingService}
import net.dmitriyvolk.pizzaandtech.domain.user.UserIdAndBriefInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation._

import scala.concurrent.ExecutionContext.Implicits.global

@RestController
@RequestMapping(Array("/meetings"))
class MeetingController @Autowired() (private val meetingService: MeetingService) {

  @RequestMapping(method=Array(POST))
  def scheduleMeeting(@RequestBody scheduleMeetingRequest: ScheduleMeetingRequest) =
    WebUtil.toDeferredResult(
      meetingService.scheduleMeeting(GroupId(scheduleMeetingRequest.groupId), scheduleMeetingRequest.meetingDetails)
        .map (createdMeeting => ScheduleMeetingResponse(createdMeeting.entityId.id)))

  def updateMeetingDetails(@PathVariable("meetingId") meetingId: String, @RequestBody meetingDetails: MeetingDetails) =
    WebUtil.toDeferredResult(
      meetingService.updateMeetingDetails(MeetingId(meetingId), meetingDetails)
        .map (createdMeeting => UpdateMeetingResponse(createdMeeting.entityId.id)))
}

@RestController
@RequestMapping(Array("/meetings/{meetingId}/comments"))
class MeetingCommentsController @Autowired()(private val meetingService: MeetingService, implicit val userDetailsService: UserDetailsService) {

  @RequestMapping(method=Array(POST))
  def addComment(@PathVariable meetingId: String, @RequestBody newComment: CommentOnMeetingRequest, principal: Principal) = {
    val commenter: UserIdAndBriefInfo = WebImplicits.principalToUserIdAndBriefInfo(principal)
    WebUtil.toDeferredResult(
      meetingService.commentOnMeeting(MeetingId(meetingId), commenter, newComment.text)
        .map(group => UpdateMeetingResponse(group.entityId.id)))
  }

}

@RestController
@RequestMapping(Array("/meetings/{meetingId}/rsvps"))
class MeetingRsvpController @Autowired() (private val meetingService: MeetingService, implicit val userDetailsService: UserDetailsService) {

  @RequestMapping(method=Array(POST))
  def rsvpToMeeting(@PathVariable("meetingId") meetingId: String,
                    @RequestBody rsvpDetails: RsvpDetails, principal: Principal) = {
    val responder: UserIdAndBriefInfo = WebImplicits.principalToUserIdAndBriefInfo(principal)
    WebUtil.toDeferredResult(
      meetingService.rsvpToMeeting(MeetingId(meetingId), responder, rsvpDetails)
        .map(updatedMeeting => UpdateMeetingResponse(updatedMeeting.entityId.id)))
  }
}

case class ScheduleMeetingRequest(groupId: String, meetingDetails: MeetingDetails)

case class ScheduleMeetingResponse(meetingId: String)

case class UpdateMeetingResponse(meetingId: String)

case class CommentOnMeetingRequest(text: String)
