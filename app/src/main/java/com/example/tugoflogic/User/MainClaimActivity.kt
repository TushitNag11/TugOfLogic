package com.example.tugoflogic.User

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.Service.VoteService
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.EVoteType
import com.example.tugoflogic.models.Vote
import kotlinx.android.synthetic.main.activity_main_claim.*

/**
 *
 * @author Tushit Nag Kanuri
 * @version 1.0
 * @since   2020-10-06
 */
class MainClaimActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_claim)

        var mainclaimId = ""
        var vote = Vote
        var voteFlag = 0
        var voteID = ""
        val sharedPref: SharedPreferences =
            this.getSharedPreferences("com.example.tugoflogic.User", 0)
        val gameID = sharedPref.getInt("GAME_ID", 0).toString().toInt()

        println("MC gameId: " + gameID)

        val submitBtn = findViewById<Button>(R.id.voteSubmitBtn)
        submitBtn.setOnClickListener {

            val intent = Intent(this, ReasonInPlay_User::class.java)
            startActivity(intent)
        }

        var mainClaimService = MainClaimService(this)

        radioGroup.visibility = View.INVISIBLE
        submitBtn.visibility = View.INVISIBLE


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

                // new main claim
                if (ESocket.SHOW_MAINCLAIM.value.equals(message)) {
                    // test


                    mainclaimId = id

                    mainClaimService.findAll()
                }

                // vote
                if (ESocket.VOTE_MAINCLAIM1.value.equals(message)) {
                    println("Start Vote:" + id)
                    radioGroup.visibility = View.VISIBLE
                    submitBtn.visibility = View.VISIBLE
                }


            }
            mainClaimService.listLiveData.observe(this, Observer { ms ->
                ms?.let {
                    var found = it.find { x -> x._id.equals(mainclaimId) }
                    if (found != null) {
                        mainclaimDisplay.setText(found.statement.toString())
                    }
                }
            })

        })
        radioGroup.setOnCheckedChangeListener(
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                val radio: RadioButton = findViewById(checkedId)
                if (radio.isChecked) {

                    if (radio.text.equals("Agree")) {
                        voteFlag = 1
                    } else {
                        voteFlag = 0
                    }


                } else {
                    Toast.makeText(this, "Please Vote", LENGTH_LONG)
                        .show()
                }
            })

        var voteService = VoteService(this)

        voteSubmitBtn.setOnClickListener {






            voteService.findAll()



        }

        voteService.listLiveData.observe(this, Observer {

            voteID = (it.size + 1).toString()

            voteService.create(voteID, gameID, EVoteType.MCI.value, voteFlag, mainclaimId.toInt())


        })

            voteService.newVote.observe(this, Observer {

                socketService.sendMessage(ESocket.NEW_VOTE_MAINCLAIM1_COMING.value+ "|" + EVoteType.MCI.value)
            })



    }

}