package com.asterisks.medchange.user;

import android.os.Bundle;

import com.asterisks.medchange.user.ui.LoginFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    LinearLayoutCompat mLinearLayoutConatiner;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayoutConatiner = findViewById(R.id.main_linear_layout_container);
        ft=getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        ft.add(R.id.view_container,loginFragment).commit();
    }
}
