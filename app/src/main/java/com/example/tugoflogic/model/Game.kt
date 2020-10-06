package com.example.tugoflogic.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Game (
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "game_id") var gameId: Int,
    @ColumnInfo(name = "date_time") var dateTime: String
)