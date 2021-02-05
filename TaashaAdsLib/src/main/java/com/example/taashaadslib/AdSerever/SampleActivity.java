package com.example.taashaadslib.AdSerever;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.taashaadslib.ModelClasses.SMSData;
import com.example.taashaadslib.R;

import java.util.ArrayList;

public class SampleActivity extends AppCompatActivity {

    private Activity mActivity;
    private Context mContext;
    private RecyclerView recycleSmsList;
    private ListView listSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        setId();


    }

    public void setId() {

        mActivity = SampleActivity.this;
        mContext = SampleActivity.this;

        ListView  listSms=(ListView)findViewById(R.id.listSms);
/*

        if(fetchInbox()!=null)
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchInbox());
            listSms.setAdapter(adapter);
        }
*/

    }

    public static ArrayList<SMSData.DataBean> fetchInbox(Activity mActivity)
    {
       ArrayList<SMSData.DataBean> smsData = new ArrayList<>();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = mActivity.getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"},null,null,null);



        cursor.moveToFirst();
        while  (cursor.moveToNext())
        {
            String address = cursor.getString(1);
            String body = cursor.getString(3);
            String date = cursor.getString(2);

          //  smsData.add("Address==> "+address+"\n\nSMS==> "+body);

            SMSData.DataBean mDatabean = new SMSData.DataBean();
            mDatabean.setDate(date);
            mDatabean.setSender(address);
            mDatabean.setSmsContent(body);

            smsData.add(mDatabean);

        }


        return smsData;

    }
}