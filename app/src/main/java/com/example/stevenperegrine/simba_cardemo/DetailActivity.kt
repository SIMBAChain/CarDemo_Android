package com.example.stevenperegrine.simba_cardemo

import android.graphics.BitmapFactory
import android.graphics.ColorSpace
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Array
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
        gasUsed = intent.getDoubleExtra("gasUsed",0.0)

        transactionHash.text = "Transaction Hash: " + tranHash
        transactionFrom.text = "Transaction From: " + tranFrom
        transactionTo.text = "Transaction To: " + tranTo
        transactionStatus.text = "Transaction Status: " + tranStatus
        gasUsedText.text = "Gas Used: " + gasUsed.toString()

        //Image View
        ID = intent.getStringExtra("id")
        detailIPFS.text = ID

        val httpClient = OkHttpClient.Builder().build()



        val builder = Retrofit.Builder()
            .baseUrl("https://api.simbachain.com/v1/ioscardemo2/transaction/"+ ID +"/bundle/")
            .addConverterFactory(GsonConverterFactory.create())
        val retrofit = builder.client(httpClient).build()
        val client = retrofit.create(Methods::class.java!!)
        val call = client.getCarImage("")


        call.enqueue(object : Callback<Models.GetImage> {
            override fun onResponse(call: Call<Models.GetImage>, response: Response<Models.GetImage>) {

                if (response.body() != null)
                {
                    val manifestArray = response.body()?.manifest?.get(0)
                    val manifestDict = manifestArray as Map<*, *>

                    detailImageName.text = "Image Name: " + manifestDict["name"].toString()
                    detailImageSize.text = "Image Size: " + manifestDict["size"].toString() + " bytes"
                    val encodedImage = manifestDict["data"] as String
                    val imageByteArray = Base64.getDecoder().decode(encodedImage)
                    val decodedImage = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.size)
                    imageView.setImageBitmap(decodedImage)
                }
                else
                {
                    Toast.makeText(this@DetailActivity, "Image not available for this transaction", Toast.LENGTH_LONG).show()
                }

            }

            override fun onFailure(call: Call<Models.GetImage>, t: Throwable) {

            }
        })
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
