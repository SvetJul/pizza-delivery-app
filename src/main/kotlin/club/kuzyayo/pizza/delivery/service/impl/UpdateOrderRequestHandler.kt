package club.kuzyayo.pizza.delivery.service.impl

import club.kuzyayo.pizza.delivery.service.OrderService
import club.kuzyayo.pizza.delivery.service.RequestHandler
import spark.Request
import spark.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

private const val DATE_PATTERN = "yyyy-dd-MM"

class UpdateOrderRequestHandler(private val orderService: OrderService) : RequestHandler {
    override fun handle(request: Request, response: Response): String {
        val id: String? = request.queryParams("id")
        val phone: String? = request.queryParams("phone")
        val address: String? = request.queryParams("address")
        val deliveryTime: String? = request.queryParams("delivery_time")
        orderService.saveOrder(
                id = id?.toLongOrNull(),
                phoneNumber = phone,
                address = address,
                deliveryTime = deliveryTime?.toLocalDateTime()
        )
        return "Accepted successfully =)"
    }

    private fun String.toLocalDateTime(): LocalDateTime {
        try {
            val dateTimeParts = split("+")
            val date = LocalDate.parse(dateTimeParts[0], DateTimeFormatter.ofPattern(DATE_PATTERN))
            val time = dateTimeParts[1].split("-")
            return LocalDateTime.of(date, LocalTime.of(time[0].toInt(), time[1].toInt()))
        } catch (e: Exception) {
            throw IllegalArgumentException("Incorrect date format, expected is '${DATE_PATTERN}+hh-mm'")
        }
    }
}