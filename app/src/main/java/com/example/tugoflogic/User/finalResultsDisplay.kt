package com.example.tugoflogic.User

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.VoteService
import com.example.tugoflogic.models.EVoteType
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main_claim_intial_voting_admin.*
import kotlinx.android.synthetic.main.activity_mainclaim_final_results_display_user.*


class finalResultsDisplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainclaim_final_results_display_user)

        var sharedPref =
            this.getSharedPreferences("com.example.tugoflogic.Admin", 0)
        var gameID = sharedPref.getInt("GAME_ID", 0).toString()

        if(gameID.equals("0")){
            sharedPref =
                this.getSharedPreferences("com.example.tugoflogic.User", 0)
            gameID = sharedPref.getInt("GAME_ID", 0).toString()
        }

        var voteService = VoteService(this)

        voteService.voteResult.observe(this, Observer {
            // listen and render vote results
            println("Vote Result: " + it)
            var yes = it.split('|')[0].toFloat();
            var no = it.split('|')[1].toFloat();

            setBarChart(yes, no)
        })

        voteService.getVote(gameID, EVoteType.MCF.value.toString());


    }

    fun setBarChart(yes: Float, no: Float) {


        val entries1 = ArrayList<BarEntry>()
        entries1.add(BarEntry(1f, yes))
        val entries2 = ArrayList<BarEntry>()
        entries2.add(BarEntry(2f, no))

        val barDataSet1 = BarDataSet(entries1, "Yes")
        barDataSet1.color = ColorTemplate.COLORFUL_COLORS.get(0);
        val barDataSet2 = BarDataSet(entries2, "No")
        barDataSet2.color = ColorTemplate.COLORFUL_COLORS.get(1);

        val data = BarData(barDataSet1, barDataSet2)
        barChart.data = data // set the data and list of lables into chart
        barChart.legend.isEnabled = true;

        barChart.animateY(1000)
    }
}