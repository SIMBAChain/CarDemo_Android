package com.example.stevenperegrine.simba_cardemo

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View


class WalletMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_menu)
    }
    public fun gotocreatewallet(view: View) {
        this.startActivity(Intent(this, CreateWalletActivity::class.java))
    }

}
