package club.kuzyayo.pizza.delivery

import com.zaxxer.hikari.HikariDataSource

val dataSource = HikariDataSource().apply {
    username = "postgres"
    jdbcUrl = "jdbc:postgresql://localhost/bd_course"
}