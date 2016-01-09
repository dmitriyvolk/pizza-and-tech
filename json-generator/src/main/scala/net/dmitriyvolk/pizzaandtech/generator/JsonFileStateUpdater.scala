package net.dmitriyvolk.pizzaandtech.generator

import java.io.{File, PrintWriter}

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import com.fasterxml.jackson.datatype.joda.JodaModule
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupIdAndDetails, GroupDetails, GroupId}
import net.dmitriyvolk.pizzaandtech.domain.meeting.{MeetingDetails, MeetingId, MeetingIdAndDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserIdAndBriefInfo, UserBriefInfo, UserId}
import net.dmitriyvolk.pizzaandtech.generator.DataWriter.DataPath
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.annotation.meta.{beanGetter, beanSetter}

@Component
class JsonFileStateUpdater @Autowired() (
                                        serializer: PizzaAndTechJsonSerializer,
                                        dataWriter: DataWriter
                                        ) extends StateUpdater {

  implicit def entityIdToString[T <: EntityIdWrapper](entityId: T): String = entityId.entityId.id

  override def createOrUpdateGroup(groupId: GroupId, groupDetails: GroupDetails): Unit = {
    write(groupFolder(groupId), "group.json", groupDetails)
  }


  def groupFolder(groupId: String) = DataPath(s"groups/$groupId")

  def meetingFolder(meetingId: String) = DataPath(s"meetings/$meetingId")

  def userFolder(userId: String) = DataPath(s"users/$userId")

  def write(folder: DataPath, filename: String, data: AnyRef) =
    dataWriter.writeJsonData(folder, filename, serializer.toJson(data))

  override def updateMeetingListForGroup(groupId: GroupId, meetingList: Seq[MeetingIdAndDetails]): Unit = {
    write(groupFolder(groupId), "meetings.json", meetingList)
  }

  override def updateMembersListForGroup(groupId: GroupId, members: Seq[UserBriefInfo]): Unit = {
    write(groupFolder(groupId), "members.json", members)
  }

  override def createOrUpdateMeeting(meetingId: MeetingId, groupId: GroupId, meetingDetails: MeetingDetails): Unit = {
    write(meetingFolder(meetingId), "meeting.json", MeetingDetailsAndGroupId(groupId, meetingDetails))
  }

  override def updateMemberListForGroup(groupId: GroupId, members: Seq[UserIdAndBriefInfo]): Unit = {
    write(groupFolder(groupId), "members.json", members)
  }

  override def updateGroupListForUser(userId: UserId, groups: Seq[GroupIdAndDetails]): Unit =
    write(userFolder(userId), "memberof.json", groups)

  override def createOrUpdateUser(userId: UserId, briefInfo: UserBriefInfo): Unit =
    write(userFolder(userId), "user.json", UserIdAndBriefInfo(userId, briefInfo))
}

object JsonFileStateUpdater {
  type Value = org.springframework.beans.factory.annotation.Value @beanSetter @beanGetter
}


import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

case class MeetingDetailsAndGroupId(groupId: GroupId, meetingDetails: MeetingDetails)

object PizzaAndTechModule extends SimpleModule {

  addSerializer(classOf[MeetingId], new EntityIdWrapperSerializer[MeetingId])
  addSerializer(classOf[GroupId], new EntityIdWrapperSerializer[GroupId])
  addSerializer(classOf[UserId], new EntityIdWrapperSerializer[UserId])

  addSerializer(new JsonSerializer[GroupIdAndDetails] {
    override def serialize(value: GroupIdAndDetails, gen: JsonGenerator, serializers: SerializerProvider): Unit =
      gen.writeObject(Map(
        ("id", value.groupId),
        ("name", value.groupDetails.name)
      ))

    override def handledType(): Class[GroupIdAndDetails] = classOf[GroupIdAndDetails]
  })

  addSerializer(new JsonSerializer[MeetingIdAndDetails]() {
    override def serialize(value: MeetingIdAndDetails, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
      gen.writeObject(Map(
        ("id", value.id),
        ("name", value.meetingDetails.name),
        ("description", value.meetingDetails.description),
        ("startDate", value.meetingDetails.startDate)
      ))
    }

    override def handledType(): Class[MeetingIdAndDetails] = classOf[MeetingIdAndDetails]
  })

  addSerializer(new JsonSerializer[MeetingDetailsAndGroupId] {

    override def handledType(): Class[MeetingDetailsAndGroupId] = classOf[MeetingDetailsAndGroupId]

    override def serialize(value: MeetingDetailsAndGroupId, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
      gen.writeObject(Map(
        ("groupId", value.groupId),
        ("name", value.meetingDetails.name),
        ("description", value.meetingDetails.description),
        ("startDate", value.meetingDetails.startDate)
      ))
    }
  })

  addSerializer(new JsonSerializer[UserIdAndBriefInfo] {
    override def serialize(value: UserIdAndBriefInfo, gen: JsonGenerator, serializers: SerializerProvider): Unit =
      gen.writeObject(Map(
        ("id", value.userId),
        ("name", value.briefInfo.fullName),
        ("username", value.briefInfo.username)
      ))

    override def handledType(): Class[UserIdAndBriefInfo] = classOf[UserIdAndBriefInfo]
  })

  class EntityIdWrapperSerializer[T <: EntityIdWrapper] extends JsonSerializer[T] {
    override def serialize(value: T, gen: JsonGenerator, serializers: SerializerProvider): Unit = gen.writeObject(value.entityId.id)
  }
}

trait PizzaAndTechJsonSerializer {
  def toJson(value: AnyRef): String
}

@Component
class JacksonJsonSerializer extends PizzaAndTechJsonSerializer {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.registerModule(new JodaModule)
  mapper.registerModule(PizzaAndTechModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def toJson(dataObject: AnyRef) = mapper.writeValueAsString(dataObject)

}






class FilesystemDataWriter(dataRoot: File) extends DataWriter {

  def makeOrGetFolder(folderName: String) = {
    val folder = new File(dataRoot, folderName)
    if (!folder.exists()) {
      folder.mkdirs()
    }
    folder
  }

  override def writeJsonData(folder: DataPath, filename: String, json: String): Unit = {
    val dataFolder = makeOrGetFolder(folder.path)
    val file = new File(dataFolder, filename)
    val w = new PrintWriter(file)
    w.write(json)
    w.close()
  }
}