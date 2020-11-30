package com.example.tugoflogic.User

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.models.ERipStatus
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.EVoteType
import kotlinx.android.synthetic.main.activity_reason_in_play__user.*

class ReasonInPlay_User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reason_in_play__user)


        var gameID = 0
        var ripID = ""
        var ripStatement=""


        //getting game ID from shared preferences

        var sharedPref: SharedPreferences = this.getSharedPreferences("com.example.tugoflogic.User",0)
        gameID = sharedPref.getInt("GAME_ID",0).toString().toInt()
        var userID = sharedPref.getInt("USER_ID", 0).toString().toInt()

        //creating service object
        var ripService = RipService(this)

        var submitBtn = findViewById<Button>(R.id.ripSubmitBtn)

        submitBtn.setOnClickListener {


            ripStatement = ripEditText.getText().toString()

            if(ripStatement == "")
            {
                Toast.makeText(this, "Please Enter Your Reason In Play", Toast.LENGTH_LONG)
                    .show()
            }
            else{
                ripService.findAll() // this call


            }

        }

        ripService.listLiveData.observe(this, Observer {


            ripID = (it.size+1).toString()
            ripService.create(ripID,gameID,userID,ripStatement,ERipStatus.PLAYED)


        })


        //creating a socketService

        var socketService = SocketService(this)

        ripService.newRIP.observe(this, Observer {

            socketService.sendMessage(ESocket.RIP_COMING.value)
        })


        ripSubmitBtn2.setOnClickListener {
            var intent = Intent(this, ReasonInPlayDisplay_User::class.java)
            startActivity(intent)
        }

    }
}