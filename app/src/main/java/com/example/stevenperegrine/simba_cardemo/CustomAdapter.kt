package com.example.stevenperegrine.simba_cardemo

import android.support.v7.widget.*
import android.view.*
import android.widget.*

class CustomAdapter() : RecyclerView.Adapter<CustomAdapter.MyViewHolder>()
{
    val adapterData = getData

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val v = LayoutInflater.from(p0?.context).inflate(R.layout.layout_get_row, p0, false)
        return  MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        //   gets size of list for recycler view
        return adapterData!!.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        //binds the getData to the recycler view
        val dataList: Map<*,*> = adapterData!![p1] as Map<*, *>
        val resultsPayload = dataList["payload"] as Map<*, *>
        val resultsInputs = resultsPayload["inputs"] as Map<*, *>
        val resultsMake = resultsInputs["Make"] as String
        val resultsModel = resultsInputs["Model"] as String
        val resultsVin = resultsInputs["VIN"] as String

        p0?.textViewMake.text = "Make: " + resultsMake
        p0?.textViewModel.text = "Model: " + resultsModel
        p0?.textViewVin.text = "VIN: " + resultsVin
        p0?.textViewId.text = "ID: " + p1.toString()
      //  p0?.textViewLocation.text = userList.location
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewMake = itemView.findViewById<TextView>(R.id.make)
        val textViewModel = itemView.findViewById<TextView>(R.id.model)
        val textViewVin = itemView.findViewById<TextView>(R.id.vin)
        val textViewId = itemView.findViewById<TextView>(R.id.id)
      //  val textViewLocation = itemView.findViewById<TextView>(R.id.location)
    }
}