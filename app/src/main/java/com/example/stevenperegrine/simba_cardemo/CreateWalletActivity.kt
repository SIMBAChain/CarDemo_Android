package com.example.stevenperegrine.simba_cardemo


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fasterxml.jackson.annotation.ObjectIdGenerators
import io.github.novacrypto.bip39.SeedCalculator
import kotlinx.android.synthetic.main.activity_createwallet.*
import org.web3j.abi.datatypes.Address
import org.web3j.crypto.*
import org.web3j.protocol.Web3j
import java.security.KeyPair
import java.util.UUID.randomUUID
import java.util.UUID



class CreateWalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createwallet)





    }

}
