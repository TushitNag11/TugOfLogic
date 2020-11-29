package com.example.tugoflogic.User

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tugoflogic.R
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_mainclaim_final_results_display_user.*


class finalResultsDisplay : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainclaim_final_results_display_user)
        setBarChart()
    }

    fun setBarChart() {

        val entries1 = ArrayList<BarEntry>()
        entries1.add(BarEntry(1f, 2.toFloat()))
        val entries2 = ArrayList<BarEntry>()
        entries2.add(BarEntry(2f, 1.toFloat()))

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