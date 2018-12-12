package com.example.stevenperegrine.simba_cardemo

import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.http.GET




interface Methods {
    //Get Ethereum Balance on the rinkeby test net
    @GET
    fun bal(@Url url: String): Call<Models.Balance>

    //Get Register Car with no filtered search
    @Headers("APIKEY: 0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
    @GET("registerCar/")
    fun getCar(): Call<Models.GetCars>
}