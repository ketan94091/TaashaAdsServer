package com.example.taashaadslib.RetrofitClass;

import com.example.taashaadslib.AppUtils.GlobalFiles;
import com.example.taashaadslib.ModelClasses.LoginAuthModel;
import com.example.taashaadslib.ModelClasses.TaashaAdsModel;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by baps on 29-03-2017.
 */

public interface UpdateAllAPI {

    //LOGIN API WITH RETROFIT
    @POST(GlobalFiles.AUTH_API)
    Call<LoginAuthModel> loginAuth(@Body JsonObject body);


    @GET()
    Call<TaashaAdsModel> getAdsFromServer(@Url String url);



}
