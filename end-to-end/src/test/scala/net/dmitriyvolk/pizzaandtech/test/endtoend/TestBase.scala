package net.dmitriyvolk.pizzaandtech.test.endtoend

import net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration.CustomUserIdFilter
import net.dmitriyvolk.pizzaandtech.commandside.web._
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupDetails
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingDetails
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.{RsvpDetails, YesNoMaybe}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserMother, UserBriefInfo}
import net.dmitriyvolk.pizzaandtech.test.endtoend.configuration.EndToEndTestConfiguration
import org.scalatest.Matchers
import org.scalatest.concurrent.Eventually._
import org.scalatest.matchers.{MatchResult, Matcher}
import org.springframework.boot.SpringApplication
import org.springframework.http.{HttpHeaders, HttpEntity}
import org.springframework.web.client.RestTemplate
import org.scalatest.time.SpanSugar._

/**
  * Created by xbo on 2/19/16.
  */
trait TestBase extends Matchers {

  val ctx = new SpringApplication(classOf[EndToEndTestConfiguration]).run()
  val restTemplate = ctx.getBean(classOf[RestTemplate])
  val port = 8080
  val baseUrl = s"http://localhost:$port"
  val usersUrl = s"$baseUrl/users"
  val groupsUrl = s"$baseUrl/groups"
  def groupCommentsUrl(groupId:String) = s"$groupsUrl/$groupId/comments"
  val meetingsUrl = s"$baseUrl/meetings"
  def meetingUrl(meetingId: String) = s"$meetingsUrl/$meetingId"
  def meetingCommentsUrl(meetingId: String) = s"${meetingUrl(meetingId)}/comments"
  def meetingRsvpUrl(meetingId: String) = s"${meetingUrl(meetingId)}/rsvps"

  import LocalMatchers._

  val createdUserId = createUser(UserMother.userInfo("One"))

  def createUser(userInfo: UserBriefInfo) = {
    val createdUser = restTemplate.postForEntity(usersUrl, RegisterUserRequest(userInfo.username, userInfo.fullName), classOf[RegisterUserResponse])
    eventually(timeout(scaled(3 seconds)), interval(scaled(1 seconds))) {
      createdUser.getBody.id should beARegisteredUserId
    }
    createdUser.getBody.id
  }

  def createGroup(details: GroupDetails, ownerId: String) = {
    val requestEntity = makeCreateGroupRequestEntity(details.name, details.description, ownerId)
    restTemplate.postForObject(groupsUrl, requestEntity, classOf[Map[String, String]])
  }

  def createMeeting(groupId: String, details: MeetingDetails, ownerId: String) = {
    val requestEntity = makeAuthorizedRequestEntity(ScheduleMeetingRequest(groupId, details), ownerId)
    restTemplate.postForObject(meetingsUrl, requestEntity, classOf[Map[String, String]])
  }

  def makeCreateGroupRequestEntity(groupName: String, groupDescription: String, userId: String): HttpEntity[CreateOrUpdateGroupRequest] = {
    val requestBody = CreateOrUpdateGroupRequest(groupName, groupDescription)
    makeAuthorizedRequestEntity(requestBody, userId)
  }

  def makeAuthorizedRequestEntity[T](requestBody: T, userId: String): HttpEntity[T] = {
    val authHeaders = new HttpHeaders()
    authHeaders.add(CustomUserIdFilter.USERID_HEADER_NAME, userId)
    new HttpEntity[T](
      requestBody,
      authHeaders)
  }

  def commentOnGroup(groupId: String, commentDetails: CommentDetails) =
    restTemplate.postForObject(groupCommentsUrl(groupId),
      makeAuthorizedRequestEntity(AddCommentRequest(commentDetails.text), commentDetails.userId.entityId.id), classOf[Map[String, String]])

  def commentOnMeeting(meetingId: String, commentDetails: CommentDetails) =
    restTemplate.postForObject(meetingCommentsUrl(meetingId),
      makeAuthorizedRequestEntity(AddCommentRequest(commentDetails.text), commentDetails.userId.entityId.id), classOf[Map[String, String]])

  def respondToMeeting(meetingId: String, yesNoMaybe: YesNoMaybe, responderId: String) =
    restTemplate.postForObject(meetingRsvpUrl(meetingId), makeAuthorizedRequestEntity(RsvpDetails(yesNoMaybe, s"I responded $yesNoMaybe"), responderId), classOf[Map[String, String]])
  class UserShouldExist extends Matcher[String] {
    val listOfUsers = restTemplate.getForObject(usersUrl, classOf[List[Map[String, String]]])
    println(listOfUsers)
    override def apply(idToCheck: String): MatchResult =
      MatchResult(
        listOfUsers.map(_("id")).contains(idToCheck),
        s"User Id $idToCheck should have appeared in the user list",
        s"User Id $idToCheck should not have appeared in the user list"
      )

  }

  object LocalMatchers {
    def beARegisteredUserId = new UserShouldExist
  }

}
