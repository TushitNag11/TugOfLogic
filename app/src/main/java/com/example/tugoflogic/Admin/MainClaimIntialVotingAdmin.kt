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
import com.example.tugoflogic.models.EVoteType
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


        var voteService = VoteService(this)

        voteService.voteResult.observe(this, Observer {
            // listen and render vote results
            println("Vote Result: " + it)
            var yes = it.split('|')[0];
            var no = it.split('|')[1];
            tvTrueVote.setText("Yes: " + yes)
            tvFalseVote.setText("No: " + yes)
        })

        startVotingBtn.setOnClickListener {
            var socketService = SocketService(this)

            socketService.sendMessage(ESocket.VOTE_MAINCLAIM1.value + "|" + EVoteType.MCI.value)

            socketService.message.observe(this, Observer { ms ->
                ms?.let {
                    // test
                    Toast.makeText(
                        this,
                        it,
                        Toast.LENGTH_SHORT
                    ).show();


                    var message = it.split('|')[0];
//                    var id = it.split('|')[1];

                    if (ESocket.NEW_VOTE_MAINCLAIM1_COMING.value.equals(message)) {
                        voteService.getVote(
                            sharedPref.getInt("GAME_ID", 0).toString(),
                            EVoteType.MCI.value.toString()
                        );
                    }
                }
            })

        }


    }
}