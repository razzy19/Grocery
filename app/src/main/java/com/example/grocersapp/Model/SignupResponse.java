package com.example.grocersapp.Model;

import com.google.gson.annotations.SerializedName;

public class SignupResponse {
    @SerializedName("state")
    private String state;

    public SignupResponse() {
    }

    public SignupResponse(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
