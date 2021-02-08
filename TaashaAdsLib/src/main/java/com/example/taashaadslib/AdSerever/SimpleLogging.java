package com.example.taashaadslib.AdSerever;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.taashaadslib.AppUtils.GlobalFiles;
import com.example.taashaadslib.ModelClasses.LoginAuthModel;
import com.example.taashaadslib.ModelClasses.SMSData;
import com.example.taashaadslib.ModelClasses.TaashaAdsModel;
import com.example.taashaadslib.RetrofitClass.UpdateAllAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

  public  class SimpleLogging {

    private static final String TAG = "SimpleLogging";


    public static void logPrintErrorLog(String strPrintErrorLog){



        //callTestApi();
        callAdsApi();

        /*if(fetchInbox()!=null)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchInbox());
            listSms.setAdapter(adapter);
        }*/
    }

      public static ArrayList<SMSData.DataBean> fetchInbox(Activity mActivity)
      {
          ArrayList<SMSData.DataBean> smsData = new ArrayList<>();

          Uri uriSms = Uri.parse("content://sms/inbox");
          Cursor cursor = mActivity.getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);



          cursor.moveToFirst();
          while  (cursor.moveToNext())
          {
              String address = cursor.getString(1);
              String body = cursor.getString(3);
              String date = cursor.getString(2);

              //  smsData.add("Address==> "+address+"\n\nSMS==> "+body);

              SMSData.DataBean mDatabean = new SMSData.DataBean();
              mDatabean.setDate(date);
              mDatabean.setSender(address);
              mDatabean.setSmsContent(body);

              smsData.add(mDatabean);

          }


          return smsData;

      }


    public static void callTestApi() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request original = chain.request();

                okhttp3.Request request = original.newBuilder()
                        .header("Content-Type", "application/json; charset=utf-8")
                        // .header("Authorization", sessionManager.getToken(mContext))
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalFiles.COMMON_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.client(client)
                .client(client.newBuilder().connectTimeout(30000, TimeUnit.SECONDS).readTimeout(30000, TimeUnit.SECONDS).writeTimeout(30000, TimeUnit.SECONDS).build())
                .build();

        UpdateAllAPI patchService1 = retrofit.create(UpdateAllAPI.class);
        JsonObject paramObject = new JsonObject();
        paramObject.addProperty("username", "EBB12BE87E78AB26BC5C176BD22830AB");
        paramObject.addProperty("password","D0DA388BFBB3D9C7E8A3ACD030768BD4");
        paramObject.addProperty("deviceId", "ccc6f4218a3e9357");

        Call<LoginAuthModel> call = patchService1.loginAuth(paramObject);


        call.enqueue(new Callback<LoginAuthModel>() {
            @Override
            public void onResponse(Call<LoginAuthModel> call, retrofit2.Response<LoginAuthModel> response) {



                if (response.isSuccessful()) {

                    Gson gson = new GsonBuilder()
                            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                            .serializeNulls()
                            .create();

                    int intCode = response.body().getCode();

                    Log.e("Login response", "Response @ Login: " + gson.toJson(response.body()));

                    switch (intCode) {
                        case 1:

                            break;

                        case 2:


                            break;
                    }

                }
            }

            @Override
            public void onFailure(Call<LoginAuthModel> call, Throwable t) {


            }
        });
    }

      public static void callAdsApi() {

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
                          .header("X-Auth-Token", "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlYXN5cGF5IiwiY3JlYXRlZCI6MTQ5Nzg2NzIwNDU3MSwiZXhwIjo2MTk3Nzg2NzIwNH0.bQS77TdFV4gH05y2L6b7f6hxQ6cJxs3R7Jmg6W7NefFhiCiv_YBqFjSUlts32ukxRFLYvylEWDGMcYrz2lR_pA")
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
