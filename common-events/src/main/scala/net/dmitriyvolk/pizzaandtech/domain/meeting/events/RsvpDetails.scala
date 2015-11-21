package net.dmitriyvolk.pizzaandtech.domain.meeting.events

/**
  * Created by xbo on 11/3/15.
  */
case class RsvpDetails ( going: YesNoMaybe, comment: String)

sealed trait YesNoMaybe
object Yes extends YesNoMaybe
object No extends YesNoMaybe
object Maybe extends YesNoMaybe
