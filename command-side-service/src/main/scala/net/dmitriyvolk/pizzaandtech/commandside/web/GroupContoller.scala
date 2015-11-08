package net.dmitriyvolk.pizzaandtech.commandside.web

import net.dmitriyvolk.pizzaandtech.commandside.web.WebImplicits._
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupDetails, GroupId, GroupService}
import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMethod._
import org.springframework.web.bind.annotation._
import scala.concurrent.ExecutionContext.Implicits.global

@RestController
@RequestMapping(Array("/groups"))
class GroupContoller @Autowired() (private val groupService: GroupService) {

  @RequestMapping(method=Array(POST))
  def createGroup(@RequestBody groupDetails: GroupDetails) = {
    val future = groupService.createGroup(groupDetails)
    WebUtil.toDeferredResult(future map (createdGroup => CreateGroupResponse(createdGroup.entityId.id)))
  }

  @RequestMapping(value=Array("/{groupId}"), method = Array(PUT))
  def updateGroupDetails(@PathVariable groupId:String, @RequestBody groupDetails:GroupDetails) = {
    val future = groupService.updateGroupInfo(GroupId(groupId), groupDetails)
    WebUtil.toDeferredResult(future map (updatedGroup => UpdateGroupResponse(updatedGroup.entityId.id)))
  }

}

@RestController
@RequestMapping(Array("/{groupId}/comment"))
class GroupCommentsController @Autowired() (private val groupService: GroupService) {

  @RequestMapping(method=Array(POST))
  def addComment(@PathVariable groupId: String, @RequestBody commentDetails: CommentDetails) = {
    val f = groupService.commentOnGroup(GroupId(groupId), commentDetails)
    WebUtil.toDeferredResult(f map(group => UpdateGroupResponse(group.entityId.id)))
  }
}

@RestController
@RequestMapping(Array("/{groupId}/members"))
class GroupMembershipController @Autowired() (private val groupService: GroupService) {

  @RequestMapping(method=Array(POST))
  def joinGroup(@PathVariable groupId: String, @RequestParam("userId") userId: String) = {
    val f = groupService.joinGroup(GroupId(groupId), UserId(userId))
    WebUtil.toDeferredResult(f map(group => UpdateGroupResponse(group.entityId.id)))
  }

  @RequestMapping(value=Array("/{memberId}"), method=Array(DELETE))
  def leaveGroup(@PathVariable("groupId") groupId: String, @PathVariable("userId") memberId: String) = {
    val f = groupService.leaveGroup(GroupId(groupId), UserId(memberId))
    WebUtil.toDeferredResult(f map(group => UpdateGroupResponse(group.entityId.id)))
  }

}

case class CreateGroupResponse(val groupId: String)
case class UpdateGroupResponse(val groupId: String)
