package com.example.taashaadslib.AdSerever;

import android.app.Activity;
import android.util.Log;

import com.example.taashaadslib.Interfaces.LoadAds;

class GetAdsActivity implements LoadAds {



    @Override
    public void loadAds(Activity mActivity, int one, int two, int three, int four) {

        Log.e("GetAdsActivity" ,"GetAdsActivity");


    }

    @Override
    public void initAds(Activity mActivity, String strKey) {




    }
}
