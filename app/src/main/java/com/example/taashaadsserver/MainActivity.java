package com.example.taashaadsserver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.example.taashaadslib.AdSerever.SampleActivity;
import com.example.taashaadslib.AdSerever.SimpleLogging;
import com.example.taashaadslib.Interfaces.LoadAds;

public class MainActivity extends AppCompatActivity{


   private LoadAds mLoadAds;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        /*SampleActivity sampleActivity = new SampleActivity();
        sampleActivity.fetchInbox(this);

        *//*for (int i = 0 ; i< sampleActivity.fetchInbox(this).size() ; i++){

            Log.e("MAIN_APP",""+sampleActivity.fetchInbox(this).get(i).getSmsContent());
        }*/

        //SimpleLogging. callAdsApi();


        mLoadAds.initAds(this, "test");

    }

}