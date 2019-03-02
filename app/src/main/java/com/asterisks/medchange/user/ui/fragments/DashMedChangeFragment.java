package com.asterisks.medchange.user.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.constants.StringKeys;
import com.asterisks.medchange.user.ui.activities.AddMedicineActivity;
import com.asterisks.medchange.user.ui.activities.GetMedicinesActivity;
import com.asterisks.medchange.user.ui.activities.SellMedicinesActivity;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashMedChangeFragment extends Fragment {
    TextView Credits;
    SharedPreferences sharedPreferences;
    LinearLayout getButton,sellButton;
    MaterialButton addButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dash_medchange,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Credits=view.findViewById(R.id.dash_credits);
        sharedPreferences=getContext().getSharedPreferences(StringKeys.APP_PREFS, Context.MODE_PRIVATE);
        Credits.setText(sharedPreferences.getString(StringKeys.USER_CREDITS_PREF,"NA"));
        getButton=view.findViewById(R.id.dash_medchange_get);
//        getButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
//                    requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, IntKeys.REQUEST_LOCATION_PERMISSION);
//                }else{
//                    Intent intent = new Intent(getContext(), MapsActivity.class);
//                    startActivity(intent);
//                }
//            }
//        });
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GetMedicinesActivity.class);
                startActivity(intent);
            }
        });

        sellButton=view.findViewById(R.id.dash_medchange_sell);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SellMedicinesActivity.class);
                startActivity(intent);
            }
        });

        addButton = view.findViewById(R.id.dash_medchange_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMedicineActivity.class);
                startActivity(intent);
            }
        });
    }
}
