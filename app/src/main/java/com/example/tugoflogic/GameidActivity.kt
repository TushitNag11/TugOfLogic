package com.example.tugoflogic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

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

        val logoutBtn = findViewById<ImageButton>(R.id.logutBtn)

        logoutBtn.setOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        val enterBtn = findViewById<Button>(R.id.enterBtn)

        enterBtn.setOnClickListener {

            val intent = Intent(this,MainClaimActivity::class.java)
            startActivity(intent)

        }
    }
}