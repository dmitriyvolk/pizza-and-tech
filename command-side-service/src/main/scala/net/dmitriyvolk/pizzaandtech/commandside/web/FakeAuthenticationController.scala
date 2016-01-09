package net.dmitriyvolk.pizzaandtech.commandside.web

import net.dmitriyvolk.pizzaandtech.domain.authentication.AuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
class FakeAuthenticationController @Autowired() (authenticationService: AuthenticationService) {

  @RequestMapping(Array("/currentuser"))
  def currentUser = authenticationService.getCurrentUser
}
