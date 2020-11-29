package com.example.tugoflogic.models


public class Vote(
    var _id: String,
    var game_id: Int,
    var user_id: Int,
    var vote_type_id: Int,
    var statement_id: Int,
    var vote_flag: Int,
    var statement: String = ""
) {

    companion object {
        fun toNewObject(
            id: Int,
            game_id: Int,
            vote_type_id: Int,
            vote_flag: Boolean,
            statement_id: Int
        ): String {
            return "{\"_id\": \"${id}\", \"game_id\":${game_id}, \"user_id\":1, \"vote_type_id\": ${vote_type_id},\"statement_id\":${statement_id},\"vote_flag\":${vote_flag}}"
        }
    }
}