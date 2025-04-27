package com.example.mobileprogramming_termproject;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface BarcodeApi {
    @Multipart
    @POST("/decode")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part image);
}
