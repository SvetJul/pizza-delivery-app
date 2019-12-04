package club.kuzyayo.pizza.delivery.dao

import club.kuzyayo.pizza.delivery.entity.AbstractEntity
import java.lang.reflect.ParameterizedType
import javax.persistence.EntityManager
import javax.persistence.EntityManagerFactory
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Root

abstract class AbstractDao<E : AbstractEntity>(private val entityManagerFactory: EntityManagerFactory,
                                               private val entityName: String) {
    private var entityClass: Class<E>

    init {
        val genericSuperclass = javaClass.genericSuperclass as ParameterizedType
        @Suppress("UNCHECKED_CAST")
        entityClass = genericSuperclass.actualTypeArguments[0] as Class<E>
    }


    fun findById(id: Any): E? {
        return withinTransaction {
            val entities = it.createQuery("SELECT e FROM $entityName e WHERE e.id = :id", entityClass)
                    .setParameter("id", id)
                    .resultList

            if (entities.isEmpty()) null else entities[0]
        }
    }

    fun findAll(): List<E> {
        return withinTransaction {
            val cb: CriteriaBuilder = it.criteriaBuilder
            val cq: CriteriaQuery<E> = cb.createQuery(entityClass)
            val rootEntry: Root<E> = cq.from(entityClass)
            val all: CriteriaQuery<E> = cq.select(rootEntry)

            val allQuery: TypedQuery<E> = it.createQuery(all)
            allQuery.resultList
        }!!
    }

    fun create(entity: E) = withinTransaction { it.persist(entity) }

    fun update(entity: E) {
        withinTransaction { it.merge(entity) }
    }

    fun exists(id: Any): Boolean {
        return withinTransaction {
            val entityCount = it.createQuery("SELECT count (e) FROM $entityName e WHERE e.id = :id", java.lang.Long::class.java)
                    .setParameter("id", id)
                    .singleResult
            entityCount.equals(1L)
        }!!
    }

    private fun <T> withinTransaction(code: (EntityManager) -> T?): T? {
        val entityManager = entityManagerFactory.createEntityManager()
        val tx = entityManager.transaction
        tx.begin()
        try {
            return code(entityManager)?.also { tx.commit() }
        } catch (e: Exception) {
            tx.rollback()
            throw RuntimeException(e)
        } finally {
            entityManager.close()
        }
    }
}