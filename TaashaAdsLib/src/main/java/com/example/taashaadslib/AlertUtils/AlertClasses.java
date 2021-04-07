package com.example.taashaadslib.AlertUtils;

import android.util.Log;

import com.example.taashaadslib.AppUtils.GlobalFiles;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class AlertClasses {

    public static void printLogE(String strTAG, String strMessage) {

        if (true){
        Log.e(strTAG, strMessage);
        }

    };

    public static String getStringDate(Long convertDate) {
        Date date = new Date(convertDate);
        SimpleDateFormat df2 = new SimpleDateFormat(GlobalFiles.COMMON_DATE_FORMAT);
        String dateText = df2.format(date);

        return dateText;
    }

    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


}
