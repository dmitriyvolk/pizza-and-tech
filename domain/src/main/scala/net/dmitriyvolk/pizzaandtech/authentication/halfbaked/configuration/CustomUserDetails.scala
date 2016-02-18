package net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration

import java.util

import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
  * Created by xbo on 2/11/16.
  */
case class CustomUserDetails(username: String, password: String, userId: UserId) extends UserDetails {
  override def isEnabled: Boolean = true

  override def getPassword: String = password

  override def isAccountNonExpired: Boolean = true

  override def getAuthorities: util.Collection[_ <: GrantedAuthority] = java.util.Collections.singletonList(new SimpleGrantedAuthority("USER"))

  override def isCredentialsNonExpired: Boolean = true

  override def isAccountNonLocked: Boolean = true

  override def getUsername: String = username
}
