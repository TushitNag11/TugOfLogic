package com.example.tugoflogic.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.Service.VoteService
import com.example.tugoflogic.models.ERipStatus
import com.example.tugoflogic.models.EVoteType
import com.example.tugoflogic.models.Vote
import kotlinx.android.synthetic.main.activity_rip_voting_results_admin.*

class ripVotingResultsAdmin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rip_voting_results_admin)

        var sharedPref =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)
        var gameID = sharedPref.getInt("GAME_ID", 0).toString()

        var voteList: List<Vote> = emptyList<Vote>();


        var ripService = RipService(this);

        ripService.listLiveData.observe(this, Observer {
            // listen and render vote results
            println(it)
            var ripList =
                it.filter { x -> x.game_id == gameID.toInt() && x.status.equals(ERipStatus.ACCEPTED) };
            println(ripList.size);
            println(voteList.size);
            for (v: Vote in voteList) {
                println(v._id)
                var foundRip = ripList.find { y -> y._id.equals(v.statement_id.toString()) }
                if (foundRip != null) {
                    v.statement = foundRip.statement;
                }
            }
            var results = ""
            var groups = voteList.groupBy { x -> x.statement_id }
            println(groups);
            for (g: Any in groups.keys) {
                var v = groups.get(g)?.get(0);
                if (v != null) {
                    results += "#" + (voteList.indexOf(v) + 1) + "\n"
                    results += v.statement + "\n"
                    val yes =
                        voteList.filter { x -> x.statement_id == v.statement_id && x.vote_flag == 1 }.size
                    val no =
                        voteList.filter { x -> x.statement_id == v.statement_id && x.vote_flag == 0 }.size
                    results += "YES: " + yes + "\n"
                    results += "NO: " + no + "\n\n\n\n"
                }

            }
            tvRipResults.setText(results)
        })

        var voteService = VoteService(this)

        voteService.listLiveData.observe(this, Observer {
            // listen and render vote results
            println(it)
            if (it.size > 0) {
                // filter by gameId
                voteList =
                    it.filter { x -> x.game_id == gameID.toInt() && x.vote_type_id == EVoteType.RIP.value };
                println(voteList.size)

                ripService.findAll();
            }

        })

        voteService.findAll()

        nextBtns.setOnClickListener {
            val intent = Intent(this, AdminMainClaimFinalVotingActivity::class.java)
            startActivity(intent)
        }

    }


}