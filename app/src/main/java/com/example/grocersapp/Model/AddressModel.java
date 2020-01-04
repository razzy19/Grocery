package com.example.grocersapp.Model;

public class AddressModel {

    String address;
    String addresstype;

    public AddressModel(String address, String addresstype) {
        this.address = address;
        this.addresstype = addresstype;
    }

    public String getAddress() {
        return address;
    }

    public String getAddresstype() {
        return addresstype;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddresstype(String addresstype) {
        this.addresstype = addresstype;
    }

}
