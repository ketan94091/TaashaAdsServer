package com.example.taashaadslib.RetrofitClass;

import com.example.taashaadslib.AppUtils.GlobalFiles;
import com.example.taashaadslib.ModelClasses.AuthPayload;
import com.example.taashaadslib.ModelClasses.FiltereKeyWords;
import com.example.taashaadslib.ModelClasses.SMSPayLoad;
import com.example.taashaadslib.ModelClasses.TaashaAdsModel;
import com.example.taashaadslib.ModelClasses.UserDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by baps on 29-03-2017.
 */

public interface UpdateAllAPI {

    @GET()
    Call<TaashaAdsModel> getAdsFromServer(@Url String url);

    @POST(GlobalFiles.POST_UPLOAD_SMS_PAYLOAD)
    Call<SMSPayLoad> uploadSMSPayload(@Body SMSPayLoad smsPayLoad);

    @POST(GlobalFiles.AUTH_URL)
    Call<AuthPayload> getAuthToken(@Body UserDTO userDTO);

    @GET()
    Call<FiltereKeyWords> getFilterKeywords(@Url String url);

}
