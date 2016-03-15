package net.dmitriyvolk.pizzaandtech.domain.common

import com.fasterxml.jackson.core.{JsonParser, JsonGenerator}
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.{DeserializationContext, JsonDeserializer, JsonSerializer, SerializerProvider}
import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.EntityIdWrapper
import net.dmitriyvolk.pizzaandtech.domain.comment.CommentDetails
import net.dmitriyvolk.pizzaandtech.domain.group.{GroupId, GroupIdAndDetails}
import net.dmitriyvolk.pizzaandtech.domain.meeting.events.{Yes, YesNoMaybe}
import net.dmitriyvolk.pizzaandtech.domain.meeting.{MeetingId, MeetingIdAndDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.{UserId, UserIdAndBriefInfo}

object PizzaAndTechCommonModule extends SimpleModule {

  addSerializer(classOf[MeetingId], new EntityIdWrapperSerializer[MeetingId])
  addDeserializer(classOf[MeetingId], new JsonDeserializer[MeetingId] {
    override def deserialize(jp: JsonParser, ctxt: DeserializationContext): MeetingId =
      MeetingId(EntityId(jp.getText))
  })
  addSerializer(classOf[GroupId], new EntityIdWrapperSerializer[GroupId])
  addDeserializer(classOf[GroupId], new JsonDeserializer[GroupId] {
    override def deserialize(jp: JsonParser, ctxt: DeserializationContext): GroupId =
      GroupId(EntityId(jp.getText))
  })
  addSerializer(classOf[UserId], new EntityIdWrapperSerializer[UserId])
  addDeserializer(classOf[UserId], new JsonDeserializer[UserId] {
    override def deserialize(jp: JsonParser, ctxt: DeserializationContext): UserId =
      UserId(EntityId(jp.getText))
  })

  addSerializer(new JsonSerializer[GroupIdAndDetails] {
    override def serialize(value: GroupIdAndDetails, gen: JsonGenerator, serializers: SerializerProvider): Unit = {
      println(s"Serializing: $value")
      gen.writeObject(Map(
        ("id", value.groupId),
        ("name", value.groupDetails.name)
      ))
    }
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

  addSerializer(new JsonSerializer[CommentDetails] {
    override def serialize(value: CommentDetails, gen: JsonGenerator, serializers: SerializerProvider): Unit =
      gen.writeObject(Map(
        ("author", Map(
          ("id", value.userId),
          ("name", value.userDisplayName)
        )),
        ("text", value.text),
        ("timestamp", value.timestamp)
      ))

    override def handledType(): Class[CommentDetails] = classOf[CommentDetails]
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

  addDeserializer(classOf[YesNoMaybe], new JsonDeserializer[YesNoMaybe] {
    override def deserialize(jp: JsonParser, ctxt: DeserializationContext): YesNoMaybe = {
      println(s"GOT >>>${jp.getText}<<<")
      Yes
    }
  })

}
class EntityIdWrapperSerializer[T <: EntityIdWrapper] extends JsonSerializer[T] {
  override def serialize(value: T, gen: JsonGenerator, serializers: SerializerProvider): Unit = gen.writeObject(value.entityId.id)
}
