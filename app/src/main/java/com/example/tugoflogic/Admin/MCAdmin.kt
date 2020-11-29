package com.example.tugoflogic.Admin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.models.ESocket
import kotlinx.android.synthetic.main.activity_m_c_admin.*
import kotlinx.android.synthetic.main.activity_main_claim.*


class MCAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_c_admin)

        val sharedPref: SharedPreferences =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)
        val gameID = sharedPref.getInt("GAME_ID", 0).toString().toInt();
        println("Game ID ${gameID}")
        tvGameId.setText(gameID.toString())

        var mainClaimService = MainClaimService(this)


        mcSubmitBtn.setOnClickListener(View.OnClickListener {

            mainClaimService.findAll()
            mainClaimService.listLiveData.observe(this, Observer {

                var mainClaimID = it.size + 1
                //checking whether main claim is empty or not
//                if() the main claim is empty or not

                var mainClaimStatement = tvMainClaim.getText().toString()


                if (mainClaimStatement != "") {
                    mainClaimService.create(mainClaimID, gameID, mainClaimStatement)

                } else {
                    Toast.makeText(
                        applicationContext,
                        "Main Claim should not be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                println(mainClaimID.toString());

            })
        })

        mainClaimService.newMainClaimData.observe(this, Observer { ms ->
            ms?.let {
                if (it > 0) {
                    println("New Mainclaim: " + it);
                    // new mainclaim is created
                    // call ws to broadcast to users
                    var socketService = SocketService(this);
                    socketService.sendMessage(ESocket.SHOW_MAINCLAIM.value + "|" + it)
                    val sharedPref: SharedPreferences =
                        this.getSharedPreferences("com.example.tugofLogic.Admin", 0)
                    val editor = sharedPref.edit()
                    var newID = it.toString().toInt()
                    editor.putInt("MAINCLAIM_ID", newID)
                    editor.apply()
                    println("MainClaimID=======>"+sharedPref.getInt("MAINCLAIM_ID",0))
                    val intent = Intent(this, MainClaimIntialVotingAdmin::class.java)
                    startActivity(intent)
                }
            }
        })

        mcSubmitBtn2.setOnClickListener(View.OnClickListener {



        })
    }
}