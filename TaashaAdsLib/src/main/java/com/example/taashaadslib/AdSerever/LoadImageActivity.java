package com.example.taashaadslib.AdSerever;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.taashaadslib.R;

import java.net.URL;

public  class LoadImageActivity  {


    public static void loadAdsView(ImageView imgView , Activity mActivity){

        imgView.setImageResource(R.drawable.whatapp);

        /*Uri uri =  Uri.parse( "https://i.imgur.com/PZ4rb.jpg" );
        imgView.setImageURI(uri);*/

        //Glide.with(mActivity).load("https://i.imgur.com/PZ4rb.jpg").into(imgView);


    }



}