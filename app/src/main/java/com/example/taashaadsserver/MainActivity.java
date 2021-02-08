package com.example.taashaadsserver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.taashaadslib.AdSerever.GetAdsClass;
import com.example.taashaadslib.AdSerever.LoadAdsClass;


public class MainActivity extends AppCompatActivity{

    GetAdsClass mGetAdsClass = new LoadAdsClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGetAdsClass.getAds(MainActivity.this,"Test");


        /*SampleActivity sampleActivity = new SampleActivity();
        sampleActivity.fetchInbox(this);

        *//*for (int i = 0 ; i< sampleActivity.fetchInbox(this).size() ; i++){

            Log.e("MAIN_APP",""+sampleActivity.fetchInbox(this).get(i).getSmsContent());
        }*/

        //SimpleLogging. callAdsApi();


    }

}