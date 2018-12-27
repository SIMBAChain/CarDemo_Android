package com.example.stevenperegrine.simba_cardemo


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_createwallet.*
import org.web3j.crypto.*
import org.bitcoinj.crypto.HDUtils
import org.bitcoinj.wallet.DeterministicKeyChain
import org.bitcoinj.wallet.DeterministicSeed
import android.content.Context
import kotlinx.android.synthetic.main.activity_importwallet.*


class ImportWalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_importwallet)





    }
    fun importWallet(view:View) {
        if (importPassField.text.isNotEmpty() && importPassFieldRep.text.isNotEmpty()) {
            if (importPassField.text.toString() == importPassFieldRep.text.toString())
            {
                val seedCode = importseed.text.toString()


                // BitcoinJ
                val seed = DeterministicSeed(seedCode, null, "", 1409478661L)
                val chain = DeterministicKeyChain.builder().seed(seed).build()


                val keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0")
                val key = chain.getKeyByPath(keyPath, true)
                val privKey = key.privKey

                // Web3j
                val credentials = Credentials.create(privKey.toString(16))
                importCreateAddress.text = credentials.address
                importCreatePrivateKey.text = "0x" + key.privateKeyAsHex.toString()
                importCreateSeed.text = seedCode

                //Save Wallet Info
                openFileOutput("address", Context.MODE_PRIVATE).use {
                    it.write(importCreateAddress.text.toString().toByteArray())
                }
                openFileOutput("privatekey", Context.MODE_PRIVATE).use {
                    it.write(importCreatePrivateKey.text.toString().toByteArray())
                }
                openFileOutput("seed", Context.MODE_PRIVATE).use {
                    it.write(importCreateSeed.text.toString().toByteArray())
                }
                openFileOutput("password", Context.MODE_PRIVATE).use {
                    it.write(importPassField.text.toString().toByteArray())
                }

            }
            else
            {
                Toast.makeText(this@ImportWalletActivity, "Password and repeat password are different.", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            Toast.makeText(this@ImportWalletActivity, "Either the Password or the Repeat Password field(s) are empty", Toast.LENGTH_LONG).show()
        }
    }
}
