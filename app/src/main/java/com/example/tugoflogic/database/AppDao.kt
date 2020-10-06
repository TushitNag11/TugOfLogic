package com.example.tugoflogic.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tugoflogic.model.Bout
import com.example.tugoflogic.model.Facilitator
import com.example.tugoflogic.model.Game
import com.example.tugoflogic.model.User

@Dao
interface AppDao {

    //User table operations
    @Insert
    fun insertAllUser(vararg users: User)

    @Delete
    fun deleteUsers(vararg users: User)

    @Query("SELECT COUNT(*) FROM User")
    fun getUserCount(): Int

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    //Bout Table operations
    @Insert
    fun insertBout(bout: Bout)

    @Delete
    fun deleteBout(bout: Bout)

    @Update
    fun updateBout(bout: Bout)

    //Facilitator table operations
    @Insert
    fun insertFacilitator(facilitator: Facilitator)

    @Delete
    fun deleteFacilitator(facilitator: Facilitator)

    @Update
    fun updateFacilitator(facilitator: Facilitator)

    //Game table ops
    @Insert
    fun insertGame(game: Game)

    @Delete
    fun deleteGame(game: Game)

    @Update
    fun updateGame(game: Game)

    //Main Claim Table ops

}