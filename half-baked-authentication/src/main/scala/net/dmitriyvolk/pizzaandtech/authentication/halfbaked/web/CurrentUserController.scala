package net.dmitriyvolk.pizzaandtech.authentication.halfbaked.web


import net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration.InMemoryUserMap
import net.dmitriyvolk.pizzaandtech.domain.user.UserIdAndBriefInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMethod, RequestMapping, RestController}


@RestController
@RequestMapping(Array("/users"))
class UsersController @Autowired() (userDetailsService: InMemoryUserMap) {

  @RequestMapping(method = Array(RequestMethod.GET))
  def allUsers(): Seq[Map[String, String]] = {
    userDetailsService.getAllUsers.map { u =>
      Map(("id", u.userId.entityId.id), ("name", u.briefInfo.fullName))
    }
  }

}
