package com.example.stevenperegrine.simba_cardemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import  android.net.Uri
import kotlinx.android.synthetic.main.activity_wallet_menu.*


class WalletMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_menu)


    }
    public fun gotocreatewallet(view: View) {
        this.startActivity(Intent(this, CreateWalletActivity::class.java))
    }
    public fun gotoimportwallet(view: View) {
        this.startActivity(Intent(this, ImportWalletActivity::class.java))
    }
    public fun openSimbaDashboard(view:View) {
        val uris = Uri.parse("https://app.simbachain.com/")
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        startActivity(intents)
    }
    public fun openSource(view:View) {
        val uris = Uri.parse("https://github.com/SIMBAChain/CarDemo_Android")
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        startActivity(intents)
    }
    public fun openDocs(view:View) {
        val uris = Uri.parse("https://cardemo-android.readthedocs.io/en/latest/")
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        startActivity(intents)
    }
    public fun openContact(view:View) {
        val uris = Uri.parse("https://simbachain.com/contact/")
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        startActivity(intents)
    }
fun reveal(view:View){
    if (fileList().contains("address"))
    {
        if (menuPass.text.toString() == openFileInput("password").readBytes().toString(charset("UTF8")) )
        {
        infoAddress.text = openFileInput("address").readBytes().toString(charset("UTF8"))
        infoPrivateKey.text = openFileInput("privatekey").readBytes().toString(charset("UTF8"))
        infoSeed.text = openFileInput("seed").readBytes().toString(charset("UTF8"))
        }
        else
        {
            Toast.makeText(this@WalletMenuActivity, "Wrong Password", Toast.LENGTH_LONG).show()
        }
    }
    else
    {
        Toast.makeText(this@WalletMenuActivity, "No stored Wallet", Toast.LENGTH_LONG).show()
    }
}
}
