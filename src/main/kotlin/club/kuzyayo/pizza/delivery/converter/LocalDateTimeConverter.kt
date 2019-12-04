package club.kuzyayo.pizza.delivery.converter

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class LocalDateTimeConverter : AttributeConverter<LocalDateTime, Timestamp> {
    override fun convertToDatabaseColumn(ldt: LocalDateTime?): Timestamp? {
        return Timestamp.valueOf(ldt)
    }

    override fun convertToEntityAttribute(ts: Timestamp): LocalDateTime? {
        return ts.toLocalDateTime()
    }
}