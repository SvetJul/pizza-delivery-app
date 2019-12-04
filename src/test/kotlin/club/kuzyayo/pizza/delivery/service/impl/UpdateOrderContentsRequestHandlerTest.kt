package club.kuzyayo.pizza.delivery.service.impl

import club.kuzyayo.pizza.delivery.service.OrderService
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import spark.Request
import spark.Response


internal class UpdateOrderContentsRequestHandlerTest {
    private val orderService: OrderService = mock()

    private val requestHandler = UpdateOrderContentsRequestHandler(orderService)

    @Test
    internal fun `should update item count with correct delta when it starts with minus`() {
        //given
        val request: Request = request("1", "4 Cheese", "-1")
        val response: Response = mock()

        //when
        val responseString = requestHandler.handle(request, response)

        //then
        then(orderService)
                .should()
                .updateItemCount(1, "4 Cheese", -1)
        assertEquals("Updated successfully =)", responseString)
    }

    @Test
    internal fun `should update item count with correct delta when it starts with plus`() {
        //given
        val request: Request = request("1", "4 Cheese", "+1")
        val response: Response = mock()

        //when
        val responseString = requestHandler.handle(request, response)

        //then
        then(orderService)
                .should()
                .updateItemCount(1, "4 Cheese", 1)
        assertEquals("Updated successfully =)", responseString)
    }

    @Test
    internal fun `should save item when exact count is passed`() {
        //given
        val request: Request = request("1", "4 Cheese", "6")
        val response: Response = mock()

        //when
        val responseString = requestHandler.handle(request, response)

        //then
        then(orderService)
                .should()
                .saveItem(1, "4 Cheese", 6)
        assertEquals("Updated successfully =)", responseString)
    }

    @Test
    internal fun `should fail when id is not passed`() {
        //given
        val request: Request = request(null, "4 Cheese", "6")
        val response: Response = mock()

        //then
        assertThrows<IllegalArgumentException> { requestHandler.handle(request, response) }
    }

    @Test
    internal fun `should fail when pizza name is not passed`() {
        //given
        val request: Request = request("1", null, "6")
        val response: Response = mock()

        //then
        assertThrows<IllegalArgumentException> { requestHandler.handle(request, response) }
    }

    @Test
    internal fun `should fail when count is not passed`() {
        //given
        val request: Request = request("1", "4 Cheese", null)
        val response: Response = mock()

        //then
        assertThrows<IllegalArgumentException> { requestHandler.handle(request, response) }
    }

    private fun request(idParam: String?,
                        pizzaParam: String?,
                        countParam: String?): Request {
        val request: Request = mock()
        given { request.queryParams("id") }
                .willReturn(idParam)
        given { request.queryParams("pizza") }
                .willReturn(pizzaParam)
        given { request.queryParams("count") }
                .willReturn(countParam)
        return request
    }
}