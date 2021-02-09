package com.example.taashaadslib.RetrofitClass;

import com.example.taashaadslib.ModelClasses.TaashaAdsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by baps on 29-03-2017.
 */

public interface UpdateAllAPI {

    @GET()
    Call<TaashaAdsModel> getAdsFromServer(@Url String url);



}
