package net.dmitriyvolk.pizzaandtech.domain.meeting.events

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.user.UserIdAndBriefInfo

case class UserAndComment(user: UserIdAndBriefInfo, comment: String)
case class Rsvps(yes: Seq[UserAndComment], no: Seq[UserAndComment], maybe: Seq[UserAndComment]) {

  def respondYes(user: UserIdAndBriefInfo, comment: String): Rsvps = copy(yes = yes :+ UserAndComment(user, comment))
  def respondNo(user: UserIdAndBriefInfo, comment: String): Rsvps = copy(no = no :+ UserAndComment(user, comment))
  def respondMaybe(user: UserIdAndBriefInfo, comment: String): Rsvps = copy(maybe = maybe :+ UserAndComment(user, comment))

  def containsRsvpOf(userId: EntityId): Boolean = {
    def containsUserId(seq: Seq[UserAndComment], userId: EntityId) = seq.exists(_.user.userId.entityId == userId)
    List(yes, no, maybe).foldLeft(false)((previous, s) => previous || containsUserId(s, userId))
  }

}
object Rsvps {
  def apply() = {
    new Rsvps(Seq(), Seq(), Seq())
  }
}
