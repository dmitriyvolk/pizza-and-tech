package net.dmitriyvolk.pizzaandtech.domain.comment

import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import org.joda.time.DateTime

case class CommentDetails(userId: UserId, userDisplayName: String, text: String, timestamp: DateTime)
