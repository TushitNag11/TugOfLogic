package com.example.tugoflogic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 *
 * @author  Agam
 * @version 1.0
 * @since   2020-10-06
 */
@Entity(
    tableName = "Vote", foreignKeys = arrayOf(
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
data class Vote(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "user_id") var userId: Int,
    @ColumnInfo(name = "game_id") var gameId: Int,
    @ColumnInfo(name = "vote_type_id") var voteTypeId: Int,
    @ColumnInfo(name = "vote_flag") var voteFlag: Boolean
)