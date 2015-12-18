package net.dmitriyvolk.pizzaandtech.domain.group

case class GroupDetails (name: String, description: String)
case class GroupIdAndDetails(groupId: GroupId, groupDetails: GroupDetails)
