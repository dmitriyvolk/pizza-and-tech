package net.dmitriyvolk.pizzaandtech.domain.comment

import net.chrisrichardson.eventstore.EntityId
import org.joda.time.DateTime

case class CommentDetails(memberId: EntityId, text: String, timestamp: DateTime)
