package net.dmitriyvolk.pizzaandtech.generator

import java.io.File

import net.dmitriyvolk.pizzaandtech.domain.group.{GroupId, GroupDetails}
import net.dmitriyvolk.pizzaandtech.domain.meeting.MeetingDetails
import net.dmitriyvolk.pizzaandtech.domain.user.{UserBriefInfo, UserId}

class JsonFileStateUpdater(contentRoot: File, jsonWriter: JsonWriter) extends StateUpdater {

  implicit def groupIdToString(groupId: GroupId): String = groupId.entityId.id

  override def addMemberToGroup(groupId: GroupId, memberId: UserId): Unit = {

  }

  override def createOrUpdateGroup(groupId: GroupId, groupDetails: GroupDetails): Unit = {
    val groupFolder = makeOrGetGroupFolder(groupId)
    val fullInfo = new File(groupFolder, "group.json")
    jsonWriter.writeToFile(fullInfo, groupDetails)
  }


  def makeOrGetGroupFolder(groupId: String) = {
    val folder = new File(contentRoot, s"groups/$groupId")
    if (!folder.exists()) {
      folder.mkdirs()
    }
    folder
  }

  override def addGroupToMembersList(userId: UserId, groupId: GroupId): Unit = ???

  override def updateMeetingListForGroup(groupId: GroupId, meetingList: Seq[MeetingDetails]): Unit = {
    val groupFolder = makeOrGetGroupFolder(groupId)
    val meetingsJson = new File(groupFolder, "meetings.json")
    jsonWriter.writeToFile(meetingsJson, meetingList)
  }

  override def updateMembersListForGroup(groupId: GroupId, members: Seq[UserBriefInfo]): Unit = {
    val groupFolder = makeOrGetGroupFolder(groupId)
    val membersJson = new File(groupFolder, "members.json")
    jsonWriter.writeToFile(membersJson, members)
  }
}

trait JsonWriter {
  def writeToFile(file: File, dataObject: Any)
}