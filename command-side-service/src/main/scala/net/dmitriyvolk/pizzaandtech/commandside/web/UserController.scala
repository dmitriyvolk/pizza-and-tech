package net.dmitriyvolk.pizzaandtech.commandside.web

import net.dmitriyvolk.pizzaandtech.domain.user.{UserBriefInfo, UserService}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestBody, RequestMethod, RequestMapping, RestController}
import WebUtil._
import scala.concurrent.ExecutionContext.Implicits.global

@RestController
@RequestMapping(Array("/users"))
class UserController @Autowired() (userService: UserService) {

  @RequestMapping(method=Array(RequestMethod.POST))
  def registerNewUser(@RequestBody request: RegisterUserRequest) = {
    toDeferredResult {
      userService.registerUser(UserBriefInfo(request.username, request.name))
        .map { registeredUserId =>
          RegisterUserResponse(registeredUserId.entityId.id)
        }
    }
  }
}

case class RegisterUserRequest(username: String, name: String)
case class RegisterUserResponse(id: String)