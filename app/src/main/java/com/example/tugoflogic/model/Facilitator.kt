package com.example.tugoflogic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "Facilitator", foreignKeys = arrayOf(
    ForeignKey(entity = User::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("user_id")
    ),
    ForeignKey(entity = Game::class,
        parentColumns = arrayOf("game_id"),
        childColumns = arrayOf("game_id")
    )
))
data class Facilitator(
    @ColumnInfo(name = "user_id") var userId: Int,
    @ColumnInfo(name = "game_id") var gameId: Int
)