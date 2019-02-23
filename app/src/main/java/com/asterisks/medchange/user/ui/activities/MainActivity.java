package com.asterisks.medchange.user.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.constants.StringKeys;
import com.asterisks.medchange.user.ui.fragments.LoginFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    LinearLayoutCompat mLinearLayoutConatiner;
    FragmentTransaction ft;
    SharedPreferences sharedPreferences;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayoutConatiner = findViewById(R.id.main_linear_layout_container);
        sharedPreferences = getSharedPreferences(StringKeys.APP_PREFS,MODE_PRIVATE);
        if(sharedPreferences.getString(StringKeys.TOKEN_PREF,"NONE").equals("NONE")){
            ft=getSupportFragmentManager().beginTransaction();
            LoginFragment loginFragment = new LoginFragment();
            ft.add(R.id.view_container,loginFragment).commit();
        }else{
            intent= new Intent(this,DashActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
