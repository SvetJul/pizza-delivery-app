// Copyright (C) 2019 Dmitry Barashev
package club.kuzyayo.pizza.delivery

import club.kuzyayo.pizza.delivery.dao.CustomerDao
import club.kuzyayo.pizza.delivery.dao.OrderDao
import club.kuzyayo.pizza.delivery.dao.OrderItemDao
import club.kuzyayo.pizza.delivery.dao.PizzaDao
import club.kuzyayo.pizza.delivery.service.OrderService
import club.kuzyayo.pizza.delivery.service.PizzaService
import club.kuzyayo.pizza.delivery.service.RequestHandler
import club.kuzyayo.pizza.delivery.service.impl.*
import org.eclipse.jetty.http.HttpStatus
import org.slf4j.LoggerFactory
import spark.Request
import spark.Response
import spark.Spark.*
import javax.persistence.Persistence

private val logger = LoggerFactory.getLogger(App::class.java)

class App(
        private val salesRequestHandler: RequestHandler,
        private val updateOrderRequestHandler: RequestHandler,
        private val updateOrderContentsRequestHandler: RequestHandler) {

    init {
        exception(Exception::class.java) { e, _, _ ->
            e.printStackTrace()
        }
        staticFiles.location("/public")
        port(8080)

        get("/sales") { req, res ->
            `try`(req, res, salesRequestHandler)
        }

        get("/update_order") { req, res ->
            `try`(req, res, updateOrderRequestHandler)
        }

        get("/update_order_contents") { req, res ->
            `try`(req, res, updateOrderContentsRequestHandler)
        }
    }

    private fun `try`(request: Request,
                      response: Response,
                      requestHandler: RequestHandler): String = try {
        requestHandler.handle(request, response)
    } catch (e: IllegalArgumentException) {
        response.status(HttpStatus.BAD_REQUEST_400)
        logger.error(e.message, e)
        "Bad request: '${e.message}' =( "
    } catch (e: Exception) {
        response.status(HttpStatus.INTERNAL_SERVER_ERROR_500)
        logger.error(e.message, e)
        "Unexpected error =("
    }
}

fun main() {
    val entityManagerFactory = Persistence.createEntityManagerFactory("PizzaDelivery")
    val pizzaService: PizzaService = PizzaServiceImpl(PizzaDao(entityManagerFactory))
    val orderService: OrderService = OrderServiceImpl(
            orderDao = OrderDao(entityManagerFactory),
            orderItemDao = OrderItemDao(entityManagerFactory),
            customerDao = CustomerDao(entityManagerFactory)
    )
    App(salesRequestHandler = SalesRequestHandler(pizzaService),
            updateOrderRequestHandler = UpdateOrderRequestHandler(orderService),
            updateOrderContentsRequestHandler = UpdateOrderContentsRequestHandler(orderService))
}
