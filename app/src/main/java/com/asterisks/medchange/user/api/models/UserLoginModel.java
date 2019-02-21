package com.asterisks.medchange.user.api.models;

public class UserLoginModel {
    String email;
    String password;

    public UserLoginModel(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
