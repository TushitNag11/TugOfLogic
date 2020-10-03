package com.example.tugoflogic.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.tugoflogic.model.User

@Dao
interface UserDao {

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun deleteUser(vararg users: User)

    @Query("SELECT COUNT(*) FROM User")
    fun getUserCount(): Int
}