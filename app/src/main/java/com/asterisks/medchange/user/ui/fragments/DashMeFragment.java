package com.asterisks.medchange.user.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.constants.StringKeys;
import com.asterisks.medchange.user.ui.activities.MainActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DashMeFragment extends Fragment {
    TextView Logout;
    SharedPreferences sharedPreferences;
    TextView Aadhaar;
    TextView Address;
    TextView PhoneNumber;
    TextView TotalCredits;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dash_me,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(StringKeys.APP_PREFS, Context.MODE_PRIVATE);
        Aadhaar=view.findViewById(R.id.dash_me_aadhaar);
        Address=view.findViewById(R.id.dash_me_address);
        PhoneNumber=view.findViewById(R.id.dash_me_phone);
        TotalCredits=view.findViewById(R.id.dash_me_credits);
        Logout=view.findViewById(R.id.dash_me_logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().clear().commit();
                Toast.makeText(getContext(),"BYE BYE",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();

            }
        });
        Aadhaar.setText("Aadhaar: "+sharedPreferences.getString(StringKeys.USER_AADHAAR_PREF,"NA"));
        Address.setText("Address:\n"+sharedPreferences.getString(StringKeys.USER_ADDRESS_PREF,"Sample Address"));
        PhoneNumber.setText("Phone: "+sharedPreferences.getString(StringKeys.USER_PHONE_PREF,"9899855245"));
        TotalCredits.setText("Total Credits: "+sharedPreferences.getString(StringKeys.USER_CREDITS_PREF,"0.00"));
    }
}
