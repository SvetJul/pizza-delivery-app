package club.kuzyayo.pizza.delivery.entity

import java.io.Serializable
import javax.persistence.*

@Embeddable
class OrderItemId : Serializable {
    @Column(name = "order_id")
    var orderId: Long? = null

    @Column(name = "pizza")
    var pizza: String? = null

    constructor()

    constructor(orderId: Long?, pizza: String?) {
        this.orderId = orderId
        this.pizza = pizza
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OrderItemId) return false

        if (orderId != other.orderId) return false
        if (pizza != other.pizza) return false

        return true
    }

    override fun hashCode(): Int {
        var result = orderId?.hashCode() ?: 0
        result = 31 * result + (pizza?.hashCode() ?: 0)
        return result
    }
}

const val ORDER_ITEM_TABLE_NAME = "order_item"
const val ORDER_ITEM_ENTITY_NAME = "OrderItem"

@Entity(name = ORDER_ITEM_ENTITY_NAME)
@Table(name = ORDER_ITEM_TABLE_NAME)
class OrderItem : AbstractEntity {
    @EmbeddedId
    override var id: OrderItemId? = null

    @Column(name = "count", nullable = false)
    var count: Int? = null

    constructor()

    constructor(id: OrderItemId?, count: Int?) {
        this.id = id
        this.count = count
    }
}