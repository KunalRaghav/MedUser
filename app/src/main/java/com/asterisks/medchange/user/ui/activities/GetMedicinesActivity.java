package com.asterisks.medchange.user.ui.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.api.models.MedicineModel;
import com.asterisks.medchange.user.api.service.MediChangeClient;
import com.asterisks.medchange.user.ui.adapter.GetMedicineListAdapter;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetMedicinesActivity extends AppCompatActivity {

    private static final String TAG = "GetMedicinesActivity";
    RecyclerView MedicinesRV;
    GetMedicineListAdapter medicineListAdapter;
    ProgressBar mProgresBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_medicines);
        MedicinesRV = findViewById(R.id.get_medicine_activity_recycler_view);
        mProgresBar = findViewById(R.id.loader);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://authpreviewapi.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();
        final MediChangeClient mediChangeClient = retrofit.create(MediChangeClient.class);
        Call<List<MedicineModel>> getMedicineList = mediChangeClient.getMedicineList();
        Log.d(TAG, "onCreate: TESTING FUNCTION");
        getMedicineList.enqueue(new Callback<List<MedicineModel>>() {
            @Override
            public void onResponse(Call<List<MedicineModel>> call, Response<List<MedicineModel>> response) {
                if(response.isSuccessful()){
                    medicineListAdapter = new GetMedicineListAdapter(response.body());
                    MedicinesRV.setAdapter(medicineListAdapter);
                    MedicinesRV.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                    Log.d(TAG, "onResponse: MEDICINES ARE AS FOLLOWS:");
                    MedicineModel model =response.body().get(0);
                    Log.d(TAG, "onResponse: "+model.toString());
                    mProgresBar.setVisibility(View.GONE);
                }else{
                    Log.d(TAG, "onResponse: Response \n"+response);
                }
            }

            @Override
            public void onFailure(Call<List<MedicineModel>> call, Throwable t) {
                Log.d(TAG, "onFailure: BHAI GADBAD HAI KUCH");
                Toast.makeText(getBaseContext(),"some error occured",Toast.LENGTH_LONG).show();
            }
        });
        Log.d(TAG, "onCreate: Function crossed");
    }
}
