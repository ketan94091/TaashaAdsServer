package com.example.taashaadslib.AdSerever;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Build;

import com.example.taashaadslib.AppUtils.GlobalFiles;
import com.example.taashaadslib.CommonClasses.SessionManager;
import com.example.taashaadslib.Interfaces.InitTaashaAdServer;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TaashaAdServer implements InitTaashaAdServer {

    private Context mContext;
    private String key;//DONE
    private double latitude ;//DONE
    private double longitude;//DONE
    private String city;//DONE
    private String state;//DONE
    private String appversion;//DONE
    private String os;//DONE
    private String osv;//DONE
    private String type;//DONE
    private String network;//DONE
    private String make;//DONE
    private String model;//DONE
    private int kioskid;//DONE
    private String bundleid;//DONE
    private int dnt;//DONE
    private int inttype;//DONE
    private String url;//DONE
    private String locationsource;//DONE
    private String pchain;//DONE
    private int gender;//DONE
    private int agegroup;//DONE
    private int household;//DONE
    private int incomesource;//DONE
    private int baserotation;//DONE
    private int currentrotation;//DONE
    private int creativeid;//DONE
    private int useruniqueid;//DONE
    private int mediatorname;//DONE
    private int isinrotation;//DONE


    @Override
    public void init(Context mContext, String key, double latitude, double longitude) {
        this.mContext =mContext;
        this.key = key;
        this.latitude =latitude;
        this.longitude = longitude;

        SessionManager mSessionManager = new SessionManager(mContext);
        mSessionManager.openSettings();

        //SAVE KEY
        mSessionManager.updatePreferenceString(GlobalFiles.KEY , key);

        //SAVE LATITUDE
        mSessionManager.updatePreferenceString(GlobalFiles.LATITUDE , ""+latitude);

        //SAVE LONGITUDE
        mSessionManager.updatePreferenceString(GlobalFiles.LONGITUDE , ""+longitude);

        //SAVE HEIGHT
        mSessionManager.updatePreferenceString(GlobalFiles.HEIGHT , "50");

        //SAVE WIDTH
        mSessionManager.updatePreferenceString(GlobalFiles.WIDTH , "300");

        //SAVE GENDER
        mSessionManager.updatePreferenceString(GlobalFiles.GENDER , "1");

        //SAVE AGE GROUP
        mSessionManager.updatePreferenceString(GlobalFiles.AGEGROUP , "2");

        //SAVE HOUSE HOLD
        mSessionManager.updatePreferenceString(GlobalFiles.HOUSEHOLD , "2");

        //SAVE INCOME SOURCE
        mSessionManager.updatePreferenceString(GlobalFiles.INCOM_SOURCE , "2");

        //SAVE BASE ROTATION
        mSessionManager.updatePreferenceString(GlobalFiles.BASE_R0TATION , "3");

        //SAVE CURRENT ROTATION
        mSessionManager.updatePreferenceString(GlobalFiles.CURRENT_R0TATION , "9");

        //SAVE CREATIVE ID
        mSessionManager.updatePreferenceString(GlobalFiles.CREATIVE_ID , "0");

        //SAVE USER UNIQUE ID
        mSessionManager.updatePreferenceString(GlobalFiles.USER_UNIQUE_ID , "3e96eb71-3778-46fd-bcd8-fccd820125cd");

        //SAVE KIOSK ID
        mSessionManager.updatePreferenceString(GlobalFiles.KIOSK_ID , "abs1234");

        //SAVE CITY & STATE NAME FROM LATITUDE
        //TYPE 1= CITY
        mSessionManager.updatePreferenceString(GlobalFiles.CITY , ""+getCityName(mContext,latitude , longitude,1));

        //TYPE 2= STATE
        mSessionManager.updatePreferenceString(GlobalFiles.STATE , ""+getCityName(mContext,latitude , longitude,2));

        //SAVE MEDIATOR NAME
        mSessionManager.updatePreferenceString(GlobalFiles.MEDIATOR_NAME ,  "0");

        //SAVE IS IN ROTATION
        mSessionManager.updatePreferenceString(GlobalFiles.IS_IN_ROTATION ,  "false");

        //SAVE BUNDLE ID
        mSessionManager.updatePreferenceString(GlobalFiles.BUNDLE_ID , "com.aerserv.www");

        //GET APPLICATION VERSION
        mSessionManager.updatePreferenceString(GlobalFiles.APPLICATION_VERSION , getAppversion(mContext));

        //SAVE NETWORK TYPE
        mSessionManager.updatePreferenceString(GlobalFiles.DEVICE_NETWORK_TYPE , chkNetwrokType(mContext));

        //SAVE INT TYPE
        mSessionManager.updatePreferenceString(GlobalFiles.DNT , "false");

        //SAVE INT TYPE
        mSessionManager.updatePreferenceString(GlobalFiles.INT_TYPE , "1");

        //SAVE URL
        mSessionManager.updatePreferenceString(GlobalFiles.URL , "http://www.aerserv.com");

        //SAVE MAKE (MANUFACTURER)
        mSessionManager.updatePreferenceString(GlobalFiles.DEVICE_MANUFACTURER , ""+ Build.MANUFACTURER);

        //SAVE DEVICE MODEL
        mSessionManager.updatePreferenceString(GlobalFiles.DEVICE_MODEL , ""+ Build.MODEL);

        //SAVE DEVICE OS
        mSessionManager.updatePreferenceString(GlobalFiles.APPLICATION_OS ,"Android");

        //SAVE DEVICE OS VERSION
        mSessionManager.updatePreferenceString(GlobalFiles.APPLICATION_OS_VERSION , ""+ Build.VERSION.SDK_INT);

        //SAVE DEVICE TYPE
        mSessionManager.updatePreferenceString(GlobalFiles.DEVICE_TYPE , "MOBILE");

        //SAVE LOCATION SOURCE
        mSessionManager.updatePreferenceString(GlobalFiles.LOCATION_SOURCE , "2");

        //SAVE PCHAIN
        mSessionManager.updatePreferenceString(GlobalFiles.PCHAIN , "0");

    }

    public String getCityName(Context mContext ,double latitude, double longitude, int typeOfValue) {


        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getLocality();
        String stateName = addresses.get(0).getAdminArea();

        if(typeOfValue == 1){
            return cityName;
        }else {
            return stateName;
        }

    }

    public String getAppversion(Context mContext) {

        String version = "0";
        try {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            version = pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return version;
    }

    public String chkNetwrokType(Context mContext) {

        String dataType = "";
        final ConnectivityManager connMgr = (ConnectivityManager)
                mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        final android.net.NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final android.net.NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi.isConnectedOrConnecting()) {
            dataType = "Wifi";
        } else if (mobile.isConnectedOrConnecting()) {
            dataType = "Mobile";
        } else {
            dataType = "No Network";
        }

        return dataType;
    }



}
