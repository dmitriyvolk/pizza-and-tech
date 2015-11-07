package net.dmitriyvolk.pizzandtech.domain.event

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.domain.meeting.{UserAndComment, Rsvps}
import net.dmitriyvolk.pizzaandtech.domain.meeting.events._
import org.scalatest.{PropSpec, Matchers}
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.prop.TableDrivenPropertyChecks

class RsvpSpec extends PropSpec with TableDrivenPropertyChecks with Matchers{


  val user1 = EntityId.make("1")
  val comment1 = "Comment One"

  import CustomMatchers._

  val possibleRsvps = Table("RSVP", Yes, No, Maybe)

  property("should be in right bucket") {

    forAll (possibleRsvps) { expectedRsvp: YesNoMaybe =>
      val rsvps = expectedRsvp match {
        case Yes => Rsvps().respondYes(user1, comment1)
        case No => Rsvps().respondNo(user1, comment1)
        case Maybe => Rsvps().respondMaybe(user1, comment1)
      }

      rsvps should haveRsvpInExpectedBucket(expectedRsvp, user1)
    }


  }
  property("should not be in the wrong bucket") {

    forAll (possibleRsvps) { expectedRsvp =>
      val rsvps = expectedRsvp match {
        case Yes => Rsvps().respondYes(user1, comment1)
        case No => Rsvps().respondNo(user1, comment1)
        case Maybe => Rsvps().respondMaybe(user1, comment1)
      }

      rsvps should notHaveRsvpInUnexpectedBucket(expectedRsvp, user1)

    }
  }


  trait CustomMatchers {


    trait BucketMatchers extends Matcher[Rsvps] {

      def containsUserId(bucket: Seq[UserAndComment], userId: EntityId) = bucket.map(_.userId).contains(userId)
    }

    class RsvpExistsInExpectedBucketMatcher(expectedRsvp: YesNoMaybe, userId: EntityId) extends BucketMatchers {
      override def apply(left: Rsvps): MatchResult = {
        MatchResult(
          containsUserId(expectedBucket(left), userId),
          s"The $expectedRsvp bucket does not contain userId $userId",
          s"The $expectedRsvp bucket contains userId $userId"
        )
      }

      def expectedBucket(rsvps: Rsvps) = expectedRsvp match {
        case Yes => rsvps.yes
        case No => rsvps.no
        case Maybe => rsvps.maybe
      }
    }

    class RsvpDoesntExistInWrongBuckets(expectedRsvp: YesNoMaybe, userId: EntityId) extends BucketMatchers {
      override def apply(left: Rsvps) = {
        MatchResult(
          !unexpectedBuckets(left).foldLeft(false)((soFar, bucket) => soFar || containsUserId(bucket, userId)),
          s"UserId $userId appeared in unexpected bucket in $left",
          s"UserId $userId didn't appear in unexpected bucket"
        )
      }

      def unexpectedBuckets(rsvps: Rsvps) = expectedRsvp match {
        case Yes => List(rsvps.no, rsvps.maybe)
        case No => List(rsvps.yes, rsvps.maybe)
        case Maybe => List(rsvps.yes, rsvps.no)
      }
    }


    def haveRsvpInExpectedBucket(expectedRsvp: YesNoMaybe, userId: EntityId) = new RsvpExistsInExpectedBucketMatcher(expectedRsvp, userId)
    def notHaveRsvpInUnexpectedBucket(expectedRsvp: YesNoMaybe, userId: EntityId) = new RsvpDoesntExistInWrongBuckets(expectedRsvp, userId)

  }

  object CustomMatchers extends CustomMatchers
}
