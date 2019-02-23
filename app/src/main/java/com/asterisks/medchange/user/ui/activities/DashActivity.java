package com.asterisks.medchange.user.ui.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.constants.StringKeys;
import com.asterisks.medchange.user.ui.fragments.DashHealerFragment;
import com.asterisks.medchange.user.ui.fragments.DashHistoryFragment;
import com.asterisks.medchange.user.ui.fragments.DashMeFragment;
import com.asterisks.medchange.user.ui.fragments.DashMedChangeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class DashActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        toolbar = findViewById(R.id.dash_toolbar);
        bottomNavigationView = findViewById(R.id.dash_bottom_nav);
        setSupportActionBar(toolbar);
        // getting details from preferences
        sharedPreferences = getSharedPreferences(StringKeys.APP_PREFS,MODE_PRIVATE);
        String Username = sharedPreferences.getString(StringKeys.USER_NAME_PREF,"NA");
        String Email = sharedPreferences.getString(StringKeys.USER_EMAIL_PREF,"NA");
        getSupportActionBar().setTitle(Username);
        getSupportActionBar().setSubtitle(Email);
        //bottomNav Setting
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        fragment=new DashMedChangeFragment();
                        LoadFragment(fragment);
                        return true;
                    case R.id.nav_history:
                        fragment=new DashHistoryFragment();
                        LoadFragment(fragment);
                        return true;
                    case R.id.nav_map:
                        fragment=new DashHealerFragment();
                        LoadFragment(fragment);
                        return true;
                    case R.id.nav_account:
                        fragment=new DashMeFragment();
                        LoadFragment(fragment);
                        return true;
                }
                return false;
            }
        });
        LoadFragment(new DashMedChangeFragment());
    }
    void LoadFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.dash_container_frame_layout,fragment).commit();
    }

}
