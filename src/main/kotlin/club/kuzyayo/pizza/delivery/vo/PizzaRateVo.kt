package club.kuzyayo.pizza.delivery.vo

import java.math.BigDecimal
import java.time.DayOfWeek

data class PizzaRateVo(
        val name: String,
        val price: BigDecimal,
        val count: Int,
        val rankByCount: Int,
        val rankByProfit: Int,
        val topDay: DayOfWeek? = null
)