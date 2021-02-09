package com.example.taashaadsserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.taashaadslib.AdSerever.TaashaAdServer;
import com.example.taashaadslib.Interfaces.GetAdsClass;
import com.example.taashaadslib.AdSerever.LoadAdsClass;
import com.example.taashaadslib.Interfaces.InitTaashaAdServer;


public class MainActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //INITIALIZATION OF SDK
       InitTaashaAdServer mInitTaashaAdServer = new TaashaAdServer();
       mInitTaashaAdServer.init(getApplicationContext() , "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlYXN5cGF5IiwiY3JlYXRlZCI6MTQ5Nzg2NzIwNDU3MSwiZXhwIjo2MTk3Nzg2NzIwNH0.bQS77TdFV4gH05y2L6b7f6hxQ6cJxs3R7Jmg6W7NefFhiCiv_YBqFjSUlts32ukxRFLYvylEWDGMcYrz2lR_pA" ,23.25 ,72.256 );

        //CREATE INSTANCE
        //LOAD ADS
        GetAdsClass mGetAdsClass = new LoadAdsClass();
        mGetAdsClass.getAds(getApplicationContext());

        /*SampleActivity sampleActivity = new SampleActivity();
        sampleActivity.fetchInbox(this);

        *//*for (int i = 0 ; i< sampleActivity.fetchInbox(this).size() ; i++){

            Log.e("MAIN_APP",""+sampleActivity.fetchInbox(this).get(i).getSmsContent());
        }*/

        //SimpleLogging. callAdsApi();


    }

}