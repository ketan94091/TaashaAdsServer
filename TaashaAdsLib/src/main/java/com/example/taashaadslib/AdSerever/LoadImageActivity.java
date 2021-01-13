package com.example.taashaadslib.AdSerever;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.taashaadslib.R;

public  class LoadImageActivity extends AppCompatActivity {

    public static ImageView imgLocalImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_image);

        imgLocalImage = (ImageView)findViewById(R.id.imgLocalImage);
    }

}