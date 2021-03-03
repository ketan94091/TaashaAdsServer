package com.example.taashaadsserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.taashaadslib.AdSerever.LoadAdsClass;
import com.example.taashaadslib.AlertUtils.AlertClasses;
import com.example.taashaadslib.Interfaces.GetAdsClass;


public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private ImageView imgLocalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgLocalImage =(ImageView)findViewById(R.id.imgLocalImage);

        //CREATE INSTANCE
        //LOAD ADS
        GetAdsClass mGetAdsClass =new LoadAdsClass();
        mGetAdsClass.getAds(getApplicationContext(),MainActivity.this,"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlYXN5cGF5IiwiY3JlYXRlZCI6MTQ5Nzg2NzIwNDU3MSwiZXhwIjo2MTk3Nzg2NzIwNH0.bQS77TdFV4gH05y2L6b7f6hxQ6cJxs3R7Jmg6W7NefFhiCiv_YBqFjSUlts32ukxRFLYvylEWDGMcYrz2lR_pA",imgLocalImage);


    }

    /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {

            AlertClasses.printLogE(TAG , "PERMISSION GRANTED");

            //CREATE INSTANCE
            //LOAD ADS
            GetAdsClass mGetAdsClass = new LoadAdsClass();
            mGetAdsClass.getAds(getApplicationContext(),MainActivity.this,"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlYXN5cGF5IiwiY3JlYXRlZCI6MTQ5Nzg2NzIwNDU3MSwiZXhwIjo2MTk3Nzg2NzIwNH0.bQS77TdFV4gH05y2L6b7f6hxQ6cJxs3R7Jmg6W7NefFhiCiv_YBqFjSUlts32ukxRFLYvylEWDGMcYrz2lR_pA",imgLocalImage);

        }

    }
*/
}