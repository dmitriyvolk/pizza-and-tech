package net.dmitriyvolk.pizzaandtech.commandside.web

import net.dmitriyvolk.pizzaandtech.commandside.web.WebImplicits._
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupDetails, GroupId, GroupService}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserService, UserId}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation._
import scala.concurrent.ExecutionContext.Implicits.global

@RestController
@RequestMapping(Array("/groups"))
class GroupContoller @Autowired() (private val groupService: GroupService) {

  @RequestMapping(method=Array(POST))
  def createGroup(@RequestBody createGroupRequest: CreateGroupRequest) = {
    val future = groupService.createGroup(createGroupRequest.makeGroupDetails)
    WebUtil.toDeferredResult(future map (createdGroup => CreateGroupResponse(createdGroup.entityId.id)))
  }

  @RequestMapping(value=Array("/{groupId}"), method = Array(PUT))
  def updateGroupDetails(@PathVariable groupId:String, @RequestBody groupDetails:GroupDetails) = {
    val future = groupService.updateGroupInfo(GroupId(groupId), groupDetails)
    WebUtil.toDeferredResult(future map (updatedGroup => UpdateGroupResponse(updatedGroup.entityId.id)))
  }

}

@RestController
@RequestMapping(Array("/groups/{groupId}/comments"))
class GroupCommentsController @Autowired() (private val groupService: GroupService) {

  @RequestMapping(method=Array(POST))
  def addComment(@PathVariable groupId: String, @RequestBody addCommentRequest: AddCommentRequest) = {
    val f = groupService.commentOnGroup(GroupId(groupId), addCommentRequest.text)
    WebUtil.toDeferredResult(f map(group => UpdateGroupResponse(group.entityId.id)))
  }
}

@RestController
@RequestMapping(Array("/groups/{groupId}/members"))
class GroupMembershipController @Autowired() (private val userService: UserService) {

  @RequestMapping(method=Array(POST))
  def joinGroup(@PathVariable groupId: String, @RequestParam("userId") userId: String) = {
    val f = userService.joinGroup(UserId(userId), GroupId(groupId))
    WebUtil.toDeferredResult(f map(group => UpdateGroupResponse(group.entityId.id)))
  }

  @RequestMapping(value=Array("/{memberId}"), method=Array(DELETE))
  def leaveGroup(@PathVariable("groupId") groupId: String, @PathVariable("userId") memberId: String) = {
    val f = userService.leaveGroup(UserId(memberId), GroupId(groupId))
    WebUtil.toDeferredResult(f map(group => UpdateGroupResponse(group.entityId.id)))
  }

}

case class CreateGroupRequest(name: String, description: String) {
  def makeGroupDetails = GroupDetails(name, description)
}
case class CreateGroupResponse(groupId: String)
case class UpdateGroupResponse(groupId: String)
case class AddCommentRequest(text: String)

