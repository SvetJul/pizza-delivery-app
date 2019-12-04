package club.kuzyayo.pizza.delivery.dao

import club.kuzyayo.pizza.delivery.entity.ORDER_ENTITY_NAME
import club.kuzyayo.pizza.delivery.entity.Order
import javax.persistence.EntityManagerFactory

class OrderDao(entityManagerFactory: EntityManagerFactory) : AbstractDao<Order>(
        entityName = ORDER_ENTITY_NAME,
        entityManagerFactory = entityManagerFactory)