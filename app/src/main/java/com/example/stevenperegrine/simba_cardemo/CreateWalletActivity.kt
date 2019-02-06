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
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH
import android.content.Context


class CreateWalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createwallet)





    }
    fun createWallet(view:View) {
        if (passField.text.isNotEmpty() && passFieldRep.text.isNotEmpty()) {
            if (passField.text.toString() == passFieldRep.text.toString())
            {
                val seedCode = generateMnemonic(128, WORDLIST_ENGLISH)

                // BitcoinJ
                val seed = DeterministicSeed(seedCode, null, "", 1409478661L)
                val chain = DeterministicKeyChain.builder().seed(seed).build()


                val keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0")
                val key = chain.getKeyByPath(keyPath, true)
                val privKey = key.privKey

                // Web3j
                val credentials = Credentials.create(privKey.toString(16))
                createAddress.text = credentials.address
                createPrivateKey.text = "0x" + key.privateKeyAsHex.toString()
                createSeed.text = seedCode

                //Save Wallet Info
                openFileOutput("address", Context.MODE_PRIVATE).use {
                    it.write(createAddress.text.toString().toByteArray())
                }
                openFileOutput("privatekey", Context.MODE_PRIVATE).use {
                    it.write(createPrivateKey.text.toString().toByteArray())
                }
                openFileOutput("seed", Context.MODE_PRIVATE).use {
                    it.write(createSeed.text.toString().toByteArray())
                }
                openFileOutput("password", Context.MODE_PRIVATE).use {
                    it.write(passField.text.toString().toByteArray())
                }

            }
            else
            {
                Toast.makeText(this@CreateWalletActivity, "Password and repeat password are different.", Toast.LENGTH_LONG).show()
            }
        }
        else
        {
            Toast.makeText(this@CreateWalletActivity, "Either the Password or the Repeat Password field(s) are empty", Toast.LENGTH_LONG).show()
        }
    }
}
