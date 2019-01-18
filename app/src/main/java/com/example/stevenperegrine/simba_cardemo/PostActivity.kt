package com.example.stevenperegrine.simba_cardemo

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.content.Intent
import android.provider.MediaStore
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_post.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import org.bitcoinj.crypto.HDUtils
import org.bitcoinj.wallet.DeterministicKeyChain
import org.bitcoinj.wallet.DeterministicSeed
import org.web3j.abi.datatypes.Utf8String
import org.web3j.crypto.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import java.io.File
import java.net.URI
import android.app.PendingIntent.getActivity
import android.content.Context
import com.koushikdutta.ion.Ion
import  java.io.FileOutputStream
import  java.io.BufferedOutputStream
import java.net.URLEncoder
import java.io.IOException


import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.StrictMode
import android.support.v4.content.FileProvider
import android.util.Log
import android.widget.ImageView
import okhttp3.Request

import java.net.URL


//import com.squareup.okhttp.RequestBody
class PostActivity : AppCompatActivity() {
    var isImage = false
    lateinit var myImage: String
    lateinit var mCurrentPhotoPath: String
    override fun onCreate(savedInstanceState: Bundle?) {

        val policy : StrictMode.ThreadPolicy =  StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
    }

    /*---------------------
    Take And Import Images
    -----------------------*/
    //Take Car Image
    fun TakeCarImage(view:View){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {

                startActivityForResult(takePictureIntent, 1)
            }
        }

    }

    //Load Car Image
    fun pickImage(view:View) {

        val gallery = Intent(Intent.ACTION_PICK)
        gallery.type = "image/*"


        startActivityForResult(gallery, 2)
        //val gallery = Intent(Intent.ACTION_PICK_ACTIVITY)
       // startActivityForResult(gallery, 2)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            postImage.setImageBitmap(imageBitmap)
            isImage = true
           // postImage.rotation = 0F
        }
        if (requestCode === 2 && resultCode === RESULT_OK) {
            val imageUri = data?.data

          /*  val uri = data!!.getData()
            val file = File(uri.path)//create path from uri
            val split = file.path.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()//split the path.
            myImage = split[1]//assign it to a string(your choice).*/


           val whatev = contentResolver.openInputStream(imageUri)
           // myImage = URLEncoder.encode(File(imageUri!!.path).absolutePath,"UTF-8")
            myImage = imageUri.toString()

           // val file = File(getRealPathFromURI(data.getData()))
            postImage.setImageURI(imageUri)

           /* val selectedImage = imageUri
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(
                selectedImage,
                filePathColumn, null, null, null
            )
            cursor!!.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val picturePath = cursor.getString(columnIndex)
            cursor.close()
            myImage = picturePath */

            isImage = true

            val hardcode = "/storage/emulated/0/Download/CR-Inline-top-picks-Toyota-Yaris-02-17.jpeg"

            myImage = hardcode


             val uri = data!!.getData();
             val cr = this.getContentResolver()
             val mime = cr.getType(uri)

        }

    }



    /*-----------------
    Posting and signing
    -----------------*/
    //Post
    public fun post(view: View) {


        if (postMake.text.isNotEmpty() && postModel.text.isNotEmpty() && postVIN.text.isNotEmpty()) {

            //get address for FROM field
            val userAddress = openFileInput("address").readBytes().toString(charset("UTF8"))




            //GET VIEW STUFF
            val httpClient = OkHttpClient.Builder().build()

            val builder = Retrofit.Builder()
                .baseUrl("https://api.simbachain.com/v1/ioscardemo2/")
                .addConverterFactory(GsonConverterFactory.create())

            val retrofit = builder.client(httpClient).build()
            val client = retrofit.create(Methods::class.java!!)


            if (isImage == false)
            {
                progressbarPost.visibility = View.VISIBLE
                val formMake = Utf8String(postMake.text.toString())
                val formModel = Utf8String(postModel.text.toString())
                val formVin = Utf8String(postVIN.text.toString())
                val formFrom = Utf8String(userAddress)
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
                progressbarPost.visibility = View.VISIBLE


                val url = URL("https://api.simbachain.com/v1/ioscardemo2/registerCar/")
                val multipart = Multipart(url)
                multipart.addFormField("Make","droid")
                multipart.addFormField("Model","droid model")
                multipart.addFormField("VIN","12345")
                multipart.addFormField("from",userAddress)
                    //  multipart.addFilePart()
                multipart.upload(object : com.example.stevenperegrine.simba_cardemo.Multipart.OnFileUploadedListener{
                    override fun onFileUploadingSuccess(response: String) {
                        progressbarPost.visibility = View.INVISIBLE
                        Toast.makeText(this@PostActivity,  "Good", Toast.LENGTH_LONG).show()
                    }

                    override fun onFileUploadingFailed(responseCode: Int) {
                        progressbarPost.visibility = View.INVISIBLE
                        Toast.makeText(this@PostActivity,  responseCode.toString(), Toast.LENGTH_LONG).show()
                    }
                })












/*
                    try {
                        Toast.makeText(this@PostActivity,"posting",Toast.LENGTH_LONG).show()

                        val client : OkHttpClient = OkHttpClient()
                        postVIN.setText("one")
                        val file = File("/storage/emulated/0/Download/CR-Inline-top-picks-Toyota-Yaris-02-17.jpeg")
                        val MEDIA_TYPE_JPEG : MediaType? = MediaType.parse("image/jpeg")
                        val requestBody : RequestBody = MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("file[0]", "image.jpeg", RequestBody.create(MEDIA_TYPE_JPEG, file))
                            .addFormDataPart("Make", postMake.text.toString())
                            .addFormDataPart("Model", postModel.text.toString())
                            .addFormDataPart("VIN", postVIN.text.toString())
                            .addFormDataPart("from", userAddress)
                            .build()
                        postVIN.setText("two")
                        var request : Request = Request.Builder()
                            .url("https://api.simbachain.com/v1/ioscardemo2/registerCar/")
                            .header("Accept", "application/json")
                            .header("Content-Type", "multipart/form-data")
                            .header("APIKEY", "0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
                            .post(requestBody).build()
                        postVIN.setText("three")

                        try {
                            postVIN.setText("four")
                            var response = client.newCall(request).execute()
                            if (!response.isSuccessful){
                                postVIN.setText("five")
                                progressbarPost.visibility = View.INVISIBLE
                             //   Log.d("fsdsdsds", response.toString())
                                Toast.makeText(this@PostActivity,"gud",Toast.LENGTH_LONG).show()

                            }else {
                                postVIN.setText("five")
                                progressbarPost.visibility = View.INVISIBLE
                              //  Log.d("fsdsdsds", response.toString())
                                Toast.makeText(this@PostActivity,"fail",Toast.LENGTH_LONG).show()
                            }
                        }catch (e: IOException){
                            postVIN.setText(e.toString())
                            Toast.makeText(this@PostActivity,e.toString(),Toast.LENGTH_LONG).show()
                            progressbarPost.visibility = View.INVISIBLE
                          //  Log.d("fsdsdsds", e.message)
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@PostActivity,"uggggggadkjhfsdfjhsk" + e.toString(),Toast.LENGTH_LONG).show()
                        progressbarPost.visibility = View.INVISIBLE
                        e.printStackTrace()
                    }
*/








              //  val formMake = MultipartBody.Part.createFormData("Make", postMake.text.toString())
              //  val formModel = MultipartBody.Part.createFormData("Model",postModel.text.toString())
              //  val formVin = MultipartBody.Part.createFormData("VIN", postVIN.text.toString())
              //  val formFrom = MultipartBody.Part.createFormData("from", userAddress)
             //   val formImage = com.squareup.okhttp.RequestBody.create(com.squareup.okhttp.MediaType.parse("image/jpeg"), File(myImage))



/*
                val formMake = RequestBody.create(MultipartBody.FORM,postMake.text.toString())
                val formModel = RequestBody.create(MultipartBody.FORM,postModel.text.toString())
                val formVin = RequestBody.create(MultipartBody.FORM,postVIN.text.toString())
                val formFrom = RequestBody.create(MultipartBody.FORM,userAddress)
                //RequestBody.create(MEDIA_TYPE_PNG, file)


             //   val formImage = com.squareup.okhttp.RequestBody.create(com.squareup.okhttp.MediaType.parse("image/jpeg"), File("/storage/emulated/0/Download/CR-Inline-top-picks-Toyota-Yaris-02-17.jpeg"))

                val formImage = MultipartBody.Part.createFormData("file[0]", "image.jpeg", RequestBody.create(MediaType.parse("image/jpeg"), File("/storage/emulated/0/Download/CR-Inline-top-picks-Toyota-Yaris-02-17.jpeg")))

             //   val formBundleHash: Utf8String = Utf8String("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")
                val call = client.postCarandImage(formMake,formModel,formVin,formFrom,formImage)

                var response = call.enqueue(object : Callback<Models.PostCar> {
                    override fun onResponse(call: Call<Models.PostCar>, response: Response<Models.PostCar>) {
                        //signtransaction(response)
                      // postVIN.setText(File("content://com.android.providers.downloads.documents/document/18").exists())
                        Toast.makeText(this@PostActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                        progressbarPost.visibility = View.INVISIBLE
                    }

                    override fun onFailure(call: Call<Models.PostCar>, t: Throwable) {
                        postVIN.setText(File("/storage/emulated/0/Download/CR-Inline-top-picks-Toyota-Yaris-02-17.jpeg").extension.toString())
                        Toast.makeText(this@PostActivity, t.toString(), Toast.LENGTH_LONG).show()
                        progressbarPost.visibility = View.INVISIBLE
                    }
                })
*/
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



        //Sign the transaction
        val httpClient = OkHttpClient.Builder().build()
        val builder = Retrofit.Builder()
            .baseUrl("https://api.simbachain.com/v1/ioscardemo2/transaction/"+postid+ "/")
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




