package com.example.grocersapp.Response;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AddressResponse {
    @SerializedName("state")
    private String state;

    @SerializedName("data")
    private ArrayList<Addresses> addresses;

    public ArrayList<Addresses> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Addresses> addresses) {
        this.addresses = addresses;
    }

    public AddressResponse(String state, ArrayList<Addresses> addresses) {
        this.state = state;
        this.addresses = addresses;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static class Addresses{
        @SerializedName("address")
        private String address;

        @SerializedName("address_type")
        private String addressType;

        @SerializedName("id")
        private int id;

        public Addresses() {
        }

        public Addresses(String address, String addressType,int id) {
            this.address = address;
            this.addressType = addressType;
            this.id=id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAddressType() {
            return addressType;
        }

        public void setAddressType(String addressType) {
            this.addressType = addressType;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


}




