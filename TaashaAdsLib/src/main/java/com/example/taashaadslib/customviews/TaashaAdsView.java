package com.example.taashaadslib.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TaashaAdsView extends androidx.appcompat.widget.AppCompatImageView {
    public TaashaAdsView(@NonNull Context context) {
        super(context);
    }

    public TaashaAdsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TaashaAdsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, 100);

    }
}
