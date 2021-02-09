package com.example.taashaadslib.AdSerever;


import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.taashaadslib.AlertUtils.AlertClasses;
import com.example.taashaadslib.AppUtils.GlobalFiles;
import com.example.taashaadslib.CommonClasses.SessionManager;
import com.example.taashaadslib.Interfaces.GetAdsClass;
import com.example.taashaadslib.ModelClasses.TaashaAdsModel;
import com.example.taashaadslib.RetrofitClass.UpdateAllAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoadAdsClass implements GetAdsClass {

    private static final String TAG = "LoadAdsClass";

    private int height;
    private int width;


    @Override
    public void getAds(Context mContext) {




        SessionManager mSessionManager = new SessionManager(mContext);
        mSessionManager.openSettings();

        AlertClasses.printLogE(TAG , "STATE : "+mSessionManager.getPreference(GlobalFiles.STATE));
        AlertClasses.printLogE(TAG , "CITY : "+mSessionManager.getPreference(GlobalFiles.CITY));


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                okhttp3.Request requestOriginal = chain.request();

                okhttp3.Request request = requestOriginal.newBuilder()
                        //.header("Content-Type", "application/json; charset=utf-8")
                        .header("X-Auth-Token", mSessionManager.getPreference(GlobalFiles.KEY))
                        .method(requestOriginal.method(), requestOriginal.body())
                        .build();


                return chain.proceed(request);
            }
        });
        final OkHttpClient httpClient1 = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalFiles.COMMON_URL)
                .client(httpClient1.newBuilder().connectTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES).writeTimeout(10, TimeUnit.MINUTES).build())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient1)
                .build();

        UpdateAllAPI patchService1 = retrofit.create(UpdateAllAPI.class);

        Call<TaashaAdsModel> call = patchService1.getAdsFromServer(GlobalFiles.GET_ADS_API+"?latitude=23.25&longitude=72.256&height=50&width=300&gender=1&agegroup=2&household=2&incomesource=2&baserotation=3&currentrotation=0&creativeid=9&useruniqueid=3e96eb71-3778-46fd-bcd8-fccd820125cd&kioskid=abs1234&city=ahmedabad&state=gujarat&mediatorname=0&isinrotation=false&bundleid=com.aerserv.www&appversion=1.0&network=wifi&dnt=false&inttype=1&url=http://www.aerserv.com&make=Apple&model=iPhone4&os=iOS&osv=7.1&type=phone&locationsource=2&pchain=");


        call.enqueue(new Callback<TaashaAdsModel>() {
            @Override
            public void onResponse(Call<TaashaAdsModel> call, retrofit2.Response<TaashaAdsModel> response) {



                if (response.isSuccessful()) {

                    Gson gson = new GsonBuilder()
                            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                            .serializeNulls()
                            .create();



                    Log.e("Response", "Response @ : " + gson.toJson(response.body().getSourceURL()));

                }
            }

            @Override
            public void onFailure(Call<TaashaAdsModel> call, Throwable t) {


            }
        });
    }

}
