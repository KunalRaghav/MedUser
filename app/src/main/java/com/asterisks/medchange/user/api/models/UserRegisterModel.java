package com.asterisks.medchange.user.api.models;

public class UserRegisterModel {
    String username;
    String email;
    String phoneNumber;
    String aadhaarNo;
    String address;
    String password;

    public UserRegisterModel(String username, String email, String phoneNumber, String aadhaarNo, String address, String password) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.aadhaarNo = aadhaarNo;
        this.address = address;
        this.password = password;
    }
}
