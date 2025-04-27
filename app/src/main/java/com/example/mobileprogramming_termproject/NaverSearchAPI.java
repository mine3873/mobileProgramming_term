package com.example.mobileprogramming_termproject;

import com.example.mobileprogramming_termproject.data.NaverSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NaverSearchAPI {
    @Headers({
            "X-Naver-Client-Id: k5hRchSH1jTJgfmCpWUa",
            "X-Naver-Client-Secret: 21SpkOZzRi"
    })
    @GET("/v1/search/shop.json")  // ✅ 앞에 / 포함!
    Call<NaverSearchResponse> searchProduct(
            @Query("query") String query,
            @Query("display") int displayCount
    );
}
