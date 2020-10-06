package com.example.tugoflogic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class VoteType (
    @PrimaryKey(autoGenerate = true) var id: Int,
    var type: Int
)