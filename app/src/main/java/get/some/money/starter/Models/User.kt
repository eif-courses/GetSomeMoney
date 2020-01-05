package get.some.money.starter.Models


data class User(var name: String="",
           val score: String="",
           val uuid: String="",
           val coins: Int = 10,
           val experience: Int = 0,
           val completion: Int = 5
           )