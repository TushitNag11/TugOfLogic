package com.example.tugoflogic.User

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.*
import com.example.tugoflogic.Service.Helper.Companion.GetUrl
import java.io.InputStream

/**
 *
 * @author Tushit Nag Kanuri
 * @version 1.0
 * @since   2020-10-06
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signupButton = findViewById<Button>(R.id.signupBtn)
        signupButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.loginBtn)
        loginButton.setOnClickListener {

            val intent = Intent(this, GameidActivity::class.java)
            startActivity(intent)
        }

        var userService = UserService();
        val s: String? = GetUrl(this)
        userService.findAll(s + "users", this);


//        var gameService = GameService();
//        gameService.findAll("http://54.225.179.78:5000/games", this);
//
//        var mainClaimService = MainClaimService();
//        mainClaimService.findAll("http://54.225.179.78:5000/mainclaims", this);
//
//        var playerService = PlayerService();
//        playerService.findAll("http://54.225.179.78:5000/players", this);
//
//        var ripService = RipService();
//        ripService.findAll("http://54.225.179.78:5000/rips", this);
//
//        var teacherService = TeacherService();
//        teacherService.findAll("http://54.225.179.78:5000/teachers", this);
//
//        var voteService = VoteService();
//        voteService.findAll("http://54.225.179.78:5000/votes", this);

    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Checks the orientation of the screen
        if (newConfig.orientation === Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show()
        } else if (newConfig.orientation === Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show()
        }
    }
}