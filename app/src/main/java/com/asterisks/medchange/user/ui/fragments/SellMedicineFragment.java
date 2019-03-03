package com.asterisks.medchange.user.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.api.models.PharmacistModel;
import com.asterisks.medchange.user.api.models.PharmacistModelList;
import com.asterisks.medchange.user.api.models.UserMedicineModel;
import com.asterisks.medchange.user.api.service.MediChangeClient;
import com.asterisks.medchange.user.constants.StringKeys;
import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

public class SellMedicineFragment extends Fragment {
    Spinner mPharmacistDropDown;
    List<String> listPharmas;
    Context mBaseContext;
    int ListPharmaSelector;
    MaterialButton mRequestButton;
    UserMedicineModel medicineModel;
    PharmacistModelList pharmaList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell_medicine,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPharmacistDropDown = view.findViewById(R.id.sell_med_dropdown);
        mBaseContext = getContext();
        listPharmas=new ArrayList<>();
        Bundle bundle = getArguments();
        SharedPreferences sp = getActivity().getSharedPreferences(StringKeys.APP_PREFS,MODE_PRIVATE);
        final String token = sp.getString(StringKeys.TOKEN_PREF,"NA");
        medicineModel=(UserMedicineModel) bundle.getSerializable("medicine");
        Log.d(TAG, "onViewCreated: MedicineID:\n"+medicineModel.getId());
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request().newBuilder().addHeader("Authorization","token "+token).build();
                        return chain.proceed(original);
                    }
                }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://206.189.133.177")
                .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build();
        final MediChangeClient mediChangeClient = retrofit.create(MediChangeClient.class);
        Call<PharmacistModelList> getPharmacistList = mediChangeClient.getPharmacistList();
        getPharmacistList.enqueue(new Callback<PharmacistModelList>() {
            @Override
            public void onResponse(Call<PharmacistModelList> call, Response<PharmacistModelList> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: List of Pharmacist:\n"+response.toString());
                    pharmaList = response.body();
                    int size = response.body().getPharmacists().size();
                    for(int i=0; i<size ;i++){
                        PharmacistModel model = pharmaList.getPharmacists().get(i);
                        String name = model.getUsername();
                        Log.d(TAG, "onResponse: Pharmaname: "+i+" "+name);
                        listPharmas.add(name);
                    }
                    Log.d(TAG, "onResponse: List \n"+listPharmas);
                    String[] pharmas=new String[pharmaList.getPharmacists().size()];
                    pharmas = listPharmas.toArray(pharmas);
                    ArrayAdapter adapter = new ArrayAdapter(mBaseContext,android.R.layout.simple_spinner_item,pharmas);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mPharmacistDropDown.setAdapter(adapter);
                    Toast.makeText(mBaseContext,"Pharmacists loaded successfully",Toast.LENGTH_LONG).show();
                    ListPharmaSelector = pharmaList.getPharmacists().get(0).getId();
                }else{
                    Log.d(TAG, "onResponse: Response wasn't successful\n"+response.body());
                }
            }

            @Override
            public void onFailure(Call<PharmacistModelList> call, Throwable t) {
                t.printStackTrace();
            }
        });
        mPharmacistDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListPharmaSelector =pharmaList.getPharmacists().get(position).getId();
                Toast.makeText(mBaseContext,"id selected: "+ListPharmaSelector, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mRequestButton = view.findViewById(R.id.sell_frag_button_request);
        mRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                medicineModel.setPharmacist(String.valueOf(ListPharmaSelector));
                medicineModel.setRequested(true);
                Call<ResponseBody> patch = mediChangeClient.patchMedicine(Integer.parseInt(medicineModel.getId()), medicineModel);
                patch.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(mBaseContext,"Successful",Toast.LENGTH_LONG).show();
                        }else{
                            Log.d(TAG, "onResponse: \n"+response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
