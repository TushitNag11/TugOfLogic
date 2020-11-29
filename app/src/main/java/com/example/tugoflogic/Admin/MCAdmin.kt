package com.example.tugoflogic.Admin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.MainClaim
import kotlinx.android.synthetic.main.activity_m_c_admin.*
import kotlinx.android.synthetic.main.activity_main_claim.*


class MCAdmin : AppCompatActivity() {
    var newID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m_c_admin)

        var sharedPref =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)

        // get gameID
        val gameID = sharedPref.getInt("GAME_ID", 0).toString().toInt();
        println("Game ID ${gameID}")
        tvGameId.setText(gameID.toString())

        var mainClaimService = MainClaimService(this)


        mcSubmitBtn.setOnClickListener(View.OnClickListener {

            mainClaimService.findAll()

        })

        mainClaimService.listLiveData.observe(this, Observer<List<MainClaim>> {

            var mainClaimID = it.size + 1

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



        mainClaimService.newMainClaimData.observe(this, Observer { ms ->
            ms?.let {
                if (it > 0) {
                    println("New Mainclaim: " + it);
                    // new mainclaim is created
                    // call ws to broadcast to users
                    var socketService = SocketService(this);
                    socketService.sendMessage(ESocket.SHOW_MAINCLAIM.value + "|" + it)

                    newID = it.toString().toInt()

                    Toast.makeText(
                        applicationContext,
                        "Main Claim is submitted",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        mcSubmitBtn2.setOnClickListener(View.OnClickListener {
            val editor = sharedPref.edit()

            editor.putInt("MAINCLAIM_ID", newID)
            editor.apply()
            println("MainClaimID=======>" + sharedPref.getInt("MAINCLAIM_ID", 0))
            val intent = Intent(this, MainClaimIntialVotingAdmin::class.java)
            startActivity(intent)
        })
    }
}