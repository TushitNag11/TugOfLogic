package com.example.tugoflogic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class GameidActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameid)

        val logoutBtn = findViewById<ImageButton>(R.id.logutBtn)

        logoutBtn.setOnClickListener{

            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}