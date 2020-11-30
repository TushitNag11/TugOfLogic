package com.example.tugoflogic.User

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.Service.VoteService
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.EVoteType
import com.example.tugoflogic.models.Vote
import kotlinx.android.synthetic.main.activity_main_claim.*
import kotlinx.android.synthetic.main.activity_reason_in_play_display__user.*

class ReasonInPlayDisplay_User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reason_in_play_display__user)

        var socketService = SocketService(this);
        var ripService = RipService(this);
        var ripId = "0";
        var voteFlag = 0
        var voteID = ""
        var sharedPref: SharedPreferences =
            this.getSharedPreferences("com.example.tugoflogic.User", 0)
        var gameID = sharedPref.getInt("GAME_ID", 0).toString().toInt()
        var userID = sharedPref.getInt("USER_ID", 0).toString().toInt()


        radioGroupRIP.visibility = View.INVISIBLE

        ripService.listLiveData.observe(this, Observer {
            var rip = it.find { x -> x._id.equals(ripId) };
            reasoninplayDisplay.setText(rip?.statement)
        })

        socketService.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            var message = it.split('|')[0]
            ripId = it.split('|')[1]

            when (message) {
                ESocket.DEBATE_RIP.value -> {
                    // load rip statement
                    ripService.findAll()
                }
                ESocket.VOTE_RIP.value -> {
                    // TODO start vote
                    radioGroupRIP.visibility = View.VISIBLE


                }
            }
        })

        radioGroupRIP.setOnCheckedChangeListener(
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


        submitRIPVote.setOnClickListener {

            voteService.findAll()


        }



        voteService.listLiveData.observe(this, Observer {

            voteID = (it.size + 1).toString()

            voteService.create(voteID,gameID, userID, EVoteType.RIP.value, voteFlag, ripId.toInt())


        })

        voteService.newVote.observe(this, Observer {

            socketService.sendMessage(ESocket.VOTE_RIP_COMING.value + "|" + EVoteType.RIP.value)


        })

    nextBTNRIP.setOnClickListener {
        val intent = Intent(this, MainClaimFinalVotingDisplayUser::class.java)
        startActivity(intent)
    }

    }

}