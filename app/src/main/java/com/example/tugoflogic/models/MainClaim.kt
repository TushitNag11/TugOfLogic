package com.example.tugoflogic.models


public class MainClaim(
    var _id: String,
    var game_id: Int,
    var user_id: Int,
    var statement: String
) {
    companion object {
        fun toNewObject(id: Int,game_id: Int,statement: String): String {
            return "{\"_id\": \"${id}\", \"game_id\":${game_id}, \"user_id\": 1, \"statement\": \"${statement}\"}"
        }
    }

}