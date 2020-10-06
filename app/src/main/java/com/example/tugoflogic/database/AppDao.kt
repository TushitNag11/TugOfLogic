package com.example.tugoflogic.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tugoflogic.model.*

/**
 *
 * @author  Agam
 * @version 1.0
 * @since   2020-10-06
 */
@Dao
interface AppDao {

    //User table operations
    @Insert
    fun insertAllUser(vararg users: User)

    @Delete
    fun deleteUsers(vararg users: User)

    @Query("SELECT COUNT(*) FROM User")
    fun getUserCount(): Int

    @Query("DELETE FROM User")
    fun delAllUsers(): Int

    @Query("SELECT * FROM User")
    fun getAllUsers(): List<User>

    //Bout Table operations
    @Insert
    fun insertBout(bout: Bout)

    @Delete
    fun deleteBout(bout: Bout)

    @Update
    fun updateBout(bout: Bout)

    @Query("SELECT * FROM Bout")
    fun getAllBouts(): List<Bout>

    @Query("DELETE FROM Bout")
    fun delAllBout(): Int

    //Facilitator table operations
    @Insert
    fun insertFacilitator(facilitator: Facilitator)

    @Delete
    fun deleteFacilitator(facilitator: Facilitator)

    @Update
    fun updateFacilitator(facilitator: Facilitator)

    @Query("SELECT * FROM Facilitator")
    fun getAllFacils(): List<Facilitator>

    @Query("DELETE FROM Facilitator")
    fun delAllFacils()

    //Game table ops
    @Insert
    fun insertGame(game: Game)

    @Delete
    fun deleteGame(game: Game)

    @Update
    fun updateGame(game: Game)

    @Query("SELECT * FROM Game")
    fun getAllGame(): List<Game>

    @Query("DELETE FROM Facilitator")
    fun delAllGame()

    //Main Claim Table ops
    @Insert
    fun insertMainClaim(mainClaim: MainClaim)

    @Delete
    fun deleteMainClaim(mainClaim: MainClaim)

    @Update
    fun updateMainClaim(mainClaim: MainClaim)

    @Query("SELECT * FROM MainClaim")
    fun getAllMainClaim(): List<MainClaim>

    @Query("DELETE FROM Facilitator")
    fun delAllMainClaim()

    //Player Table Ops
    @Insert
    fun insertPlayer(player: Player)

    @Delete
    fun deletePlayer(player: Player)

    @Update
    fun updatePlayer(player: Player)

    @Query("SELECT * FROM Player")
    fun getAllPlayer(): List<Player>

    @Query("DELETE FROM Player")
    fun delAllPlayer()

    //Rip Table Ops
    @Insert
    fun insertRip(rip: Rip)

    @Delete
    fun deleteRip(rip: Rip)

    @Update
    fun updateRip(rip: Rip)

    @Query("SELECT * FROM Rip")
    fun getAllRip(): List<Rip>

    @Query("DELETE FROM Rip")
    fun delAllRip()

    //Vote Table Ops
    @Insert
    fun insertVote(vote: Vote)

    @Delete
    fun deleteRip(vote: Vote)

    @Update
    fun updateRip(vote: Vote)

    @Query("SELECT * FROM Vote")
    fun getAllVote(): List<Vote>

    @Query("DELETE FROM Vote")
    fun delAllVote()

}