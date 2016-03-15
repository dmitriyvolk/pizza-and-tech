package net.dmitriyvolk.pizzaandtech.test.endtoend

import java.net.URI

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupDetails, GroupMother}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserId, UserMother}
import org.joda.time.DateTime
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import org.springframework.http.{HttpMethod, HttpStatus}

class GroupEndToEndSpec extends FeatureSpec with Matchers with GivenWhenThen with TestBase {



  def updateGroup(groupId: String, details: GroupDetails, ownerId: String) = {
    val requestEntity = makeCreateGroupRequestEntity(details.name, details.description, ownerId)
    restTemplate.exchange(new URI(s"$groupsUrl/$groupId"), HttpMethod.PUT, requestEntity, classOf[Map[String, String]])
  }

  feature("Groups can be created and renamed") {

    scenario("Authorized user creates a group") {
      Given("an authorized user")
      val groupOwnerId = createdUserId

      When("new group call is issued")
      val createdGroup = createGroup(GroupMother.makeGroupDetails("group1"), groupOwnerId)

      Then("group should be created")
      val createdGroupId = createdGroup("groupId")
      createdGroupId should not be null

    }

    scenario("Authorized user renames a group") {

      Given("an existing group and an authorized user")
      val groupOwnerId = createdUserId
      val createdGroupId = createGroup(GroupMother.makeGroupDetails("group2"), groupOwnerId)("groupId")

      When("rename group call is issued")
      val updatedGroup = updateGroup(createdGroupId, GroupMother.makeGroupDetails("group2updated"), groupOwnerId)

      Then("call succeeds")
      updatedGroup.getStatusCode should be(HttpStatus.OK)
    }

    scenario("User comments on a group") {

      Given("an existing group")
      val groupOwnerId = createdUserId
      val groupId = createGroup(GroupMother.makeGroupDetails("group2"), groupOwnerId)("groupId")

      Given("an existing user other than the group's owner")
      val commenter = createUser(UserMother.userInfo("two"))

      When("user comments on a group")
      val result = commentOnGroup(groupId, CommentDetails(UserId(EntityId(commenter)), "FIXME!", "My comment", new DateTime()))

      Then("call succeeds")
      result("groupId") should not be null
    }


  }


}

