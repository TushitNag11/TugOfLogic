package com.example.tugoflogic.models


public class Rip(
    var _id: String,
    var game_id: Int,
    var user_id: Int,
    var statement: String,
    var status: ERipStatus
) {

        companion object {
        fun toNewObject(id: Int,game_id: Int,statement: String, status: ERipStatus): String {
            return "{\"_id\": \"${id}\", \"game_id\":${game_id}, \"user_id\": 1, \"statement\": \"${statement}\", \"status\": \"${status}\"}"
        }
    }



}