package com.example.grocersapp.Model;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("state")
    private String state;

    @SerializedName("token")
    private String token;

    @SerializedName("name")
    private String name;

    @SerializedName("contact")
    private String contact;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponse(String state) {
        this.state = state;
    }

    public LoginResponse() {
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
        private boolean error;
    private String message;
    private User user;

    public LoginResponse(boolean error, String message, User user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }
}
