package club.kuzyayo.pizza.delivery.entity

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

const val CUSTOMER_ENTITY_NAME = "Customer"
const val CUSTOMER_TABLE_NAME = "customer"

@Entity(name = CUSTOMER_ENTITY_NAME)
@Table(name = CUSTOMER_TABLE_NAME)
class Customer : AbstractEntity {
    @Id
    @Column(name = "phone_number")
    var phoneNumber: String? = null

    constructor()

    constructor(phoneNumber: String?) {
        this.phoneNumber = phoneNumber
    }

    override val id: Any?
        get() = phoneNumber
}