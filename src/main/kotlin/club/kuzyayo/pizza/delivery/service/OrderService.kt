package club.kuzyayo.pizza.delivery.service

import java.time.LocalDateTime

interface OrderService {
    fun saveOrder(id: Long?, phoneNumber: String?, address: String?, deliveryTime: LocalDateTime?)

    fun saveItem(orderId: Long, pizza: String, count: Int)

    fun updateItemCount(orderId: Long, pizza: String, delta: Int)
}