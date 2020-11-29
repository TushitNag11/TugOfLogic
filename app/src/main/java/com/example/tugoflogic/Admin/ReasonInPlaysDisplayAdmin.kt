package com.example.tugoflogic.Admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.VoteService
import com.example.tugoflogic.models.EVoteType

class ReasonInPlaysDisplayAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reason_in_plays_display_admin)

        var sharedPref =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)
        var gameID = sharedPref.getInt("GAME_ID", 0).toString()

        var voteService = VoteService(this)

        voteService.listLiveData.observe(this, Observer {
            // listen and render vote results
            println(it)

            // filter by gameId
            var list = it.filter { x -> x.game_id == 1 && x.vote_type_id == EVoteType.RIP.value };
            println(list.size)
        })

        voteService.findAll()
    }


}