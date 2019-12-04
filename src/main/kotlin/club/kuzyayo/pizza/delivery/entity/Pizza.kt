package club.kuzyayo.pizza.delivery.entity

import java.math.BigDecimal
import javax.persistence.*

const val PIZZA_TABLE_NAME = "pizza"
const val PIZZA_ENTITY_NAME = "Pizza"

@Entity(name = PIZZA_ENTITY_NAME)
@Table(name = PIZZA_TABLE_NAME)
class Pizza : AbstractEntity {
    @Id
    @Column(name = "name")
    var name: String? = null

    @Column(name = "weight", nullable = false)
    var weight: BigDecimal? = null

    @Column(name = "price", nullable = false)
    var price: BigDecimal? = null

    @OneToOne
    @JoinColumn(name = "name")
    var rate: PizzaRate? = null
    override val id: Any?
        get() = name
}