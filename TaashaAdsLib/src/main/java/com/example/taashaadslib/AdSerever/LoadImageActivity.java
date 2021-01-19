package com.example.taashaadslib.AdSerever;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.taashaadslib.R;

import java.net.URL;

public class LoadImageActivity {


    private static Handler handler;
    public static int count = 1;

    public void starttimer() {

       /* if ((position + 1) % showPromotion == 0) {

            holder.adView.loadAd(adRequest);
        }else {
            holder.adView.setVisibility(View.GONE);
        }*/


    }

    public static void loadAdsView(ImageView imgView,  Activity mActivity) {


        switch (count) {

            case 1:
               // imgView.setImageResource(R.drawable.facebook);
                Glide.with(mActivity).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(imgView);
                break;
            case 2:
               // imgView.setImageResource(R.drawable.whatapp);
                Glide.with(mActivity).load("https://homepages.cae.wisc.edu/~ece533/images/baboon.png").into(imgView);
                break;
            case 3:
                //imgView.setImageResource(R.drawable.instagram);
                Glide.with(mActivity).load("https://homepages.cae.wisc.edu/~ece533/images/frymire.png").into(imgView);
                break;
            case 4:
               // imgView.setImageResource(R.drawable.appdate);
                Glide.with(mActivity).load("https://homepages.cae.wisc.edu/~ece533/images/tulips.png").into(imgView);
                break;
            case 5:
                //imgView.setImageResource(R.drawable.snapchat);
                Glide.with(mActivity).load("https://i.imgur.com/PZ4rb.jpg").into(imgView);
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

                loadAdsView(imgView,mActivity);
            }
        }, 5000);



        /*Uri uri =  Uri.parse( "https://i.imgur.com/PZ4rb.jpg" );
        imgView.setImageURI(uri);*/




    }


}