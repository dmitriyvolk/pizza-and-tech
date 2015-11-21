package net.dmitriyvolk.pizzaandtech.generator

import java.io.File

import net.dmitriyvolk.pizzaandtech.domain.group.{GroupId, GroupDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.UserId

class JsonFileStateUpdater(contentRoot: File, jsonWriter: JsonWriter) extends StateUpdater {

  override def addMemberToGroup(groupId: GroupId, memberId: UserId): Unit = {

  }

  override def createOrUpdateGroup(groupId: GroupId, groupDetails: GroupDetails): Unit = {
    val groupFolder = makeOrGetGroupFolder(groupId.entityId.id)
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
}

trait JsonWriter {
  def writeToFile(file: File, dataObject: Any)
}