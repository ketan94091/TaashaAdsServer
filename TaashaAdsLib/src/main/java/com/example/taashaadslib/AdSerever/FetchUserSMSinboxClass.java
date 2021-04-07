package com.example.taashaadslib.AdSerever;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.taashaadslib.AlertUtils.AlertClasses;
import com.example.taashaadslib.AppUtils.GlobalFiles;
import com.example.taashaadslib.CommonClasses.SessionManager;
import com.example.taashaadslib.ModelClasses.Keywords;
import com.example.taashaadslib.ModelClasses.SMSData;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FetchUserSMSinboxClass {

    private static SessionManager mSessionManager;
    private static final String TAG = "FetchUserSMSinboxClass";
    private static Cursor cursor = null;

    public static ArrayList<SMSData.DataBean> fetchInbox(Context mContext) {

        mSessionManager = new SessionManager(mContext);
        mSessionManager.openSettings();

        Gson gson1 = new Gson();
        TypeToken<ArrayList<Keywords.KeyWords>> token1 = new TypeToken<ArrayList<Keywords.KeyWords>>() {
        };
        ArrayList<Keywords.KeyWords> mSmsList = gson1.fromJson(mSessionManager.getPreference(GlobalFiles.SMS_FILTER_KEYWORD_LIST), token1.getType());


        ArrayList<SMSData.DataBean> smsData = new ArrayList<>();
        smsData.clear();


        if (!mSessionManager.getPreference(GlobalFiles.SMS_LAST_SYNC_DATE).equalsIgnoreCase("")) {


            String currentString = mSessionManager.getPreference(GlobalFiles.SMS_LAST_SYNC_DATE);
            String[] separated = currentString.split("/");
            String strDay = separated[0];
            String strMonth = separated[1];
            String strYear =separated[2];

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date dateStart = null;
            try {
                dateStart = formatter.parse(strYear+"-"+strMonth+"-"+strDay+ "T00:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String filter = "date>=" + dateStart.getTime();
            final Uri SMS_INBOX = Uri.parse("content://sms/inbox");
            cursor = mContext.getContentResolver().query(SMS_INBOX, new String[]{"_id", "address", "date", "body"}, filter, null, null);
        } else {

            Uri uriSms = Uri.parse("content://sms/inbox");
            cursor = mContext.getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"}, null, null, null);
        }

        cursor.moveToNext();
        while (cursor.moveToNext()) {


            String address = cursor.getString(1);
            String body = cursor.getString(3);
            String date = cursor.getString(2);

            for (int i = 0; i < mSmsList.size(); i++) {

                if (cursor.getString(3).contains(mSmsList.get(i).getKeyword())) {

                    AlertClasses.printLogE("MATCH WORD :", "" + mSmsList.get(i).getKeyword());

                    SMSData.DataBean mDatabean = new SMSData.DataBean();
                    mDatabean.setDate(AlertClasses.getStringDate(Long.valueOf(date)));
                    mDatabean.setSender(address);
                    mDatabean.setSmsContent(body);

                    smsData.add(mDatabean);

                    break;
                }

            }


        }

        //UPDATE LAST SYNC DATE
        mSessionManager.updatePreferenceString(GlobalFiles.SMS_LAST_SYNC_DATE, "" + AlertClasses.getStringDate(System.currentTimeMillis()));

        return smsData;

    }
}
