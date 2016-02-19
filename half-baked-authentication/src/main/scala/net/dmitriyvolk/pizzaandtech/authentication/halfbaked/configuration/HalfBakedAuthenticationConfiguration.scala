package net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration

import javax.servlet._
import javax.servlet.http.HttpServletRequest

import net.dmitriyvolk.pizzaandtech.domain.common.UserIdHolder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.{Bean, ComponentScan, Configuration}
import org.springframework.stereotype.Component

/**
  * Created by xbo on 2/17/16.
  */
@Configuration
@ComponentScan(basePackages = Array("net.dmitriyvolk.pizzaandtech.authentication"))
class HalfBakedAuthenticationConfiguration {

  @Bean
  def userInfoResolver = new InMemoryUserMap
}

@Component
class CustomUserIdFilter @Autowired() (userIdHolder: UserIdHolder) extends Filter {


  override def init(filterConfig: FilterConfig): Unit = {}

  import CustomUserIdFilter.USERID_HEADER_NAME
  def extract(request: ServletRequest): String = request.asInstanceOf[HttpServletRequest].getHeader(USERID_HEADER_NAME)

  override def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {
    userIdHolder.set(extract(request))
    chain.doFilter(request, response)
  }

  override def destroy(): Unit = {}

}

object CustomUserIdFilter {

  val USERID_HEADER_NAME: String = "X-PIZZAANDTECH-USERID"

}