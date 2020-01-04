package com.example.grocersapp.Model;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {

    @SerializedName("state")
    private String state;

    @SerializedName("msg")
    private String msg;

    public DefaultResponse(String state, String msg) {
        this.state = state;
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
