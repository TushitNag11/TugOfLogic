package com.example.tugoflogic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 * @author
 * @version 1.0
 * @since   2020-10-06
 */
@Entity
data class VoteType(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var type: Int
)