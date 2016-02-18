package net.dmitriyvolk.pizzaandtech.authentication.halfbaked.web


import java.security.Principal

import net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration.CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.web.bind.annotation.{RequestMapping, RestController}

@RestController
class CurrentUserController @Autowired() (userDetailsService: UserDetailsService) {

  @RequestMapping(Array("/currentuser"))
  def currentUser(principal: Principal): CustomUserDetails = userDetailsService.loadUserByUsername(principal.getName).asInstanceOf[CustomUserDetails]

}
