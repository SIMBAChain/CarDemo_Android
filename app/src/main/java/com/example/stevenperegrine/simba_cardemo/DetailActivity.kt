package com.example.stevenperegrine.simba_cardemo

import android.graphics.ColorSpace
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    var make:String = ""
    var model:String = ""
    var vin:String = ""

    var tranHash = ""
    var tranFrom = ""
    var tranTo = ""
    var tranStatus = ""
    var gasUsed = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        //displays basic detail text
        make = intent.getStringExtra("make")
        model = intent.getStringExtra("model")
        vin = intent.getStringExtra("vin")

        detailMake.text =   "Make: " + make
        detailModel.text =  "Model: " + model
        detailVin.text=     "VIN: " + vin

        //sets scroll view text
        tranHash = intent.getStringExtra("tranHash")
        tranFrom = intent.getStringExtra("tranFrom")
        tranTo = intent.getStringExtra("tranTo")
        tranStatus = intent.getStringExtra("tranStatus")
        gasUsed = intent.getStringExtra("gasUsed")

    }


}
