package com.example.stevenperegrine.simba_cardemo.PostClasses;

import android.content.Context;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import com.example.stevenperegrine.simba_cardemo.Models;
import java.io.File;

public class PostService {

    public void postWithImage(Context context, String Make,String Model,String VIN,String from, File imageFile, Callback<Models.PostCar> callback) {

        // create upload service client
        ApiClient service = ApiClientBuilder.getMGClient();

        RequestBody requestFile =
                RequestBody.create(MediaType.parse(FileUtils.getFileExtension(imageFile.getAbsolutePath())), imageFile);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("fileToUpload", imageFile.getName(), requestFile);

        // add another part within the multipart request



        RequestBody postMake =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Make);

        RequestBody postModel =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Model);

        RequestBody postVIN =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, VIN);

        RequestBody postFrom =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, from);

        Call<Models.PostCar> result =  service.uploadImage(postMake,postModel,postVIN,postFrom, body);

        result.enqueue(callback);

    }
}
