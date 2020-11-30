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
import com.example.tugoflogic.models.ERipStatus
import com.example.tugoflogic.models.ESocket
import com.example.tugoflogic.models.EVoteType
import com.example.tugoflogic.models.Rip
import kotlinx.android.synthetic.main.ripvotingviewadmin.view.*

class RIPVotingAdapter(
    val context: Context,
    val rips: List<Rip>?,
    val service: RipService,
    val ws: SocketService
) :
    RecyclerView.Adapter<RIPVotingAdapter.VoteHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RIPVotingAdapter.VoteHolder {
        val inflatedView = parent.inflate(R.layout.ripvotingviewadmin, false)
        return VoteHolder(inflatedView)

    }

    override fun getItemCount(): Int = rips!!.size ?: 0

    override fun onBindViewHolder(holder: RIPVotingAdapter.VoteHolder, position: Int) {
        val item = rips?.get(position)
        Log.i("RecyclerAdapter", "onBindViewHolder")
        holder.bindFlag(item, context, service, ws)

    }

    class VoteHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
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
            var tv = view.ripTxt as TextView;
            tv?.setText(this.rip?.statement);
            view.studentName.setText(this.rip?.user?.username)
            view.startDebateBtn.setOnClickListener(View.OnClickListener {
                ws.sendMessage(ESocket.DEBATE_RIP.value + "|" + this.rip?._id);
            })
            view.startVoteBtn.setOnClickListener(View.OnClickListener {
                ws.sendMessage(ESocket.VOTE_RIP.value + "|" + this.rip?._id);
            })


        }

    }
}