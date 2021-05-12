package com.example.dataaccess

import com.example.dataaccess.tables.MessageTable
import com.example.model.Message
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class MessageDataAccessor {
    // メッセージを追加する
    fun addMessage(message: Message) {
        transaction {
            MessageTable.insert {
                it[this.id] = message.id
                it[this.message] = message.message
            }
        }
    }

    // メッセージを取得する
    fun getMessage(id: String): Message? {
        var result: Message? = null
        transaction {
            result = MessageTable
                .select { MessageTable.id eq id }
                .map { convertToMessage(it) }
                .firstOrNull()
        }
        return result
    }

    // messagesテーブルのレコードをMessageオブジェクトに変換する
    private fun convertToMessage(row: ResultRow): Message {
        return Message(
            id = row[MessageTable.id],
            message = row[MessageTable.message],
        )
    }
}
