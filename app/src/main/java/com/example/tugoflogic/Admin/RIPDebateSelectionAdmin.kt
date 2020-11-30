package com.example.tugoflogic.Admin

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.Service.UserService
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.Rip
import com.example.tugoflogic.models.User
import kotlinx.android.synthetic.main.activity_r_i_p_debate_selection_admin.*

class RIPDebateSelectionAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_r_i_p_debate_selection_admin)

        var users: List<User> = emptyList();
        var rips: List<Rip> = emptyList();

        var ripService = RipService(this);

        val sharedPref: SharedPreferences =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)
        val gameID = sharedPref.getInt("GAME_ID", 0).toString().toInt()

        reasoninplaysDebateAdmin.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapterForRecycler = RIPAdapter(this, rips, ripService)
        reasoninplaysDebateAdmin.adapter = adapterForRecycler

        ripService.listLiveData.observe(this, Observer {
            rips = it.filter { x -> x.game_id == gameID };
            println(rips)
            for (r in rips) {
                r.user = users.find { x -> x._id == r.user_id }
            }
            adapterForRecycler.update(rips)
        })


        var userService = UserService(this);

        userService.listLiveData.observe(this, Observer {
            users = it;
            ripService.findAll()
        })

        userService.findAll()

        btnNext.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ReasonInPlaysDisplayAdmin::class.java)
            startActivity(intent)
        })

        // listen
        var socketService = SocketService(this)
        socketService.message.observe(this, Observer { ms ->
            ms?.let {
                var message = it.split('|')[0];
//                var id = it.split('|')[1]
                if (message.equals(ESocket.RIP_COMING.value)) {
                    ripService.findAll()
                }
            }
        })
    }
}