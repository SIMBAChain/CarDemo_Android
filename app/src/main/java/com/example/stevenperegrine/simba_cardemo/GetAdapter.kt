package com.example.stevenperegrine.simba_cardemo

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
/*
class GetAdapter(private val context: Context, private val data: List<Models.GetCars>?) :
    RecyclerView.Adapter<GetAdapter.SimbaViewHolder>() {

    //Sets up everything in the RecyclerView
    internal inner class SimbaViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        var make: TextView
        var model: TextView
        var vin: TextView
        var id: TextView
        var parentLayout: RelativeLayout

        init {

            make = mView.findViewById(R.id.make)
            model = mView.findViewById(R.id.model)
            vin = mView.findViewById(R.id.vin)
            id = mView.findViewById(R.id.id)
            parentLayout = mView.findViewById(R.id.parent_layout)
        }
    }


    //Inflates the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimbaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val v = layoutInflater.inflate(R.layout.layout_get_row, parent, false)
        return SimbaViewHolder(v)
    }


    //Sets everything to the RecyclerView
    override fun onBindViewHolder(holder: SimbaViewHolder, position: Int) {

        holder.make.text = "Audit No. " + data[position].getHashIdInString()
        holder.model.text = "Poster ID: " + data[position].getAuditor()
        holder.vin.text = "Poster ID: " + data[position].getAuditor()
        holder.id.text = "Poster ID: " + data[position].getAuditor()


     /*   holder.parentLayout.setOnClickListener {
            val intent = Intent(context, AuditGalleryActivity::class.java)
            intent.putExtra("audit_no", data[position].getHashId())
            intent.putExtra("ipfc", data[position].getHash())
            intent.putExtra("verified", data[position].getVerified())
            context.startActivity(intent)
        } */
    }

    override fun getItemCount(): Int {
        return data.size
    }
}
*/