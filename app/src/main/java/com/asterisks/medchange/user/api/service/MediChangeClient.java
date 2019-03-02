package com.asterisks.medchange.user.api.service;

import com.asterisks.medchange.user.api.models.MedicineModel;
import com.asterisks.medchange.user.api.models.UserLoginCallbackModel;
import com.asterisks.medchange.user.api.models.UserLoginModel;
import com.asterisks.medchange.user.api.models.UserRegisterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MediChangeClient {

    @Headers(
            {"Content-Type: application/json"}
    )


    @POST("api/auth/login") Call<UserLoginCallbackModel> login (@Body UserLoginModel userLogin);
    @POST("api/auth/register")Call<UserLoginCallbackModel> register (@Body UserRegisterModel userRegisterModel);
    //@POST("api/auth/logout")
    @GET("api/medicinelist") Call<List<MedicineModel>> getMedicineList();
}
