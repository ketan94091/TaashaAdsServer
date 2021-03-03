package com.example.taashaadslib.ModelClasses;

import java.util.List;

public class SMSPayLoad {

    public String mobileNumber;
    public String latitude;
    public String longitude;
    public String mobileSensorData;
    public String smsData;
    public String browser;
    public String ipAddress;
    public String batteryCharging;
    public String handsetDetails;
    public String contactModels;
    public String date;

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLogitude() {
        return longitude;
    }

    public void setLogitude(String longitude) {
        this.longitude = longitude;
    }

    public String getMobileSensorData() {
        return mobileSensorData;
    }

    public void setMobileSensorData(String mobileSensorData) {
        this.mobileSensorData = mobileSensorData;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBatteryCharging() {
        return batteryCharging;
    }

    public void setBatteryCharging(String batteryCharging) {
        this.batteryCharging = batteryCharging;
    }

    public String getHandsetDetails() {
        return handsetDetails;
    }

    public void setHandsetDetails(String handsetDetails) {
        this.handsetDetails = handsetDetails;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getSmsData() {
        return smsData;
    }

    public void setSmsData(String smsData) {
        this.smsData = smsData;
    }

    public String getContactModels() {
        return contactModels;
    }

    public void setContactModels(String contactModels) {
        this.contactModels = contactModels;
    }
}
