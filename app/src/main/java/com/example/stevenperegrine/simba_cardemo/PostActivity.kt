package com.example.stevenperegrine.simba_cardemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap
import android.media.Image
import android.text.Editable
import android.widget.Toast
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.RequestBody
import kotlinx.android.synthetic.main.activity_importwallet.*
import kotlinx.android.synthetic.main.activity_post.*
import kotlinx.android.synthetic.main.layout_get_row.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import org.bitcoinj.crypto.HDUtils
import org.bitcoinj.wallet.DeterministicKeyChain
import org.bitcoinj.wallet.DeterministicSeed
import org.spongycastle.math.raw.Mod
import org.web3j.abi.datatypes.Utf8String
import org.web3j.crypto.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class PostActivity : AppCompatActivity() {
    var isImage = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)





    }
    //Take Car Image
    fun TakeCarImage(view:View){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, 1)
            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            postImage.setImageBitmap(imageBitmap)
            isImage = true
           // postImage.rotation = 0F
        }
        if (requestCode === 2 && resultCode === RESULT_OK) {
            val imageUri = data?.getData()
            postImage.setImageURI(imageUri)
            isImage = true
          //  postImage.rotation = 90F

        }
    }
    //Load Car Image
    fun pickImage(view:View) {

        val gallery = Intent(Intent.ACTION_GET_CONTENT)
        gallery.type = "image/*"
        startActivityForResult(gallery, 2)

    }

    //Post
    public fun post(view: View) {
        if (postMake.text.isNotEmpty() && postModel.text.isNotEmpty() && postVIN.text.isNotEmpty()) {
        progressbarPost.visibility = View.VISIBLE
            //get address for FROM field
            val userAddress = openFileInput("address").readBytes().toString(charset("UTF8"))




            //GET VIEW STUFF
            val httpClient = OkHttpClient.Builder().build()
            val builder = Retrofit.Builder()
                .baseUrl("https://api.simbachain.com/v1/ioscardemo2/")
                .addConverterFactory(GsonConverterFactory.create())

            val retrofit = builder.client(httpClient).build()

            val client = retrofit.create(Methods::class.java!!)
            val formMake = Utf8String(postMake.text.toString())
            val formModel = Utf8String(postModel.text.toString())
            val formVin = Utf8String(postVIN.text.toString())
            val formFrom = Utf8String(userAddress)

            if (isImage == false)
            {
            val call = client.postCar(formMake, formModel, formVin, formFrom)

            var response = call.enqueue(object : Callback<Models.PostCar> {
                override fun onResponse(call: Call<Models.PostCar>, response: Response<Models.PostCar>) {
                   // Toast.makeText(this@PostActivity,  response.body()!!.payload.toString(), Toast.LENGTH_LONG).show()
                    signtransaction(response)
                }

                override fun onFailure(call: Call<Models.PostCar>, t: Throwable) {
                }
            })
        }
           else
            {
              val formImage = postImage as Image
                val call = client.postCarandImage(formMake, formModel, formVin, formFrom,formImage)

                var response = call.enqueue(object : Callback<Models.PostCar> {
                    override fun onResponse(call: Call<Models.PostCar>, response: Response<Models.PostCar>) {
                        Toast.makeText(this@PostActivity, response.body()!!.payload.toString(), Toast.LENGTH_LONG).show()

                    }

                    override fun onFailure(call: Call<Models.PostCar>, t: Throwable) {
                    }
                })
            }
        }
        else
        {
            Toast.makeText(this@PostActivity, "Please fill all fields and try again", Toast.LENGTH_LONG).show()
        }
    }



    //Sign the transaction
    fun signtransaction(response: Response<Models.PostCar>){
        //Wallet for Signing
        val seedCode = openFileInput("seed").readBytes().toString(charset("UTF8"))


        // BitcoinJ
        val seed = DeterministicSeed(seedCode, null, "", 1409478661L)
        val chain = DeterministicKeyChain.builder().seed(seed).build()


        val keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0")
        val key = chain.getKeyByPath(keyPath, true)
        val privKey = key.privKey

        // Web3j
        val credentials = Credentials.create(privKey.toString(16))

        val postPayload = response.body()!!.payload
        val postRaw = postPayload["raw"] as Map<*,*>
        val postdata = postRaw["data"] as String
        val postid = response.body()!!.id

        val signedtransaction = credentials.ecKeyPair.sign(postdata.toByteArray()).r



        //GET VIEW STUFF
        val httpClient = OkHttpClient.Builder().build()
        val builder = Retrofit.Builder()
            .baseUrl("https://api.simbachain.com/v1/ioscardemo2/transaction/4702e7392c244d79bde7dbdbebff5185/")
            .addConverterFactory(GsonConverterFactory.create())

        val retrofit = builder.client(httpClient).build()
        val client = retrofit.create(Methods::class.java!!)
        val signedmodel :Models.SignedData = Models.SignedData(signedtransaction.toString())

        val call = client.postsigneddata("",signedmodel)

            var response = call.enqueue(object : Callback<Models.SignedData> {
                override fun onResponse(call: Call<Models.SignedData>, response: Response<Models.SignedData>) {
                    progressbarPost.visibility = View.INVISIBLE
                    if (response.code() == 200)
                    {
                        Toast.makeText(this@PostActivity, "Transaction Posted And Signed", Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        Toast.makeText(this@PostActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<Models.SignedData>, t: Throwable) {
                    progressbarPost.visibility = View.INVISIBLE
                }
            })

      //  Toast.makeText(this@PostActivity, "all good", Toast.LENGTH_LONG).show()
    }
}