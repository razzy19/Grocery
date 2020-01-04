package com.example.grocersapp.Response;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

public class AddOrderResponse {
    @SerializedName("state")
    String state;

    @SerializedName("otp")
    int otp;

    @SerializedName("msg")
    String msg;

    public AddOrderResponse(String state, String msg,int otp) {
        this.state = state;
        this.msg = msg;
        this.otp = otp;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
