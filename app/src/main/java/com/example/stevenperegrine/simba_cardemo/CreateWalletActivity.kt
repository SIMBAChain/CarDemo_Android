package com.example.stevenperegrine.simba_cardemo


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_createwallet.*
import org.kethereum.bip39.generateMnemonic
import org.kethereum.bip39.wordlists.WORDLIST_ENGLISH
import org.kethereum.crypto.api.ec.keyPairGenerator
import org.kethereum.crypto.model.ECKeyPair
import org.kethereum.wallet.LIGHT_SCRYPT_CONFIG

import org.kethereum.crypto.createEthereumKeyPair
import org.kethereum.crypto.model.PrivateKey
import org.kethereum.crypto.publicKeyFromPrivate
import org.kethereum.wallet.createWallet
import org.kethereum.wallet.generateWalletFile


class CreateWalletActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createwallet)
        createAddress.text = publicKeyFromPrivate(privateKey = ("5254aae6a8d34a95a5aff7350d11f5bf46db6deca2182545fbd7267ece2cb486" as PrivateKey)).toString()
        createSeed.text = generateMnemonic(128,WORDLIST_ENGLISH).toString()

    }


}
