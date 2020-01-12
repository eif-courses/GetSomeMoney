package get.some.money.starter.Models

import com.google.firebase.Timestamp

data class Level(val id: Long = 0,
                 val name: String ="",
                 val assets: List<Int> = emptyList(),
                 val category: String="",
                 val question: String="",
                 val timestamp: Timestamp = Timestamp(java.util.Date())
                 )