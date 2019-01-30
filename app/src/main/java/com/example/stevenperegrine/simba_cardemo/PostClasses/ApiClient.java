package com.example.stevenperegrine.simba_cardemo.PostClasses;


import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface ApiClient {

    @Multipart
    @Headers("APIKEY: 0ce2c6f644fa15bfb25520394392af4f835153a6be1beff0c096988d647a97c4")
    @POST("registerCar/")
    Call<Response> uploadImage(@Part("Make") RequestBody make,
                                  @Part("Model") RequestBody model,
                                  @Part("VIN") RequestBody vin,
                                  @Part("from") RequestBody from,
                                  @Part MultipartBody.Part file);
}
