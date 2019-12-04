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
import java.time.LocalDateTime


internal class UpdateOrderRequestHandlerTest {
    private val orderService: OrderService = mock()

    private val requestHandler = UpdateOrderRequestHandler(orderService)

    @Test
    internal fun `should save order when delivery time is not passed`() {
        //given
        val request: Request = request(
                idParam = "-1",
                phoneParam = "89211234567",
                addressParam = "не_дом_и_не_улица")
        val response: Response = mock()

        //when
        val responseString = requestHandler.handle(request, response)

        //then
        then(orderService)
                .should()
                .saveOrder(-1, "89211234567", "не_дом_и_не_улица", null)
        assertEquals("Accepted successfully =)", responseString)
    }

    @Test
    internal fun `should save order when delivery time is passed correctly`() {
        //given
        val request: Request = request(
                idParam = "-1",
                phoneParam = "89211234567",
                addressParam = "не_дом_и_не_улица",
                deliveryTimeParam = "2019-24-11+11-00")
        val response: Response = mock()

        //when
        val responseString = requestHandler.handle(request, response)

        //then
        then(orderService)
                .should()
                .saveOrder(
                        id = -1,
                        phoneNumber = "89211234567",
                        address = "не_дом_и_не_улица",
                        deliveryTime = LocalDateTime.of(2019, 11, 24, 11, 0))
        assertEquals("Accepted successfully =)", responseString)
    }

    @Test
    internal fun `should fail when delivery time has bad format`() {
        //given
        val request: Request = request(
                idParam = "-1",
                phoneParam = "89211234567",
                addressParam = "не_дом_и_не_улица",
                deliveryTimeParam = "2019-11-24+11-00")
        val response: Response = mock()

        //then
        assertThrows<IllegalArgumentException> { requestHandler.handle(request, response) }
    }

    private fun request(idParam: String? = null,
                        phoneParam: String? = null,
                        addressParam: String? = null,
                        deliveryTimeParam: String? = null): Request {
        val request: Request = mock()
        given { request.queryParams("id") }
                .willReturn(idParam)
        given { request.queryParams("phone") }
                .willReturn(phoneParam)
        given { request.queryParams("address") }
                .willReturn(addressParam)
        given { request.queryParams("delivery_time") }
                .willReturn(deliveryTimeParam)
        return request
    }
}