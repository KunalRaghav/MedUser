package com.asterisks.medchange.user.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.api.models.MedicineModel;
import com.asterisks.medchange.user.api.models.UserMedicineModel;
import com.asterisks.medchange.user.api.service.MediChangeClient;
import com.asterisks.medchange.user.constants.StringKeys;

import java.io.IOException;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SellMedicinesActivity extends AppCompatActivity {
    List<UserMedicineModel> userMedsList;
    private static final String TAG = "SellMedicinesActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_medicine);
        SharedPreferences sp = getSharedPreferences(StringKeys.APP_PREFS,MODE_PRIVATE);
        final String token = sp.getString(StringKeys.TOKEN_PREF,"NA");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request().newBuilder().addHeader("Authorization","token "+token).build();
//                        HttpUrl url = original.url();
//                        HttpUrl newHttpUrl = url.newBuilder().addQueryParameter("token",token).build();
//                        Request.Builder builder = original.newBuilder().url(url);
//                        Request request = builder.addHeader("Authorization","token " + token).build();
                        return chain.proceed(original);
                    }
                }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://206.189.133.177")
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        MediChangeClient mediChangeClient = retrofit.create(MediChangeClient.class);
        Call<List<UserMedicineModel>> getListOfMedicinesOfUsers = mediChangeClient.getListOfMedicinesOfUsers();
        getListOfMedicinesOfUsers.enqueue(new Callback<List<UserMedicineModel>>() {
            @Override
            public void onResponse(Call<List<UserMedicineModel>> call, Response<List<UserMedicineModel>> response) {
                if(response.isSuccessful()){
                    userMedsList=response.body();
                    Toast.makeText(getBaseContext(),"Successfully got medicines",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getBaseContext(),"Failed",Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onResponse: Failed:\n"+response.toString());
                }
            }

            @Override
            public void onFailure(Call<List<UserMedicineModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
