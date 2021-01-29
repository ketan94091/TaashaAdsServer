package com.example.taashaadslib.ModelClasses;

/**
 * Created by Svep Developer on 08-11-2017.
 */

public class LoginAuthModel {


    /**
     * code : 1
     * data : {"token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjcnBuaWtvYmFyIiwiY3JlYXRlZCI6MTYwMzk1MzE2Nzk3NSwiZXhwIjoxNjA0NTU3OTY3fQ.BSvlzsXnyOP82LUx0ElqMloPYx0tooKONg2Hs0RwnHlXc0tXVTRcDnp4YhKxtJX5ZSodMj3iuldN3ed4QYLr-w","username":"crpnikobar","userId":2405070,"crpEpId":2405072,"lastSyncTime":1603889741355}
     * message : Success
     * description : null
     */

    private int code;
    private DataBean data;
    private String message;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class DataBean {
        /**
         * token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjcnBuaWtvYmFyIiwiY3JlYXRlZCI6MTYwMzk1MzE2Nzk3NSwiZXhwIjoxNjA0NTU3OTY3fQ.BSvlzsXnyOP82LUx0ElqMloPYx0tooKONg2Hs0RwnHlXc0tXVTRcDnp4YhKxtJX5ZSodMj3iuldN3ed4QYLr-w
         * username : crpnikobar
         * userId : 2405070
         * crpEpId : 2405072
         * lastSyncTime : 1603889741355
         */

        public String token;
        public String username;
        public int userId;
        public int crpEpId;
        public long lastSyncTime;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getCrpEpId() {
            return crpEpId;
        }

        public void setCrpEpId(int crpEpId) {
            this.crpEpId = crpEpId;
        }

        public long getLastSyncTime() {
            return lastSyncTime;
        }

        public void setLastSyncTime(long lastSyncTime) {
            this.lastSyncTime = lastSyncTime;
        }
    }
}
