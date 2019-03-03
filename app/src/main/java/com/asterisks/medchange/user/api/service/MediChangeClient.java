package com.asterisks.medchange.user.api.service;

import com.asterisks.medchange.user.api.models.MedicineLocationArrayModel;
import com.asterisks.medchange.user.api.models.MedicineModel;
import com.asterisks.medchange.user.api.models.PatchModel;
import com.asterisks.medchange.user.api.models.PharmacistModel;
import com.asterisks.medchange.user.api.models.PharmacistModelList;
import com.asterisks.medchange.user.api.models.UserLoginCallbackModel;
import com.asterisks.medchange.user.api.models.UserLoginModel;
import com.asterisks.medchange.user.api.models.UserMedicineModel;
import com.asterisks.medchange.user.api.models.UserRegisterModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;

public interface MediChangeClient {

    @Headers(
            {"Content-Type: application/json"}
    )


    @POST("api/auth/login") Call<UserLoginCallbackModel> login (@Body UserLoginModel userLogin);

    @POST("api/auth/register")Call<UserLoginCallbackModel> register (@Body UserRegisterModel userRegisterModel);
    //@POST("api/auth/logout")

    @GET("api/medicinelist") Call<List<MedicineModel>> getMedicineList();
//    @POST("api/medicineofuser/")
//    Call postMedicine(
//            @Part("medicine") long medicine,
//            @Part("quantityOfMedicine") int quantity,
//            @Part("expiryDate") String date,
//            @Part("expiryPicture") Bitmap Expbitmap,
//            @Part("medicinePicture") Bitmap MedBitmap
//            );
    @POST("api/medicineofuser/")
    Call postMedicine(
            @PartMap Map<String,RequestBody> map
            );
    @GET("api/medicineofuser")
    Call<List<UserMedicineModel>> getListOfMedicinesOfUsers();
    @GET("api/medicinelocation") Call<MedicineLocationArrayModel> getMedcinesWithLocation();
    @GET("api/pharmacistlist") Call<PharmacistModelList> getPharmacistList();
    @PATCH("api/medicineofuser/{id}/") Call<ResponseBody> patchMedicine(@Path("id") int medicineID, @Body PatchModel patchModel);
}
