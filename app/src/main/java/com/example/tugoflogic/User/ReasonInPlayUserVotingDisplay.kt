package com.example.tugoflogic.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugoflogic.Admin.RIPVoteResultDisplayAdapter
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.Service.VoteService
import com.example.tugoflogic.models.*
import kotlinx.android.synthetic.main.activity_reason_in_play_user_voting_display.*

class ReasonInPlayUserVotingDisplay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reason_in_play_user_voting_display)

        var sharedPref =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)

        var gameID = sharedPref.getInt("GAME_ID", 0).toString()
        var ws = SocketService(this)
        var rips: List<Rip> = emptyList();
        var votes: List<Vote> = emptyList();

        var ripService = RipService(this);
        ripvotingTable.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,false)
        val adapter = RIPVoteResultDisplayAdapter(this,rips,ripService,ws)
        ripvotingTable.adapter = adapter
        ripService.listLiveData.observe(this, Observer {
            rips = it.filter  { x -> x.game_id == gameID.toInt() && x.status.equals(ERipStatus.ACCEPTED) };
            for (r in rips) {
                r.yes = votes.filter { x ->
                    x.statement_id.toString().equals(r._id) && x.vote_flag == 1
                }.size
                r.no = votes.filter { x ->
                    x.statement_id.toString().equals(r._id) && x.vote_flag == 0
                }.size
            }
            adapter.update(rips)
        })

        var voteService = VoteService(this);
        voteService.listLiveData.observe(this, Observer {
            votes = it.filter { x -> x.vote_type_id == EVoteType.RIP.value };
            Thread.sleep(500)
            ripService.findAll()
        })

    }


}