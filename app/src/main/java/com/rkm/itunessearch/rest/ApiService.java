package com.rkm.itunessearch.rest;



import com.rkm.itunessearch.model.ItuneResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search")
    Call<ItuneResponse> getSearchResult(@Query("term") String term);
}
