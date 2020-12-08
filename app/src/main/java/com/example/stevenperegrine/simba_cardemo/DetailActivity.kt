package com.example.stevenperegrine.simba_cardemo

import android.R.attr.src
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*


class DetailActivity : AppCompatActivity() {
    var make:String = ""
    var model:String = ""
    var vin:String = ""

    var tranHash = ""
    var tranFrom = ""
    var tranTo = ""
    var tranStatus = ""
    var gasUsed : Double = 0.0
    var ID = ""

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
        gasUsed = intent.getDoubleExtra("gasUsed", 0.0)

        transactionHash.text = "Transaction Hash: " + tranHash
        transactionFrom.text = "Transaction From: " + tranFrom
        transactionTo.text = "Transaction To: " + tranTo
        transactionStatus.text = "Transaction Status: " + tranStatus
        gasUsedText.text = "Gas Used: " + gasUsed.toString()

        //Image View
        ID = intent.getStringExtra("id")
        detailIPFS.text = "IPFS Bundle Hash: " + ID

        // Disable the `NetworkOnMainThreadException` and make sure it is just logged.
        StrictMode
            .setThreadPolicy(StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build())

        val url = URL("https://api.simbachain.com/v1/ioscardemo2/transaction/"+ ID +"/file/0/")
        val connection: HttpURLConnection = url
            .openConnection() as HttpURLConnection

        connection.setDoInput(true)
        connection.setRequestProperty("APIKEY", "0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
        connection.connect()
        val input: InputStream = connection.getInputStream()
        val myBitmap = BitmapFactory.decodeStream(input)
        imageView.setImageBitmap(myBitmap)



    }
    fun infoToggle(view: View)
    {
        if (infoScroll.visibility == View.VISIBLE)
        {
            infoScroll.visibility = View.INVISIBLE
            imageView.visibility = View.VISIBLE
        }
        else
        {
            infoScroll.visibility = View.VISIBLE
            imageView.visibility = View.INVISIBLE
        }
    }

}
