package com.example.stevenperegrine.simba_cardemo

import java.lang.reflect.Array
import java.util.*

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
}

