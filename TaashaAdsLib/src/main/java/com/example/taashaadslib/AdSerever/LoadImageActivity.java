package com.example.taashaadslib.AdSerever;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.taashaadslib.R;

public  class LoadImageActivity  {


    public static void loadAdsView(ImageView imgView){

        //imgView.setImageResource(R.drawable.test);

        Uri uri =  Uri.parse( "https://i.imgur.com/PZ4rb.jpg" );
        imgView.setImageURI(uri);

    }



}