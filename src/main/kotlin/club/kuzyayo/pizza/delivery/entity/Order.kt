package club.kuzyayo.pizza.delivery.entity

import club.kuzyayo.pizza.delivery.OrderStatus
import java.time.LocalDateTime
import javax.persistence.*

const val ORDER_TABLE_NAME = "\"order\""
const val ORDER_ENTITY_NAME = "Order"

@Entity(name = ORDER_ENTITY_NAME)
@Table(name = ORDER_TABLE_NAME)
class Order : AbstractEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null

    @Column(name = "date", nullable = false)
    var deliveryTime: LocalDateTime? = null

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String? = null

    @Column(name = "address", nullable = false)
    var address: String? = null

    @Column(name = "status", nullable = false)
    var status: OrderStatus? = null

    constructor()

    constructor(deliveryTime: LocalDateTime,
                customer: String,
                address: String,
                status: OrderStatus) {
        this.deliveryTime = deliveryTime
        this.phoneNumber = customer
        this.address = address
        this.status = status
    }
}