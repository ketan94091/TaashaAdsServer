package com.example.taashaadslib.AdSerever;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.taashaadslib.R;

import java.net.URL;

public class LoadImageActivity {


    private static Handler handler;
    public static int count = 1;

    public void starttimer() {


    }

    public static void loadAdsView(ImageView imgView) {


        switch (count) {

            case 1:
                imgView.setImageResource(R.drawable.facebook);
                break;
            case 2:
                imgView.setImageResource(R.drawable.whatapp);
                break;
            case 3:
                imgView.setImageResource(R.drawable.instagram);
                break;
            case 4:
                imgView.setImageResource(R.drawable.appdate);
                break;
            case 5:
                imgView.setImageResource(R.drawable.snapchat);
                break;
        }


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                count++;

                if(count == 6){
                    count=1;
                }

                loadAdsView(imgView);
            }
        }, 1000);



        /*Uri uri =  Uri.parse( "https://i.imgur.com/PZ4rb.jpg" );
        imgView.setImageURI(uri);*/

        //Glide.with(mActivity).load("https://i.imgur.com/PZ4rb.jpg").into(imgView);


    }


}