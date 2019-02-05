package com.example.stevenperegrine.simba_cardemo

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.support.v4.app.ActivityCompat
import java.util.jar.Manifest
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import android.support.v4.content.ContextCompat

class MainActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Check Permissions
        Dexter.withActivity(this)
            .withPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {/* ... */
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {/* ... */
                }
            }).check()






        //get Balance
          //  val localAddress = "0x4c01d2810e6E38947addFD6C5A086C2F62da296B"
        postButton.isEnabled = false
        if (fileList().contains("address")) {



            val userAddress = openFileInput("address").readBytes().toString(charset("UTF8"))
            val urlEnd = "/api?module=account&action=Balance&address=" + userAddress + "&tag=latest&apikey=8TZXFHXHCEBNSMQZDP64NKS8R4SDHVNWSF"
            balText.text = "Loading..."
            val httpClient = OkHttpClient.Builder().build()


            val builder = Retrofit.Builder()
                .baseUrl("https://api-rinkeby.etherscan.io")
                .addConverterFactory(GsonConverterFactory.create())

            val retrofit = builder.client(httpClient).build()

            val client = retrofit.create(Methods::class.java!!)

            val call = client.bal(urlEnd)



            call.enqueue(object : Callback<Models.Balance> {
                override fun onResponse(call: Call<Models.Balance>, response: Response<Models.Balance>) {
                    println(response.body())
                    val dict = response.body()
                    balText.text = response.body().toString()
                    val balList = response.body()?.result
                    val balConv =
                        balList!!.toDouble() / 1000000000000000000 //The Balance is returned as Wei which is 1/1000000000000000000 Eth. That is the purpose behind the division
                    balText.text = "Balance: " + balConv.toString() + " Eth"
                    if (balConv <=0.1)
                    {
                        Toast.makeText(this@MainActivity, "No ETH found in wallet posting disabled", Toast.LENGTH_LONG).show()
                        postButton.isEnabled = false
                        postButton.alpha = 0.5f
                        needEthButton.visibility = View.VISIBLE
                    }
                    else
                    {
                        postButton.alpha = 1.0f
                        postButton.isEnabled = true

                        if (ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                            Toast.makeText(this@MainActivity, "Read Storage Permission Denied Please Enable in Settings", Toast.LENGTH_LONG).show()
                            postButton.isEnabled = false
                            postButton.alpha = 0.5f
                        }
                    }
                }

                override fun onFailure(call: Call<Models.Balance>, t: Throwable) {

                    balText.text = "fail"
                    balText.text = t.message
                }
            })
        }
        else
        {
            Toast.makeText(this@MainActivity, "No wallet found posting disabled", Toast.LENGTH_LONG).show()
        }



    }

    public fun gotoGet(view: View) {
        this.startActivity(Intent(this, GetActivity::class.java))
    }
    public fun gotoPost(view: View) {
        this.startActivity(Intent(this, PostActivity::class.java))
    }
    public fun gotowalletmenu(view: View) {
        this.startActivity(Intent(this, WalletMenuActivity::class.java))
    }

    public fun needEth(view: View){
        val uris = Uri.parse("https://www.rinkeby.io/#faucet")
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        startActivity(intents)
    }
}
