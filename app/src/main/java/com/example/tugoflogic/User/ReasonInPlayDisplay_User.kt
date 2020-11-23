package com.example.tugoflogic.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.tugoflogic.R

class ReasonInPlayDisplay_User : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reason_in_play_display__user)


        val nextBtn = findViewById<Button>(R.id.next)
        nextBtn.setOnClickListener {

            val intent = Intent(this, ReasonInPlayUserVotingDisplay::class.java)
            startActivity(intent)
        }

    }
}