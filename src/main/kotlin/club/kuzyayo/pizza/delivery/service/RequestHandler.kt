package club.kuzyayo.pizza.delivery.service

import spark.Request
import spark.Response

@FunctionalInterface
interface RequestHandler {
    fun handle(request: Request, response: Response): String
}