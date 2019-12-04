package club.kuzyayo.pizza.delivery

import javax.persistence.AttributeConverter
import javax.persistence.Converter

enum class OrderStatus {
    ACCEPTED,
    IN_PROGRESS,
    SHIPPED,
    COMPLETED;
}

@Converter(autoApply = true)
class JpaConverter: AttributeConverter<OrderStatus, String> {
    override fun convertToDatabaseColumn(attribute: OrderStatus?): String? {
        return attribute?.name
    }

    override fun convertToEntityAttribute(dbData: String?): OrderStatus? {
        if (dbData == null) {
            return null
        }
        for (prop in OrderStatus.values()) {
            if (prop.name == dbData) {
                return prop
            }
        }
        throw IllegalArgumentException(String.format("Invalid value '%s' for %s.", dbData, OrderStatus::class.java.simpleName))
    }
}
