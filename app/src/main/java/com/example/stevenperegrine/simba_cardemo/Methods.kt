package com.example.stevenperegrine.simba_cardemo

import android.media.Image
import retrofit2.Call
import okhttp3.ResponseBody
import retrofit2.http.*
import retrofit2.http.GET
import okhttp3.MultipartBody
import retrofit2.http.POST
import retrofit2.http.Multipart

import retrofit2.http.PUT
import com.squareup.okhttp.RequestBody
import org.web3j.abi.datatypes.Utf8String


interface Methods {
    //Get Ethereum Balance on the rinkeby test net
    @GET
    fun bal(@Url url: String): Call<Models.Balance>

    //Get Register Car with no filtered search
    @Headers("APIKEY: 0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
    @GET("registerCar/")
    fun getCar(): Call<Models.GetCars>

    //Get Register Car with filtered search
    @Headers("APIKEY: 0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
    @GET
    fun getCarFiltered(@Url url: String): Call<Models.GetCars>

    //Get the Car Image
    @Headers("APIKEY: 0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
    @GET
    fun getCarImage(@Url url: String): Call<Models.GetImage>

    //Posting to Register Car No Image
    @Headers("APIKEY: 0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
    //@Multipart
    @FormUrlEncoded
    @POST("registerCar/")
    fun postCar(@Field("Make") Make: Utf8String, @Field("Model") Model: Utf8String, @Field("VIN") VIN: Utf8String, @Field("from") from: Utf8String): Call<Models.PostCar>

    //Posting to Register Car With Image
    @Headers("APIKEY: 0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
    //@Multipart
    @FormUrlEncoded
    @POST("registerCar/")
    fun postCarandImage(@Field("Make") Make: Utf8String,
                        @Field("Model") Model: Utf8String,
                        @Field("VIN") VIN: Utf8String,
                        @Field("from") from: Utf8String,
                        @Field("file[0]") file: Image): Call<Models.PostCar>



   @Headers("APIKEY: 0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
    @POST()
    fun postsigneddata(@Url url:String, @Body signedData: Models.SignedData): Call<Models.SignedData>
}