package com.example.taashaadslib.AdSerever;


import android.app.Activity;
import android.util.Log;

public class LoadAdsClass implements GetAdsClass {
    @Override
    public void getAds(Activity mActivity, String msg) {

        Log.e("KETAN PATEL" , msg);
    }
}
