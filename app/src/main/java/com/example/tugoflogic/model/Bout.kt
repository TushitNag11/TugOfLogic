package com.example.tugoflogic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Bout", foreignKeys = arrayOf(
    ForeignKey(entity = User::class,
        parentColumns = arrayOf("user_id"),
        childColumns = arrayOf("user_id")
    ),
    ForeignKey(entity = Rip::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("rip_id")
    )
))
data class Bout(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "user_id") var userId: Int,
    @ColumnInfo(name = "rip_id") var ripId: Int
    )