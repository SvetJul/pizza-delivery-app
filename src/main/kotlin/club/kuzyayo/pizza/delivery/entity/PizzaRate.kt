package club.kuzyayo.pizza.delivery.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "pizza_rate")
class PizzaRate: AbstractEntity {
    @Id
    @Column(name = "pizza_name")
    var pizzaName: String? = null

    @Column(name = "total_count", nullable = false)
    var totalCount: Int? = null

    @Column(name = "rank_by_count", nullable = false)
    var rankByCount: Int? = null

    @Column(name = "rank_by_profit", nullable = false)
    var rankByProfit: Int? = null

    @Column(name = "top_day")
    var topDay: Int? = null

    override val id: Any?
        get() = pizzaName
}