package club.kuzyayo.pizza.delivery.service.impl

import club.kuzyayo.pizza.delivery.dao.PizzaDao
import club.kuzyayo.pizza.delivery.entity.Pizza
import club.kuzyayo.pizza.delivery.service.PizzaService
import club.kuzyayo.pizza.delivery.vo.PizzaRateVo
import java.time.DayOfWeek


class PizzaServiceImpl(private val pizzaDao: PizzaDao) : PizzaService {
    override fun find(name: String?): List<PizzaRateVo> {
        val pizza: List<Pizza> = name?.let { pizzaName ->
            val pizza = pizzaDao.findById(pizzaName)
            pizza?.let { listOf(it) }
                    ?: throw IllegalArgumentException("Pizza with name $name not found")
        } ?: pizzaDao.findAll()

        return pizza.map { it.toVo() }
    }

    private fun Pizza.toVo(): PizzaRateVo {
        return PizzaRateVo(
                name = name!!,
                price = price!!,
                count = rate!!.totalCount!!,
                rankByCount = rate!!.rankByCount!!,
                rankByProfit = rate!!.rankByProfit!!,
                topDay = rate!!.topDay?.let {
                    when (it) {
                        0 -> DayOfWeek.SUNDAY
                        1 -> DayOfWeek.MONDAY
                        2 -> DayOfWeek.TUESDAY
                        3 -> DayOfWeek.WEDNESDAY
                        4 -> DayOfWeek.THURSDAY
                        5 -> DayOfWeek.FRIDAY
                        6 -> DayOfWeek.SATURDAY
                        else -> throw RuntimeException()
                    }
                }
        )
    }
}