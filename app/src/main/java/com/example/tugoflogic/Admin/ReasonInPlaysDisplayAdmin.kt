package com.example.tugoflogic.Admin

import android.content.Intent
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
import com.example.tugoflogic.models.EVoteType
import com.example.tugoflogic.models.Rip
import com.example.tugoflogic.models.User
import com.example.tugoflogic.models.Vote
import kotlinx.android.synthetic.main.activity_r_i_p_debate_selection_admin.*
import kotlinx.android.synthetic.main.activity_reason_in_plays_display_admin.*

class ReasonInPlaysDisplayAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reason_in_plays_display_admin)

        var ws = SocketService(this)

        var users: List<User> = emptyList();
        var rips: List<Rip>;

        var ripService = RipService(this);

        ripService.listLiveData.observe(this, Observer {
            rips = it;
            for (r in rips) {
                r.user = users.find { x -> x._id == r.user_id }
            }
            rvRip.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            val adapterForRecycler = RIPVotingAdapter(this, rips, ripService, ws)
            rvRip.adapter = adapterForRecycler
        })


        var userService = UserService(this);

        userService.listLiveData.observe(this, Observer {
            users = it;
            ripService.findAll()
        })

        userService.findAll()

        ws.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

//        btnNext.setOnClickListener(View.OnClickListener {
//            val intent = Intent(this, ReasonInPlaysDisplayAdmin::class.java)
//            startActivity(intent)
//        })
    }


}