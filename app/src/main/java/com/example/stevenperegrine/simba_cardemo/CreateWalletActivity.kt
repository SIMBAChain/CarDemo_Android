package com.example.stevenperegrine.simba_cardemo


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fasterxml.jackson.annotation.ObjectIdGenerators

import kotlinx.android.synthetic.main.activity_createwallet.*
import org.web3j.abi.datatypes.Address
import org.web3j.crypto.*
import org.web3j.protocol.Web3j
import java.security.KeyPair
import java.util.UUID.randomUUID
import java.util.UUID
import org.bitcoinj.crypto.HDUtils
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.wallet.DeterministicKeyChain
import org.bitcoinj.wallet.DeterministicSeed
import org.bitcoinj.wallet.KeyChain


class CreateWalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createwallet)





    }
    fun createWallet(view:View)
    {
        val seedCode = "jeans absorb curve mimic task apology green ability cake eyebrow report inner"

// BitcoinJ
        val seed = DeterministicSeed(seedCode, null, "", 1409478661L)
        val chain = DeterministicKeyChain.builder().seed(seed).build()


        val keyPath = HDUtils.parsePath("M/44H/60H/0H/0/0")
        val key = chain.getKeyByPath(keyPath, true)
        val privKey = key.privKey

// Web3j
        val credentials = Credentials.create(privKey.toString(16))
        createAddress.text = credentials.address
        createPrivateKey.text = privKey.toString()
    }

}
