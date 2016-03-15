package net.dmitriyvolk.pizzaandtech.commandside.web

import net.dmitriyvolk.pizzaandtech.domain.common.{UserIdHolder, UserInfoResolver}
import net.dmitriyvolk.pizzaandtech.domain.user.UserIdAndBriefInfo
import org.springframework.web.context.request.async.DeferredResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}

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

trait ResolvingCurrentUser {
  val userInfoResolver: UserInfoResolver
  val userIdHolder: UserIdHolder

  def getCurrentUser: Option[UserIdAndBriefInfo] = {
    userInfoResolver.resolveUserId(userIdHolder.get)
  }

  def withCurrentUser[T](f: UserIdAndBriefInfo => Future[T]) = getCurrentUser match {
    case Some(userInfo) => {
      println(s"Will call f with $userInfo")
      f(userInfo)
    }
    case None => Promise[T]().failure(new RuntimeException("UserNotFound")).future
  }

}
