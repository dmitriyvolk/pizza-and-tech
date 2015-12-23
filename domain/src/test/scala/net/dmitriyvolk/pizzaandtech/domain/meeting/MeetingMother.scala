package net.dmitriyvolk.pizzaandtech.domain.meeting

import org.joda.time.DateTime

object MeetingMother {

  val meetingFixtureOne = makeMeetingDetails(seed = "1")

  def makeMeetingDetails(seed: String, startDatePolicy: () => DateTime = defaultStartDatePolicy): MeetingDetails =
    MeetingDetails(
      name = s"Meeting=$seed",
      description = s"Description for Meeting-$seed",
//      startDate = startDatePolicy()
      startDate = new DateTime()
    )

  val defaultStartDatePolicy = () => new DateTime()
}
