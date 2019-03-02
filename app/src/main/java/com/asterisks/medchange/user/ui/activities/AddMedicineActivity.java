package com.asterisks.medchange.user.ui.activities;

import android.os.Bundle;
import android.widget.Spinner;

import com.asterisks.medchange.user.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddMedicineActivity extends AppCompatActivity {
    Spinner mDropdown;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
    }
}
