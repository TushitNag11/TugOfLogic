package com.example.tugoflogic.User

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.MainClaimService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.models.ESocket
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

        var mainclaimId = "";

        val sharedPref: SharedPreferences =
            this.getSharedPreferences("com.example.tugoflogic.User", 0)
        val gameID = sharedPref.getInt("GAME_ID", 0).toString().toInt();

        println("MC gameId: " + gameID)

        val submitBtn = findViewById<Button>(R.id.submitBtn)
        submitBtn.setOnClickListener {

            val intent = Intent(this, ReasonInPlay_User::class.java)
            startActivity(intent)
        }

        var mainClaimService = MainClaimService(this)

        radioGroup.visibility = View.INVISIBLE
        submitBtn.visibility = View.INVISIBLE


        // ws listening
        var socketService = SocketService(this);
        socketService.message.observe(this, Observer { ms ->
            ms?.let {
                var message = it.split('|')[0];
                var id = it.split('|')[1];

                Toast.makeText(
                    this,
                    it,
                    Toast.LENGTH_SHORT
                ).show();

                println(it);

                // new main claim
                if (ESocket.SHOW_MAINCLAIM.value.equals(message)) {
                    // test


                    mainclaimId = id;

                    mainClaimService.findAll();
                }

                // vote
                if (ESocket.VOTE_MAINCLAIM1.value.equals(message)) {
                    println("Start Vote:" + id);
                }


            }
        })

        mainClaimService.listLiveData.observe(this, Observer { ms ->
            ms?.let {
                var found = it.find { x -> x._id.equals(mainclaimId) }
                if (found != null) {
                    mainclaimDisplay.setText(found.statement.toString());
                }
            }
        })

    }
}