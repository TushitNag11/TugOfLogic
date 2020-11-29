package com.example.tugoflogic.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.Service.UserService
import com.example.tugoflogic.models.Rip
import com.example.tugoflogic.models.User
import kotlinx.android.synthetic.main.activity_r_i_p_debate_selection_admin.*

class RIPDebateSelectionAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_r_i_p_debate_selection_admin)

        var users: List<User> = emptyList();
        var rips: List<Rip>;

        var ripService = RipService(this);

        ripService.listLiveData.observe(this, Observer {
            rips = it;
            for (r in rips) {
                r.user = users.find { x -> x._id == r.user_id }
            }
            reasoninplaysDebateAdmin.layoutManager =
                LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            val adapterForRecycler = RIPAdapter(this, rips, ripService)
            reasoninplaysDebateAdmin.adapter = adapterForRecycler
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
    }
}