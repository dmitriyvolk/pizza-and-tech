package net.dmitriyvolk.pizzaandtech.authentication.halfbaked.configuration

import javax.servlet._
import javax.servlet.http.{Cookie, HttpServletResponse, HttpServletRequest}

import net.dmitriyvolk.pizzaandtech.domain.user.UserId
import org.springframework.context.annotation.{ComponentScan, Bean, Configuration}
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.{EnableWebSecurity, WebSecurityConfigurerAdapter}
import org.springframework.security.core.userdetails.{UserDetails, UserDetailsService}
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.csrf.{CsrfToken, CsrfFilter, HttpSessionCsrfTokenRepository, CsrfTokenRepository}
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.WebUtils

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = Array("net.dmitriyvolk.pizzaandtech.authentication.halfbaked"))
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
  override def configure(http: HttpSecurity): Unit =
    http
      .httpBasic().and()
      .authorizeRequests()
        .antMatchers("/currentuser").permitAll()
        .anyRequest().authenticated()
        .and().asInstanceOf[HttpSecurity]
      .formLogin()
        .loginPage("/login")
        .permitAll()
        .and()
      .logout()
        .permitAll()
      .and()
      .userDetailsService(userDetailsService)
      .addFilterAfter(new CsrfHeaderFilter(), classOf[CsrfFilter])
      .addFilterBefore(new CorsFilter(), classOf[BasicAuthenticationFilter])
      .csrf().csrfTokenRepository(csrfTokenRepository())


  @Bean
  override def userDetailsService: UserDetailsService = new CustomUserDetailsService()

  def csrfTokenRepository(): CsrfTokenRepository ={
    val repository: HttpSessionCsrfTokenRepository  = new HttpSessionCsrfTokenRepository()
    repository.setHeaderName("X-XSRF-TOKEN")
    repository
  }
}


class CustomUserDetailsService extends UserDetailsService {
  val users = scala.collection.mutable.Map[String, CustomUserDetails]()

  override def loadUserByUsername(username: String): UserDetails = {
    println(s"Looking up: $username")
    users.get(username) match {
      case Some(details) =>
        println(details)
        details
      case None =>
        println("Not found")
        null
    }


  }

  def createUser(userId: UserId, username: String, password: String) = {
    println(s"Creating user $username / $password")
    users.put(username, CustomUserDetails(username, password, userId))
  }

}

class CsrfHeaderFilter extends OncePerRequestFilter {
  override def doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain): Unit = {
    val csrf = request.getAttribute(classOf[CsrfToken].getName).asInstanceOf[CsrfToken]
    if (csrf != null) {
      val incomingCookie = WebUtils.getCookie(request, "XSRF-TOKEN")
      val token = csrf.getToken
      if (incomingCookie == null || token!=null && !token.equals(incomingCookie.getValue)) {
        val cookie = new Cookie("XSRF-TOKEN", token)
        cookie.setPath("/")
        response.addCookie(cookie)
      }
    }
    filterChain.doFilter(request, response)
  }
}

class CorsFilter extends Filter {
  override def init(filterConfig: FilterConfig): Unit = {}

  override def destroy(): Unit = {}

  override def doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain): Unit = {
    val request = req.asInstanceOf[HttpServletRequest]
    val response = res.asInstanceOf[HttpServletResponse]
    response.setHeader("Access-Control-Allow-Origin", "*")
    response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE")
    response.setHeader("Access-Control-Allow-Headers", "x-requested-with,authorization,Content-Type")
    response.setHeader("Access-Control-Max-Age", "3600")
    if (request.getMethod()!="OPTIONS") {
      chain.doFilter(req, res)
    } else {
    }
  }
}

