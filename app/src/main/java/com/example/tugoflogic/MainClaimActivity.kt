package com.example.tugoflogic

import android.content.Intent
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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

        val submitBtn = findViewById<Button>(R.id.submitBtn)
        submitBtn.setOnClickListener {

            val intent = Intent(this, ReasonInPlay_User::class.java)
            startActivity(intent)
        }

           }
}