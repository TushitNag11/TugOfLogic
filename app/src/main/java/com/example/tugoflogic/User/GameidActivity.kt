package com.example.tugoflogic.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.GameService
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * @author Tushit Nag Kanuri
 * @version 1.0
 * @since   2020-10-06
 */
class GameidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameid)


        val enterBtn = findViewById<Button>(R.id.enterBtn)

        enterBtn.setOnClickListener {
            if (!emailTexbox.text.isEmpty()) {
                var gameService = GameService(this);

                var gameId = emailTexbox.text.toString()
                gameService.findAll()

                gameService.listLiveData.observe(this, Observer { ms ->
                    ms?.let {
                        println(it.toTypedArray().toString());

                        val found = it.find {
                            it._id.toString().equals(gameId)
                        }

                        if (found != null) {
                            val intent = Intent(this, MainClaimActivity::class.java)
                            println("GAME ID: " + gameId)
                            intent.putExtra("gameId", gameId)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Game does not exist!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })


            }
        }

        fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu, menu)
            return true
        }

        fun onOptionsItemSelected(item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.accountBtn -> {

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.logoutBtn -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> super.onOptionsItemSelected(item)
            }
        }
    }
}