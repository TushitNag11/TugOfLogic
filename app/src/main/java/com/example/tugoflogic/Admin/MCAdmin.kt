package com.example.tugoflogic.Admin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
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
                    mainClaimService.create(mainClaimID, 1, mainClaimStatement)

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


    }
}