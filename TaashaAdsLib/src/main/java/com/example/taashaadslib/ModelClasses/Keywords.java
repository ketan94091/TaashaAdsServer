package com.example.taashaadslib.ModelClasses;

import java.util.List;

public class Keywords {

    public static List<KeyWords> data;

    public static List<KeyWords> getData() {
        return data;
    }

    public void setData(List<KeyWords> data) {
        this.data = data;
    }

    public static class KeyWords {

        private String keyword;

        public String getKeyword() {
            return keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }
    }
}
