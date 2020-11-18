package com.example.tugoflogic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ReasonInPlay_User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reason_in_play__user)

        val submitBtn = findViewById<Button>(R.id.ripSubmitBtn)
        submitBtn.setOnClickListener {

            val intent = Intent(this, ReasonInPlayDisplay_User::class.java)
            startActivity(intent)
        }
    }
}