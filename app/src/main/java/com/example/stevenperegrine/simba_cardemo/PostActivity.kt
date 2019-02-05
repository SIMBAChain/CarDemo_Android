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
import java.net.URI
import android.app.PendingIntent.getActivity
import android.content.Context
import java.net.URLEncoder


import android.app.AlertDialog
import android.content.ContentResolver
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.StrictMode
import android.preference.PreferenceActivity
import android.provider.DocumentsContract
import android.support.v4.content.FileProvider
import android.util.Log
import android.widget.ImageView
import com.example.stevenperegrine.simba_cardemo.PostClasses.PostService
import okhttp3.Request

import java.net.URL


import org.json.JSONObject

import com.example.stevenperegrine.simba_cardemo.PostClasses.FileUtils
import com.example.stevenperegrine.simba_cardemo.PostClasses.ImageFilePath
import org.spongycastle.asn1.cms.CMSObjectIdentifiers.data
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


class PostActivity : AppCompatActivity() {
    var isImage = false
    lateinit var myImage: String
    lateinit var mCurrentPhotoPath: String
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
    }

    /*---------------------
    Take And Import Images
    -----------------------*/
    //Take Car Image


    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            mCurrentPhotoPath = absolutePath
        }
    }

    fun TakeCarImage(view:View){
        val REQUEST_TAKE_PHOTO = 1

            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                // Ensure that there's a camera activity to handle the intent
                takePictureIntent.resolveActivity(packageManager)?.also {
                    // Create the File where the photo should go
                    val photoFile: File? = try {
                        createImageFile()
                    } catch (ex: IOException) {
                        // Error occurred while creating the File

                        null
                    }
                    // Continue only if the File was successfully created
                    photoFile?.also {
                        val photoURI: Uri = FileProvider.getUriForFile(
                            this,
                            "com.example.android.fileprovider",
                            it
                        )
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                    }
                }
            }


    }

    //Load Car Image
    fun pickImage(view:View) {

        val gallery = Intent(Intent.ACTION_PICK)
        gallery.type = "image/*"

        startActivityForResult(gallery, 2)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            try {


                val compressionRatio = 25
                val file = File(mCurrentPhotoPath)
                try {
                    val bitmap = BitmapFactory.decodeFile(file.path)

                    bitmap.compress(Bitmap.CompressFormat.JPEG, compressionRatio, FileOutputStream(file))
                } catch (t: Throwable) {

                    Log.e("ERROR", "Error compressing file.$t")
                    t.printStackTrace()
                }

                val imageBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath)
                isImage = true
                postImage.setImageBitmap(imageBitmap)

            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
        if (requestCode === 2 && resultCode === RESULT_OK) {


            val imageUri = data?.data
            var imagePath: String = data?.data!!.path


            myImage = imageUri.toString()



            val realPath = ImageFilePath.getPath(this, data.getData())
//              realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData())


            try {
                val bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri)



                postImage.setImageBitmap(bitmap)
                isImage = true
                mCurrentPhotoPath = realPath


            } catch (e:IOException) {
                e.printStackTrace()
            }
        } else {
            Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show()
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
                    response.raw().toString()
                    signtransaction(response)

                }

                override fun onFailure(call: Call<Models.PostCar>, t: Throwable) {
                }
            })
        }
           else
            {
                progressbarPost.visibility = View.VISIBLE
                val image = "/storage/emulated/0/Download/CR-Inline-top-picks-Toyota-Yaris-02-17.jpeg"


                PostService().postWithImage(this,postMake.text.toString(),postModel.text.toString(),postVIN.text.toString(),userAddress,File(mCurrentPhotoPath), object : retrofit2.Callback<Models.PostCar>{
                    override fun onResponse(call: Call<Models.PostCar>, response: Response<Models.PostCar>) {


                        if (response.isSuccessful) {
                           // Toast.makeText(this@PostActivity, "It has been posted", Toast.LENGTH_LONG).show()
                           // postVIN.setText(response.body().toString())
                            signtransaction(response)


                        } else {
                            Toast.makeText(this@PostActivity, "Error Code: " + response.code().toString(), Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<Models.PostCar>, t: Throwable) {
                        progressbarPost.visibility = View.INVISIBLE
                        postVIN.setText(t.toString())
                        Toast.makeText(this@PostActivity, "Error: " + t.toString(), Toast.LENGTH_LONG).show()
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
        //postVIN.setText(response.body().toString())

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
                  //  progressbarPost.visibility = View.INVISIBLE
                    if (response.code() == 200)
                    {
                        progressbarPost.visibility = View.INVISIBLE
                        Toast.makeText(this@PostActivity, "Transaction Posted And Signed", Toast.LENGTH_LONG).show()
                    }
                    else
                    {
                        progressbarPost.visibility = View.INVISIBLE
                        Toast.makeText(this@PostActivity, response.code().toString(), Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<Models.SignedData>, t: Throwable) {
                    progressbarPost.visibility = View.INVISIBLE
                }
            })

    }


}




