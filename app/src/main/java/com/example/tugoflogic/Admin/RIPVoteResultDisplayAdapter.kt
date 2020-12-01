package com.example.tugoflogic.Admin

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.Service.SocketService
import com.example.tugoflogic.inflate
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.Rip
import kotlinx.android.synthetic.main.ripvotingdisplaylayout.view.*
import kotlinx.android.synthetic.main.ripvotingviewadmin.view.*

class RIPVoteResultDisplayAdapter(
    var context: Context,
    var rips: List<Rip>?,
    var service: RipService,
    val ws: SocketService
) :
    RecyclerView.Adapter<RIPVoteResultDisplayAdapter.ResultHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RIPVoteResultDisplayAdapter.ResultHolder {
        val inflatedView = parent.inflate(R.layout.ripvotingdisplaylayout, false)
        return ResultHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ResultHolder, position: Int) {
        val item = rips?.get(position)
        Log.i("RecyclerAdapter", "onBindViewHolder")
        holder.bindFlag(item, context, service, ws)
    }

    override fun getItemCount(): Int = rips!!.size ?: 0

    fun update(modelList: List<Rip>) {
        this.rips = modelList
        this.notifyDataSetChanged()
    }

    class ResultHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var rip: Rip? = null


        init {
//            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {

            Log.d("RecyclerView", rip?._id.toString())
        }


        fun bindFlag(rip: Rip?, context: Context, service: RipService, ws: SocketService) {
            Log.i("RecyclerAdapter", "bindFlag")
            this.rip = rip
            var tv = view.resultsRipdisplay as TextView;
            tv?.setText(this.rip?.statement);
            view.agreePercentage.setText("" + this.rip?.yes)
            view.disagreePercentage.setText("" + this.rip?.no)


        }
    }


}