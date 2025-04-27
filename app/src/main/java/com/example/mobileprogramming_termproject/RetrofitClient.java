package com.example.mobileprogramming_termproject;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit = null;
    private static Retrofit naverRetrofit = null;
    public static Retrofit getClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static NaverSearchAPI getApiService() {
        if (naverRetrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);  // 요청/응답 전체 로그 출력

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();

            naverRetrofit = new Retrofit.Builder()
                    .baseUrl("https://openapi.naver.com/")  // ✅ 정확히 이거
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return naverRetrofit.create(NaverSearchAPI.class);
    }
}
