package com.example.tugoflogic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

/**
 *
 * @author
 * @version 1.0
 * @since   2020-10-06
 */
@Entity(
    tableName = "Player", foreignKeys = arrayOf(
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("user_id"),
            childColumns = arrayOf("user_id")
        ),
        ForeignKey(
            entity = Game::class,
            parentColumns = arrayOf("game_id"),
            childColumns = arrayOf("game_id")
        )
    ),
    primaryKeys = arrayOf("user_id", "game_id")
)
data class Player(
    @ColumnInfo(name = "game_id") var gameId: Int,
    @ColumnInfo(name = "user_id") var userId: Int
)