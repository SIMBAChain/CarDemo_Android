package com.example.stevenperegrine.simba_cardemo


import android.content.Intent
import android.content.Context
import android.support.v7.widget.*
import android.view.*
import android.widget.*


class CustomAdapter(context: Context) : RecyclerView.Adapter<CustomAdapter.MyViewHolder>()
{
  val myContext = context

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
        p0?.parentLayout.setOnClickListener {

            //info values
            val resultsRaw = resultsPayload["raw"] as Map<*, *>
            val tranHash = resultsRaw["data"] as String
            val tranFrom = resultsRaw["from"] as String
            val tranTo = resultsRaw["to"] as String
            val tranStatus = dataList["status"] as String
            val gasUsed = resultsRaw["gas"] as Double

            //id for getting image
            val tranId = dataList["id"] as String


            val intent = Intent(myContext, DetailActivity::class.java)

            intent.putExtra("make", resultsMake)
            intent.putExtra("model", resultsModel)
            intent.putExtra("vin", resultsVin)
            intent.putExtra("tranHash", tranHash)
            intent.putExtra("tranFrom", tranFrom)
            intent.putExtra("tranTo", tranTo)
            intent.putExtra("tranStatus", tranStatus)
            intent.putExtra("gasUsed", gasUsed)
            intent.putExtra("id", tranId)
            myContext.startActivity(intent)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val textViewMake = itemView.findViewById<TextView>(R.id.make)
        val textViewModel = itemView.findViewById<TextView>(R.id.model)
        val textViewVin = itemView.findViewById<TextView>(R.id.vin)
        val textViewId = itemView.findViewById<TextView>(R.id.id)
        val parentLayout = itemView.findViewById<RelativeLayout>(R.id.parent_layout)


    }
}