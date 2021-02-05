package com.example.taashaadslib.AdSerever;

import android.app.Activity;
import android.util.Log;

import com.example.taashaadslib.Interfaces.GetAdsClass;
import com.example.taashaadslib.Interfaces.LoadAds;

public class GetAdsActivity implements LoadAds {


    private GetAdsClass mGetAdsClass =new  LoadAdsClass();

    @Override
    public void loadAds(Activity mActivity, int one, int two, int three, int four) {



    }

    @Override
    public void initAds(Activity mActivity, String strKey) {


        Log.e("GetAdsActivity" ,strKey);

        mGetAdsClass.getAds(mActivity , strKey);

    }
}
