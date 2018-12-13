package com.example.stevenperegrine.simba_cardemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_get.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList
import kotlin.*

var getData: ArrayList<*>? = null
class GetActivity : AppCompatActivity() {
    var selected = 0



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

        //recyclerview stuff
        val recyclerView = findViewById<RecyclerView>(R.id.myRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        //Requests all getData from from the audits page
        call.enqueue(object : Callback<Models.GetCars> {
            override fun onResponse(call: Call<Models.GetCars>, response: Response<Models.GetCars>) {
                println(response.body())
                val resultsArray = response.body()?.results as ArrayList<*>
                 getData = resultsArray



                //recyclerview  adapter stuff
                progressBar.visibility = View.GONE
                val adapter = CustomAdapter()
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<Models.GetCars>, t: Throwable) {



            }
        })



        }


    fun filteredGet(view: View){

        //GET VIEW STUFF
        val httpClient = OkHttpClient.Builder().build()



        val builder = Retrofit.Builder()
            .baseUrl("https://api.simbachain.com/v1/ioscardemo2/registerCar/?Make_contains=" + filterText.text.toString())
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.client(httpClient).build()

        val client = retrofit.create(Methods::class.java!!)

        val call = client.getCarFiltered("")

        //recyclerview stuff
        val recyclerView = findViewById<RecyclerView>(R.id.myRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.VERTICAL, false)
        //Requests all getData from from the audits page
        call.enqueue(object : Callback<Models.GetCars> {
            override fun onResponse(call: Call<Models.GetCars>, response: Response<Models.GetCars>) {
                println(response.body())
                val resultsArray = response.body()?.results as ArrayList<*>
                getData = resultsArray



                //recyclerview  adapter stuff
                progressBar.visibility = View.GONE
                val adapter = CustomAdapter()
                recyclerView.adapter = adapter
            }

            override fun onFailure(call: Call<Models.GetCars>, t: Throwable) {



            }
        })
    }
    }







