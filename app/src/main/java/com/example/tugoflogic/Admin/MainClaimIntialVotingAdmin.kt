package com.example.tugoflogic.Admin

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
import kotlinx.android.synthetic.main.activity_main_claim.*
import kotlinx.android.synthetic.main.activity_main_claim_intial_voting_admin.*

class MainClaimIntialVotingAdmin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_claim_intial_voting_admin)

        var sharedPref =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)
        var mainclaimID = sharedPref.getInt("MAINCLAIM_ID", 0).toString()

        println("MainClaim ID ---> $mainclaimID")

        var mainClaimService = MainClaimService(this)

        mainClaimService.findAll()

        mainClaimService.listLiveData.observe(this, Observer { ms ->
            ms?.let {

                var flag = it.find { x -> x._id.equals(mainclaimID) }

                if (flag != null) {

                    tvMainClaimDisplay.setText(flag.statement.toString())
                }


            }
        })



    }
}