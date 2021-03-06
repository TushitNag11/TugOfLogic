package com.example.tugoflogic.Admin

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tugoflogic.R
import com.example.tugoflogic.Service.RipService
import com.example.tugoflogic.inflate
import com.example.tugoflogic.models.ERipStatus
import com.example.tugoflogic.models.Rip
import kotlinx.android.synthetic.main.ripselection_admin.view.*

class RIPAdapter(
    val context: Context,
    var rips: List<Rip>?,
    val service: RipService
) :
    RecyclerView.Adapter<RIPAdapter.VoteHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RIPAdapter.VoteHolder {
        val inflatedView = parent.inflate(R.layout.ripselection_admin, false)
        return VoteHolder(inflatedView)

    }

    fun update(modelList:List<Rip>){
        this.rips = modelList
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = rips!!.size ?: 0

    override fun onBindViewHolder(holder: RIPAdapter.VoteHolder, position: Int) {
        val item = rips?.get(position)
        Log.i("RecyclerAdapter", "onBindViewHolder")
        holder.bindFlag(item, context, service)

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

        fun bindFlag(rip: Rip?, context: Context, service: RipService) {
            Log.i("RecyclerAdapter", "bindFlag")
            this.rip = rip
            var tv = view.ripTxt as TextView;
            tv?.setText(this.rip?.statement);
            view.status.visibility = View.GONE
            view.studentName.setText(this.rip?.user?.username)
            view.acceptBtn.setOnClickListener(View.OnClickListener {
                this.rip?.status = ERipStatus.ACCEPTED;
                service.update(this.rip);
                view?.acceptBtn.visibility = View.GONE
                view?.declineBtn.visibility = View.GONE
                view.status.visibility = View.VISIBLE
                view.status.setText("ACCEPTED")
            })
            view.declineBtn.setOnClickListener(View.OnClickListener {
                this.rip?.status = ERipStatus.DECLINE;
                service.update(this.rip);
                view?.acceptBtn.visibility = View.GONE
                view?.declineBtn.visibility = View.GONE
                view.status.visibility = View.VISIBLE
                view.status.setText("DECLINED")
            })


        }

    }
}