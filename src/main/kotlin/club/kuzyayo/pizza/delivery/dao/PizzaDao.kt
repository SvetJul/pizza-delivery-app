package club.kuzyayo.pizza.delivery.dao

import club.kuzyayo.pizza.delivery.entity.PIZZA_ENTITY_NAME
import club.kuzyayo.pizza.delivery.entity.Pizza
import javax.persistence.EntityManagerFactory

class PizzaDao(entityManagerFactory: EntityManagerFactory) : AbstractDao<Pizza>(
        entityName = PIZZA_ENTITY_NAME,
        entityManagerFactory = entityManagerFactory)