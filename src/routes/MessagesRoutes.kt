package com.example.routes

import com.example.dataaccess.MessageDataAccessor
import com.example.model.Message
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.messagesRoutes(messageDataAccessor: MessageDataAccessor) {
    route("/api/v1/messages") {
        // メッセージ投稿用のエンドポイント
        post {
            val message = call.receive<Message>()
            messageDataAccessor.addMessage(message)
            call.respond(HttpStatusCode.OK)
        }

        // メッセージ取得用のエンドポイント
        get("/{id}") {
            val messageId = call.parameters["id"] ?: ""
            val result = messageDataAccessor.getMessage(messageId)
            if (result != null) {
                call.respond(HttpStatusCode.OK, result)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
