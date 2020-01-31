package get.some.money.starter.Models


data class User(
  var name: String = "",
  val score: Int = 0,
  val uuid: String = "",
  val coins: Int = 10,
  val levels: List<Long> = emptyList(),
  val multiplier: Int = 1,
  val specialLevels: Int = 0,
  val items: List<Inventory> = emptyList()
)