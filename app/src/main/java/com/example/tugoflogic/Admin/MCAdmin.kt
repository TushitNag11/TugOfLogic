package com.example.tugoflogic.Admin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
import kotlinx.android.synthetic.main.activity_m_c_admin.*


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
//                if()

                var mainClaimStatement = tvMainClaim.getText().toString()

                mainClaimService.create(mainClaimID,1,mainClaimStatement)

                println(mainClaimID.toString());

            })
        })



    }
}