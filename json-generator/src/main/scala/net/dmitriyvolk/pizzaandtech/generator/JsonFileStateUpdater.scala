package net.dmitriyvolk.pizzaandtech.generator

import java.io.{File, PrintWriter}

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{JsonSerializer, SerializerProvider}
import com.fasterxml.jackson.datatype.joda.JodaModule
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupDetails, GroupId}
import net.dmitriyvolk.pizzaandtech.domain.meeting.{MeetingDetails, MeetingId, MeetingIdAndDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserBriefInfo, UserId}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.annotation.meta.{beanGetter, beanSetter}

@Component
class JsonFileStateUpdater @Autowired() (
//                                          @BeanProperty @Value("{jsonRoot}") var jsonRoot: String,
                                          jsonWriter: JsonWriter) extends StateUpdater {

  val jsonRoot = new File("/home/xbo/projects/pizza-and-tech/ui/monolith/data")

  implicit def entityIdToString[T <: EntityIdWrapper](entityId: T): String = entityId.entityId.id

  override def addMemberToGroup(groupId: GroupId, memberId: UserId): Unit = {

  }

  override def createOrUpdateGroup(groupId: GroupId, groupDetails: GroupDetails): Unit = {
    val groupFolder = makeOrGetGroupFolder(groupId)
    val fullInfo = new File(groupFolder, "group.json")
    jsonWriter.writeToFile(fullInfo, groupDetails)
  }


  def makeOrGetGroupFolder(groupId: String) = makeOrGetEntityFolder(s"groups/$groupId")

  def makeOrGetMeetingFolder(meetingId: String) = makeOrGetEntityFolder(s"meetings/$meetingId")

  def makeOrGetEntityFolder(folderName: String) = {
    val folder = new File(jsonRoot, folderName)
    if (!folder.exists()) {
      folder.mkdirs()
    }
    folder
  }

  override def addGroupToMembersList(userId: UserId, groupId: GroupId): Unit = ???

  override def updateMeetingListForGroup(groupId: GroupId, meetingList: Seq[MeetingIdAndDetails]): Unit = {
    val groupFolder = makeOrGetGroupFolder(groupId)
    val meetingsJson = new File(groupFolder, "meetings.json")
    jsonWriter.writeToFile(meetingsJson, meetingList)
  }

  override def updateMembersListForGroup(groupId: GroupId, members: Seq[UserBriefInfo]): Unit = {
    val groupFolder = makeOrGetGroupFolder(groupId)
    val membersJson = new File(groupFolder, "members.json")
    jsonWriter.writeToFile(membersJson, members)
  }

  override def createOrUpdateMeeting(meetingId: MeetingId, groupId: GroupId, meetingDetails: MeetingDetails): Unit = {
    val meetingFolder = makeOrGetMeetingFolder(meetingId)
    val meetingJson = new File(meetingFolder, "meeting.json")
    jsonWriter.writeToFile(meetingJson, MeetingDetailsAndGroupId(groupId, meetingDetails))
  }
}

object JsonFileStateUpdater {
  type Value = org.springframework.beans.factory.annotation.Value @beanSetter @beanGetter

}
trait JsonWriter {
  def writeToFile(file: File, dataObject: Any)
}

import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper

@Component
class JacksonJsonWriter extends JsonWriter {

  val mapper = new ObjectMapper() with ScalaObjectMapper
  mapper.registerModule(DefaultScalaModule)
  mapper.registerModule(new JodaModule)
  mapper.registerModule(PizzaAndTechModule)
  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

  def toJson(dataObject: Any) = mapper.writeValueAsString(dataObject)

  override def writeToFile(file: File, dataObject: Any): Unit = {
    val w = new PrintWriter(file)
    w.write(toJson(dataObject))
    w.close()
  }
}

case class MeetingDetailsAndGroupId(groupId: GroupId, meetingDetails: MeetingDetails)

object PizzaAndTechModule extends SimpleModule {

  addSerializer(classOf[MeetingId], new EntityIdWrapperSerializer[MeetingId])
  addSerializer(classOf[GroupId], new EntityIdWrapperSerializer[GroupId])
  addSerializer(classOf[UserId], new EntityIdWrapperSerializer[UserId])

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

  class EntityIdWrapperSerializer[T <: EntityIdWrapper] extends JsonSerializer[T] {
    override def serialize(value: T, gen: JsonGenerator, serializers: SerializerProvider): Unit = gen.writeObject(value.entityId.id)
  }
}