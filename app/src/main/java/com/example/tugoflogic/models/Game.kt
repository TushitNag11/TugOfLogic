package com.example.tugoflogic.models


public class Game(
    var _id: Int,
    var date_time: Int
) {
    companion object {
        fun toNewObject(id: Int): String {
            return "{\"_id\": \"${id}\", \"date_time\": 0}";
        }

    }
}