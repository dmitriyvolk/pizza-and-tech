package net.dmitriyvolk.pizzaandtech.commandside.web

import net.chrisrichardson.eventstore.EntityId
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
}

