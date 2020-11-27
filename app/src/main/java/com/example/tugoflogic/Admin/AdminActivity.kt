package com.example.tugoflogic.Admin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.GameService
import kotlinx.android.synthetic.main.activity_admin.*


class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        createGame.setOnClickListener(View.OnClickListener {
            var gameService = GameService(this);

            gameService.findAll();
            gameService.listLiveData.observe(this, Observer {
                var nextId = it.size + 1;
                gameService.create(nextId);
                gameService.newGameLiveData.observe(this, Observer {
                    // save game id into preference
                    val sharedPref: SharedPreferences = this.getSharedPreferences("com.example.tugoflogic.Admin",0)
                    val editor = sharedPref.edit()
                    var newID =  it.toInt()
                    editor.putInt("GAME_ID", newID)
                    editor.apply()
                    println("GameID=========="+sharedPref.getInt("GAME_ID",0))
                    // move to mainclain screen
                    val intent = Intent(this, MCAdmin::class.java)
                    startActivity(intent)
                })
            })
        })
    }
}