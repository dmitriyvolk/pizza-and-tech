package net.dmitriyvolk.pizzaandtech.generator.dto

import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.GroupDetails

case class GroupDto(groupDetails: GroupDetails, events: Seq[EventDto], comments:Seq[CommentDetails]) {

}
