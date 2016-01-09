package net.dmitriyvolk.pizzaandtech.domain.authentication

import net.chrisrichardson.eventstore.EventStore
import net.dmitriyvolk.pizzaandtech.domain.user.commands.RegisterNewUserCommand
import net.dmitriyvolk.pizzaandtech.domain.user.{UserId, User, UserBriefInfo, UserIdAndBriefInfo}
import net.chrisrichardson.eventstore.util.ServiceUtil._

trait AuthenticationService {
  def getCurrentUser: UserIdAndBriefInfo

}

class FakeAuthenticationService(implicit eventStore: EventStore) extends AuthenticationService {
  import scala.concurrent.ExecutionContext.Implicits.global

  @volatile var currentUser: UserIdAndBriefInfo = null //kill me please

  val userInfo = UserBriefInfo("scott", "Scott Tiger")
  (newEntity[User] <== RegisterNewUserCommand(userInfo)) onSuccess { case x => currentUser = UserIdAndBriefInfo(UserId(x.entityId), userInfo) }

  override def getCurrentUser = currentUser

}

