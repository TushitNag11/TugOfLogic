package com.example.tugoflogic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.tugoflogic.RipStatus

/**
 *
 * @author  Agam
 * @version 1.0
 * @since   2020-10-06
 */
@Entity(
    tableName = "Rip", foreignKeys = arrayOf(
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
    )
)
data class Rip(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "user_id") var userId: Int,
    @ColumnInfo(name = "game_id") var gameId: Int,
    var statement: String,
    var status: Int
)