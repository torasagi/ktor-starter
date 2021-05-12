package com.example.dataaccess.tables

import org.jetbrains.exposed.sql.Table

object MessageTable : Table("messages") {
    val id = text("id")
    val message = text("message")

    override val primaryKey = PrimaryKey(id)
}
