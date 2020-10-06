package com.example.tugoflogic.model

import androidx.room.PrimaryKey

data class VoteType (
    @PrimaryKey(autoGenerate = true) var id: Int,
    var type: VoteType
)