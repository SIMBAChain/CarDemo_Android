package com.example.stevenperegrine.simba_cardemo

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.view.View
import android.widget.Toast
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_get.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.*
class GetActivity : AppCompatActivity() {
    var selected = 0
    var data: ArrayList<*>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get)


        //GET VIEW STUFF
        val httpClient = OkHttpClient.Builder().build()



        val builder = Retrofit.Builder()
            .baseUrl("https://api.simbachain.com/v1/ioscardemo2/")
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.client(httpClient).build()

        val client = retrofit.create(Methods::class.java!!)

        val call = client.getCar()



        //Requests all data from from the audits page
        call.enqueue(object : Callback<Models.GetCars> {
            override fun onResponse(call: Call<Models.GetCars>, response: Response<Models.GetCars>) {
                println(response.body())
                val resultsArray = response.body()?.results as ArrayList<*>
                 data = resultsArray
                make.text = "Ready"

                count.text = response.body()?.count.toString()
                //   val balList = response.body()?.
                //  val balConv = balList!!.toDouble() / 1000000000000000000 //The Balance is returned as Wei which is 1/1000000000000000000 Eth. That is the purpose behind the division
                //    balText.text = "Balance: " + balConv.toString() + " Eth"
            }

            override fun onFailure(call: Call<Models.GetCars>, t: Throwable) {

                make.text = "fail"
                make.text = t.message

            }
        })
        }

    fun GetNumber(view: View)
    {

        val sel = number.text.toString()
        selected = sel.toInt()
        val resultsDict = data!!.get(selected) as Map<*, *>
       val resultsPayload = resultsDict["payload"] as Map<*, *>
        val resultsInputs = resultsPayload["inputs"] as Map<*, *>
        val resultsMake = resultsInputs["Make"] as String
        val resultsModel = resultsInputs["Model"] as String
        val resultsVin = resultsInputs["VIN"] as String
        make.text = resultsMake
        model.text = resultsModel
        vin.text = resultsVin
    }

    }







