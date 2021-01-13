package com.example.taashaadslib.AdSerever;

import android.util.Log;

import androidx.appcompat.widget.DialogTitle;

public class SimpleLogging {

    private static final String TAG = "SimpleLogging";

    public static void logPrintErrorLog(String strPrintErrorLog){

        Log.e(TAG,strPrintErrorLog);
    }


}
