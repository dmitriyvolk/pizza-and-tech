package net.dmitriyvolk.pizzaandtech.domain.event

import org.joda.time.DateTime

case class EventDetails(name: String, description: String, startDate: DateTime)
