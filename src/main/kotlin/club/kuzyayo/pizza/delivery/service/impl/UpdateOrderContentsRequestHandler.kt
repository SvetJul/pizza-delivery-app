package club.kuzyayo.pizza.delivery.service.impl

import club.kuzyayo.pizza.delivery.service.OrderService
import club.kuzyayo.pizza.delivery.service.RequestHandler
import spark.Request
import spark.Response

class UpdateOrderContentsRequestHandler(private val orderService: OrderService) : RequestHandler {
    override fun handle(request: Request, response: Response): String {
        val id: String = requireNotNull(request.queryParams("id"))
        val pizza: String = requireNotNull(request.queryParams("pizza"))
        val countParam: String = requireNotNull(request.queryParams("count"))

        when {
            countParam.startsWith("+")
                    || countParam.startsWith("-") -> orderService.updateItemCount(
                    orderId = id.toLong(),
                    pizza = pizza,
                    delta = countParam.toInt()
            )
            else -> orderService.saveItem(
                    orderId = id.toLong(),
                    pizza = pizza,
                    count = countParam.toInt()
            )
        }
        return "Updated successfully =)"
    }
}