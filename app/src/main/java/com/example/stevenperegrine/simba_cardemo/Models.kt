package com.example.stevenperegrine.simba_cardemo

import android.service.voice.AlwaysOnHotwordDetector
import java.lang.reflect.Array
import java.util.*
import com.squareup.okhttp.RequestBody
import com.squareup.okhttp.MediaType
import org.web3j.abi.datatypes.Bool

object Models {
    data class Balance(val status: String,
                       val message: String,
                       val result: String )

    data class GetCars(val count: Int,
                       val next: Any,
                       val previous: Any,
                       val results: Any
                     //  val results: MutableMap<String,Any>
                       )

    data class GetImage(val bundle_hash: String,
                        val manifest: ArrayList<*>)

    data class PostCar(val id: String,
                       val payload: Map<*,*>,
                       val timestamp: String,
                       val smart_contract_id: String,
                       val bytes_stored_on_blockchain:String,
                       val bundle_id:Any,
                       val bytes_Stored_on_blockchain: String,
                       val adapter_id: String,
                       val application_id:String,
                       val data_store_id: String,
                       val method_id: String,
                       val organisation_id: String,
                       val parent_id: Any,
                       val user_id: Int,
                       val is_asset: Boolean,
                       val receipt: Any,
                       val group_id: Any,
                       val member_id: Any,
                       val error: Any,
                       val transaction_hash: Any

    )
    data class SignedData(val payload: String)


}

