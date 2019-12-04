package club.kuzyayo.pizza.delivery.service

import club.kuzyayo.pizza.delivery.vo.PizzaRateVo

interface PizzaService {
    fun find(name: String? = null): List<PizzaRateVo>
}