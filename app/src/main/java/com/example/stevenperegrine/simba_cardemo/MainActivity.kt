package com.example.stevenperegrine.simba_cardemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get Balance
          //  val localAddress = "0x4c01d2810e6E38947addFD6C5A086C2F62da296B"
        val localAddress = "/api?module=account&action=Balance&address=0x4c01d2810e6E38947addFD6C5A086C2F62da296B&tag=latest&apikey=8TZXFHXHCEBNSMQZDP64NKS8R4SDHVNWSF"
            balText.text = "Loading..."
            val httpClient = OkHttpClient.Builder().build()



            val builder = Retrofit.Builder()
                .baseUrl("https://api-rinkeby.etherscan.io")
                .addConverterFactory(GsonConverterFactory.create())

            val retrofit = builder.client(httpClient).build()

            val client = retrofit.create(Methods::class.java!!)

            val call = client.bal(localAddress)



            //Requests all data from from the audits page
            call.enqueue(object : Callback<Models.Balance> {
                override fun onResponse(call: Call<Models.Balance>, response: Response<Models.Balance>) {
                    println(response.body())
                    val dict = response.body()
                    balText.text = response.body().toString()
                    val balList = response.body()?.result
                    val balConv = balList!!.toDouble() / 1000000000000000000 //The Balance is returned as Wei which is 1/1000000000000000000 Eth. That is the purpose behind the division
                    balText.text = "Balance: " + balConv.toString() + " Eth"
                }

                override fun onFailure(call: Call<Models.Balance>, t: Throwable) {

                    balText.text = "fail"
                    balText.text = t.message
                }
            })
        //GET VIEW STUFF
        val httpClient2 = OkHttpClient.Builder().build()



        val builder2 = Retrofit.Builder()
            .baseUrl("https://api.simbachain.com/v1/ioscardemo2/")
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit2 = builder2.client(httpClient2).build()

        val client2 = retrofit2.create(Methods::class.java!!)

        val call2 = client2.getCar()



        //Requests all data from from the audits page
        call2.enqueue(object : Callback<Models.GetCars> {
            override fun onResponse(call: Call<Models.GetCars>, response: Response<Models.GetCars>) {
                println(response.body())
                val dict = response.body()
                getText.text = response.body().toString()
             //   val balList = response.body()?.
              //  val balConv = balList!!.toDouble() / 1000000000000000000 //The Balance is returned as Wei which is 1/1000000000000000000 Eth. That is the purpose behind the division
            //    balText.text = "Balance: " + balConv.toString() + " Eth"
            }

            override fun onFailure(call: Call<Models.GetCars>, t: Throwable) {

                getText.text = "fail"
                getText.text = t.message

            }
        })
    }
    fun gotoGet(view: View) {
        this.startActivity(Intent(this, GetActivity::class.java))
    }
}