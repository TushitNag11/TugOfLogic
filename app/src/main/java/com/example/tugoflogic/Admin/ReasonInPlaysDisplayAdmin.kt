package com.example.tugoflogic.Admin

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.Service.UserService
import com.example.tugoflogic.Service.VoteService
import com.example.tugoflogic.models.*
import kotlinx.android.synthetic.main.activity_r_i_p_debate_selection_admin.*
import kotlinx.android.synthetic.main.activity_reason_in_plays_display_admin.*

class ReasonInPlaysDisplayAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reason_in_plays_display_admin)

        var ws = SocketService(this)

        var users: List<User> = emptyList();
        var rips: List<Rip> = emptyList();
        var votes: List<Vote> = emptyList();

        var ripService = RipService(this);

        val sharedPref: SharedPreferences =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)
        val gameID = sharedPref.getInt("GAME_ID", 0).toString().toInt()

        rvRip.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapterForRecycler = RIPVotingAdapter(this, rips, ripService, ws)
        rvRip.adapter = adapterForRecycler

        ripService.listLiveData.observe(this, Observer {
            rips = it.filter { x -> x.game_id == gameID };
            for (r in rips) {
                r.user = users.find { x -> x._id == r.user_id }
                r.yes = votes.filter { x ->
                    x.statement_id.toString().equals(r._id) && x.vote_flag == 1
                }.size
                r.no = votes.filter { x ->
                    x.statement_id.toString().equals(r._id) && x.vote_flag == 0
                }.size
            }
            adapterForRecycler.update(rips)
        })

        var voteService = VoteService(this);
        voteService.listLiveData.observe(this, Observer {
            votes = it.filter { x -> x.vote_type_id == EVoteType.RIP.value };
            ripService.findAll()
        })

        var userService = UserService(this);

        userService.listLiveData.observe(this, Observer {
            users = it;
            voteService.findAll()
        })

        userService.findAll()

        ws.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

            var message = it.split('|')[0];
//                var id = it.split('|')[1]
            if (message.equals(ESocket.VOTE_RIP_COMING.value)) {

            }
        })

//        btnNext.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, ReasonInPlaysDisplayAdmin::class.java)
//            startActivity(intent)
//        })
    }


}