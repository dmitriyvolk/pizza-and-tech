package net.dmitriyvolk.pizzaandtech.commandside.web

import java.security.Principal

import net.chrisrichardson.eventstore.EntityId
import net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration.{AuthImplicits, CustomUserDetails}
import net.dmitriyvolk.pizzaandtech.domain.user.UserIdAndBriefInfo
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.context.request.async.DeferredResult

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * stolen from here: https://github.com/cer/event-sourcing-examples/blob/master/scala-spring/common-web/src/main/scala/net/chrisrichardson/eventstore/examples/bank/web/util/WebUtil.scala
  */
object WebUtil {
  def toDeferredResult[T](future: Future[T]): DeferredResult[T] = {
    val result = new DeferredResult[T]
    future onSuccess {
      case r => result.setResult(r)
    }
    future onFailure {
      case t => result.setErrorResult(t)
    }
    result
  }
}

object WebImplicits {
  implicit def stringToEntityId(id: String): EntityId = EntityId(id)

  implicit def principalToUserIdAndBriefInfo(principal: Principal)(implicit userDetailsService: UserDetailsService): UserIdAndBriefInfo =
    AuthImplicits.toUserIdAndBriefInfo(userDetailsService.loadUserByUsername(principal.getName).asInstanceOf[CustomUserDetails])
}

