package com.example.tugoflogic.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true) var id: Int,
    @ColumnInfo(name = "user_name") var userName: String,
    @ColumnInfo var password: String
) {
    constructor() : this(0, "", "")

    constructor(userName: String, password: String) : this() {
        this.userName = userName
        this.password = password
    }
}