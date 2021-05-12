package com.example

import com.example.dataaccess.MessageDataAccessor
import com.example.routes.messagesRoutes
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.jetty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module() {
    install(ContentNegotiation) {
        gson {
        }
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Authorization)
        header(HttpHeaders.ContentType)
        allowCredentials = true
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    // データベースのホスト名 (環境変数 `DATABASE_HOSTNAME` から取得)
    val databaseHostName = System.getenv("DATABASE_HOSTNAME") ?: "localhost"

    Database.connect(
        url = "jdbc:mysql://$databaseHostName:3306/ktor-starter",
        driver = "com.mysql.cj.jdbc.Driver",
        user = "root",
    )

    routing {
        messagesRoutes(MessageDataAccessor())
    }
}
