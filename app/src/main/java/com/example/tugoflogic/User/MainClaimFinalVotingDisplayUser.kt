package com.example.tugoflogic.User

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.Service.VoteService
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.EVoteType
import com.example.tugoflogic.models.Vote
import kotlinx.android.synthetic.main.activity_main_claim.*
import kotlinx.android.synthetic.main.activity_main_claim_final_voting_user.*
import kotlinx.android.synthetic.main.activity_main_claim_intial_voting_admin.*

class MainClaimFinalVotingDisplayUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_claim_final_voting_user)


        var vote = Vote
        var voteFlag = 0
        var voteID = ""
        val sharedPref: SharedPreferences =
            this.getSharedPreferences("com.example.tugoflogic.User", 0)
        val gameID = sharedPref.getInt("GAME_ID", 0).toString().toInt()
        var userID = sharedPref.getInt("USER_ID", 0).toString().toInt()
        var mainclaimId = sharedPref.getInt("MAINCLAIM_ID", 0).toString().toInt()



        var mainClaimService = MainClaimService(this)

        radioGroupFinal.visibility = View.INVISIBLE
        submitVote.visibility = View.INVISIBLE


        mainClaimService.listLiveData.observe(this, Observer { ms ->
            ms?.let {
                var found = it.find { x -> x._id.equals(mainclaimId.toString()) }
                if (found != null) {
                    tvMCDisplay.setText(found.statement.toString())
                }
            }
        })

        mainClaimService.findAll()
        // ws listening
        var socketService = SocketService(this)
        socketService.message.observe(this, Observer { ms ->
            ms?.let {
                var message = it.split('|')[0]
                var id = it.split('|')[1]

                Toast.makeText(
                    this,
                    it,
                    Toast.LENGTH_SHORT
                ).show()

                println(it)
                // vote
                if (ESocket.VOTE_MAINCLAIM2.value.equals(message)) {
                    println("Start Vote:" + id)
                    radioGroupFinal.visibility = View.VISIBLE
                    submitVote.visibility = View.VISIBLE
                }


            }



        })


        radioGroupFinal.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                if (radio.isChecked) {

                    if (radio.text.equals("Agree")) {
                        voteFlag = 1
                    } else {
                        voteFlag = 0
                    }


                } else {
                    Toast.makeText(this, "Please Vote", Toast.LENGTH_LONG)
                        .show()
                }
            })

        var voteService = VoteService(this)

        submitVote.setOnClickListener {


            voteService.findAll()


        }

        voteService.listLiveData.observe(this, Observer {

            voteID = (it.size + 1).toString()

            voteService.create(voteID, gameID, userID, EVoteType.MCF.value, voteFlag, mainclaimId)


        })

        voteService.newVote.observe(this, Observer {

            socketService.sendMessage(ESocket.NEW_VOTE_MAINCLAIM2_COMING.value + "|" + EVoteType.MCF.value)
        })



        submitVote2.setOnClickListener {

            val intent = Intent(this, finalResultsDisplay::class.java)
            startActivity(intent)
        }

    }
}