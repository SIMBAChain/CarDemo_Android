package com.example.stevenperegrine.simba_cardemo;

import android.graphics.ColorSpace;
import okhttp3.*;
import retrofit2.Retrofit;

import java.io.File;
import java.io.IOException;

public class PostJava{
    public void uploadToServer(String filePath) {
        Retrofit retrofit = NetworkClient.getRetrofitClient(this);
        Methods uploadAPIs = retrofit.create(Methods.class);
        //Create a file object using file path
        File file = new File(filePath);
        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody postMake = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        RequestBody postModel = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        RequestBody postVIN = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        RequestBody postFrom = RequestBody.create(MediaType.parse("text/plain"), "image-type");
        //
        Call call = (Call) uploadAPIs.postCarandImage(postMake,postModel,postVIN,postFrom,part);
        call.enqueue(new Callback() {


            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}