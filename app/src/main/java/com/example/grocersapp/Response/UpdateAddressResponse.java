package com.example.grocersapp.Response;

import com.google.gson.annotations.SerializedName;

public class UpdateAddressResponse {
    @SerializedName("state")
    String state;
    @SerializedName("msg")
    String msg;

    public UpdateAddressResponse(String state, String msg) {
        this.state = state;
        this.msg = msg;
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
}
