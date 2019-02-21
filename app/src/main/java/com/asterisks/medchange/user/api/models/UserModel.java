package com.asterisks.medchange.user.api.models;

public class UserModel {
    long id;
    String username;
    String email;
    String phoneNumber;
    String aadhaarNo;
    String totalCredits;
    String address;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public String getTotalCredits() {
        return totalCredits;
    }

    public String getAddress() {
        return address;
    }
}

