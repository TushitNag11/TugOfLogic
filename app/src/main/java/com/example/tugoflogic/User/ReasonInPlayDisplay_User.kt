package com.example.tugoflogic.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.models.ESocket
import kotlinx.android.synthetic.main.activity_reason_in_play_display__user.*

class ReasonInPlayDisplay_User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reason_in_play_display__user)

        var socketService = SocketService(this);
        var ripService = RipService(this);
        var ripId = "0";

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

                }
            }
        })

        val nextBtn = findViewById<Button>(R.id.next)
        nextBtn.setOnClickListener {

            val intent = Intent(this, ReasonInPlayUserVotingDisplay::class.java)
            startActivity(intent)
        }

    }

}