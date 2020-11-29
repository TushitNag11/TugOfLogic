package com.example.tugoflogic.Admin

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.Service.VoteService
import com.example.tugoflogic.models.ESocket
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

    startVotingBtn.setOnClickListener { View.OnClickListener {

       var socketService = SocketService(this)

        socketService.sendMessage(ESocket.VOTE_MAINCLAIM1.value + "|" + it)

        socketService.message.observe(this, Observer { ms ->
            ms?.let {
                // test
                Toast.makeText(
                    this,
                    it,
                    Toast.LENGTH_SHORT
                ).show();

               var voteService = VoteService()

                voteService.listLiveData.observe(this, Observer {

                  var voteFound =   it.find { x -> x.statement_id.equals(mainclaimID) }

                    if (voteFound != null) {

                            if(voteFound.vote_flag.equals(true))
                            {
                                var trueCounter = 0
                                trueCounter+ 1;
                            }

                        }

                })


            }
        })




    } }



    }
}