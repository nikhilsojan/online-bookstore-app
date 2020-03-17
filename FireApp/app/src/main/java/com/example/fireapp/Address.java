package com.example.fireapp;

public class Address {
    String name;
    String number;
    String housename;
    String city;
    String state;
    String pincode;

    public Address(String name, String number, String housename, String city, String state, String pincode) {
        this.name = name;
        this.number = number;
        this.housename = housename;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getHousename() {
        return housename;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getPincode() {
        return pincode;
    }
}
