package com.example.tugoflogic.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.example.tugoflogic.R

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

            val intent = Intent(this, MainClaimActivity::class.java)
            startActivity(intent)

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
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