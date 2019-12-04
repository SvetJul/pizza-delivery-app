package club.kuzyayo.pizza.delivery.service.impl

import club.kuzyayo.pizza.delivery.service.PizzaService
import club.kuzyayo.pizza.delivery.service.RequestHandler
import club.kuzyayo.pizza.delivery.vo.PizzaRateVo
import spark.Request
import spark.Response

class SalesRequestHandler(private val pizzaService: PizzaService) : RequestHandler {
    override fun handle(request: Request, response: Response): String {
        val pizzaName: String? = request.queryParams("pizza")
        return pizzaService.find(pizzaName).toHtmlTable()
    }

    private fun List<PizzaRateVo>.toHtmlTable(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendln("<table border=\"1\" cellpadding=\"15\" align=\"center\">")
        stringBuilder.appendln("<caption>Sales</caption>")
        stringBuilder.appendln("<tbody>")
        stringBuilder.appendln("<tr>")
        stringBuilder.appendln("<th>Name</th>")
        stringBuilder.appendln("<th>Price</th>")
        stringBuilder.appendln("<th>Count</th>")
        stringBuilder.appendln("<th>Rank By Count</th>")
        stringBuilder.appendln("<th>Rank By Profit</th>")
        stringBuilder.appendln("<th>Top Day</th>")
        stringBuilder.appendln("</tr>")
        stringBuilder.appendln(map { it.toHtmlTableRow() }
                .reduce { a, b -> a + b })
        stringBuilder.appendln("</tbody>")
        stringBuilder.appendln("</table>")

        return stringBuilder.toString()
    }

    private fun PizzaRateVo.toHtmlTableRow(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.appendln("<tr>")
        stringBuilder.appendln(name.toHtmlTableCell())
        stringBuilder.appendln(price.toHtmlTableCell())
        stringBuilder.appendln(count.toHtmlTableCell())
        stringBuilder.appendln(rankByCount.toHtmlTableCell())
        stringBuilder.appendln(rankByProfit.toHtmlTableCell())
        stringBuilder.appendln(topDay?.toHtmlTableCell() ?: "-".toHtmlTableCell())
        stringBuilder.appendln("</tr>")
        return stringBuilder.toString()
    }

    private fun Any.toHtmlTableCell(): String = "<td>${this}</td>"
}
