package net.dmitriyvolk.pizzaandtech.domain.group

object GroupMother {

  val groupFixtureOne = makeGroupDetails("1")

  def makeGroupDetails(seed: String) = GroupDetails(s"Group-$seed", s"Description for Group-$seed")
}
