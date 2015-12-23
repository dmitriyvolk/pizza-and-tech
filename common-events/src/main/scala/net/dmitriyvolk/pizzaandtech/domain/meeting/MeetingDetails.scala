package net.dmitriyvolk.pizzaandtech.domain.meeting

import org.joda.time.DateTime

case class MeetingDetails(name: String, description: String, startDate: DateTime)
case class MeetingIdAndDetails(id: MeetingId, meetingDetails: MeetingDetails)