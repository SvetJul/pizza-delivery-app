package club.kuzyayo.pizza.delivery.dao

import club.kuzyayo.pizza.delivery.entity.ORDER_ITEM_ENTITY_NAME
import club.kuzyayo.pizza.delivery.entity.OrderItem
import javax.persistence.EntityManagerFactory

class OrderItemDao(entityManagerFactory: EntityManagerFactory) : AbstractDao<OrderItem>(
        entityName = ORDER_ITEM_ENTITY_NAME,
        entityManagerFactory = entityManagerFactory)