package com.asterisks.medchange.user.api.models;

public class UserLoginCallbackModel {
    UserModel user;
    String token;

    public String getToken() {
        return token;
    }
}
