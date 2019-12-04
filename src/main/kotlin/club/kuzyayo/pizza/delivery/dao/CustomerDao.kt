package club.kuzyayo.pizza.delivery.dao

import club.kuzyayo.pizza.delivery.entity.CUSTOMER_ENTITY_NAME
import club.kuzyayo.pizza.delivery.entity.Customer
import javax.persistence.EntityManagerFactory

class CustomerDao(entityManagerFactory: EntityManagerFactory) : AbstractDao<Customer>(
        entityName = CUSTOMER_ENTITY_NAME,
        entityManagerFactory = entityManagerFactory)