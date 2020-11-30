package com.example.tugoflogic.models


public class Rip(
    var _id: String,
    var game_id: Int,
    var user_id: Int,
    var statement: String,
    var status: ERipStatus,
    var user: User? = null,
    var yes: Int? = 0,
    var no: Int? = 0
) {
    fun toJSON(): String {
        return "{\"_id\": \"${_id}\", \"game_id\":${game_id}, \"user_id\": ${user_id}, \"statement\": \"${
            statement.lines().joinToString("\n")
        }\", \"status\": \"${status}\"}"
    }

    companion object {
        fun toNewObject(id: String, game_id: Int, user_id: Int, statement: String, status: ERipStatus): String {
            return "{\"_id\": \"${id}\", \"game_id\":${game_id}, \"user_id\": ${user_id}, \"statement\": \"${statement}\", \"status\": \"${status}\"}"
        }
    }


}