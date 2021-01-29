package com.example.taashaadslib.RetrofitClass;

/**
 * Created by Developer on 8/1/2017.
 */

public class PatchResponseModel {

    private int code;

    private Object data;

    private String message;

    private String description;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
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
}
