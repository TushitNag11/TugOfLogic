package com.example.tugoflogic.User

import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tugoflogic.Admin.AdminActivity
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.*
import com.example.tugoflogic.Service.Helper.Companion.GetUrl
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.EVoteType
import kotlinx.android.synthetic.main.activity_main.*
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

//        val signupButton = findViewById<Button>(R.id.signupBtn)
//        signupButton.setOnClickListener {
//            val intent = Intent(this, SignupActivity::class.java)
//            startActivity(intent)
//        }

        this.getSharedPreferences("com.example.tugoflogic.Admin", 0).edit().clear().commit()
        this.getSharedPreferences("com.example.tugoflogic.User", 0).edit().clear().commit()

        val ADMIN = "admin";
        val loginButton = findViewById<Button>(R.id.loginBtn)
        loginButton.setOnClickListener {
            if (!emailTexbox.text.isEmpty() && !signupPassword.text.isEmpty()) {
                var userService = UserService(this);

                // login
                val username = emailTexbox.text.toString();
                var password = signupPassword.text.toString();
                userService.findAll()
                userService.listLiveData.observe(this, Observer { ms ->
                    ms?.let {
                        println(it.toTypedArray().toString());
                        var user = it;
                        val found = user.find {
                            it.username.equals(username) && it.password
                                .equals(password)
                        }
                        if (found != null) {
                            if (found.username.toString().equals(ADMIN)) {
                                // goto admin page
                                val intent = Intent(this, AdminActivity::class.java)
                                startActivity(intent)
                            } else {
                                // goto user page
                                // check user login
                                var userID = found._id
                                val sharedPref: SharedPreferences =
                                    this.getSharedPreferences("com.example.tugoflogic.User", 0)

                                val editor = sharedPref.edit()
                                editor.putInt("USER_ID", userID.toInt())
                                editor.apply()

                                val intent = Intent(this, GameidActivity::class.java)
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Username or password is wrong",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }

                })

            } else {
                Toast.makeText(
                    applicationContext,
                    "Username and password are required",
                    Toast.LENGTH_LONG
                ).show()
            }

        }


//        val s: String? = GetUrl(this)
//        var userService = UserService();
//        userService.findAll( this);


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

        var socketService = SocketService(this);

        socketService.message.observe(this, Observer { ms ->
            ms?.let {
                Toast.makeText(
                    this,
                    it,
                    Toast.LENGTH_SHORT
                ).show();
            }
        })

//        socketService.sendMessage(ESocket.SHOW_MAINCLAIM);
//        println("vote type " + EVoteType.MCI.value);
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