package club.kuzyayo.pizza.delivery.service.impl

import club.kuzyayo.pizza.delivery.OrderStatus
import club.kuzyayo.pizza.delivery.dao.CustomerDao
import club.kuzyayo.pizza.delivery.dao.OrderDao
import club.kuzyayo.pizza.delivery.dao.OrderItemDao
import club.kuzyayo.pizza.delivery.entity.Customer
import club.kuzyayo.pizza.delivery.entity.Order
import club.kuzyayo.pizza.delivery.entity.OrderItem
import club.kuzyayo.pizza.delivery.entity.OrderItemId
import club.kuzyayo.pizza.delivery.service.OrderService
import java.time.LocalDateTime

class OrderServiceImpl(private val orderDao: OrderDao,
                       private val orderItemDao: OrderItemDao,
                       private val customerDao: CustomerDao) : OrderService {
    override fun saveOrder(id: Long?,
                           phoneNumber: String?,
                           address: String?,
                           deliveryTime: LocalDateTime?) {
        val order = id?.let { orderDao.findById(it) }
        if (order != null) {
            updateOrder(order, phoneNumber, address, deliveryTime)
        } else {
            createOrder(phoneNumber = requireNotNull(phoneNumber),
                    address = requireNotNull(address),
                    deliveryTime = requireNotNull(deliveryTime)
            )
        }
    }

    override fun updateItemCount(orderId: Long, pizza: String, delta: Int) {
        val id = OrderItemId(orderId, pizza)
        val orderItem = orderItemDao.findById(id)
        val newCount = if (orderItem != null) {
            orderItem.count!! + delta
        } else {
            delta
        }
        saveItem(orderId, pizza, newCount)
    }

    override fun saveItem(orderId: Long, pizza: String, count: Int) {
        require(count >= 0)
        val id = OrderItemId(orderId, pizza)
        val entity = orderItemDao.findById(id)
        if (entity != null) {
            orderItemDao.update(entity.apply { this.count = count })
        } else {
            orderItemDao.create(OrderItem(id, count))
        }
    }

    private fun updateOrder(order: Order,
                            phoneNumber: String?,
                            address: String?,
                            deliveryTime: LocalDateTime?) {
        phoneNumber?.let { order.phoneNumber = it }
        address?.let { order.address = it }
        deliveryTime?.let { order.deliveryTime = it }

        orderDao.update(order)
    }

    private fun createOrder(phoneNumber: String,
                            address: String,
                            deliveryTime: LocalDateTime) {
        if (!customerDao.exists(phoneNumber)) {
            customerDao.create(Customer(phoneNumber))
        }
        orderDao.create(Order(customer = phoneNumber,
                address = address,
                deliveryTime = deliveryTime,
                status = OrderStatus.ACCEPTED))
    }
}