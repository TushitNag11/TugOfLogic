package com.example.tugoflogic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") var uid: Int,
    @ColumnInfo(name = "user_name") var userName: String,
    @ColumnInfo var password: String
)