package com.example.taashaadslib.ModelClasses;

import java.util.List;

public class SMSData {

    public static  List<DataBean> data;

    public static List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String date;
        private String smsContent;
        private String sender;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getSmsContent() {
            return smsContent;
        }

        public void setSmsContent(String smsContent) {
            this.smsContent = smsContent;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }
    }
}
