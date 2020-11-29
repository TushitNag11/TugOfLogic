package com.example.tugoflogic.Admin

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tugoflogic.R

class MainClaimIntialVotingAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_claim_intial_voting_admin)

        val sharedPref: SharedPreferences =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)

        val mainclaimID = sharedPref.getInt("MAINCLAIM_ID", 0).toString().toInt()

        println("MainClaim ID ---> $mainclaimID")
    }
}