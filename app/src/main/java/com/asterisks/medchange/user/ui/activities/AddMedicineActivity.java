package com.asterisks.medchange.user.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.constants.IntKeys;
import com.asterisks.medchange.user.constants.StringKeys;
import com.asterisks.medchange.user.ui.fragments.DatePickerFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class AddMedicineActivity extends AppCompatActivity {
    private static final String TAG = "AddMedicineActivity";
    Spinner mDropdown;
    TextView ExpiryDate;
    TextInputEditText Quantity;
    ImageView MedicineImageView;
    ImageView ExpiryImageView;
    MaterialButton AddMedicineButton;
    FragmentManager fm;
    int kar=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        mDropdown = findViewById(R.id.add_med_dropdown);
        ExpiryDate = findViewById(R.id.add_med_expiry_date);
        Quantity = findViewById(R.id.add_med_quantity);
        MedicineImageView = findViewById(R.id.add_med_photo);
        AddMedicineButton = findViewById(R.id.add_med_button);
        ExpiryImageView = findViewById(R.id.add_med_expiry_photo);
        fm = getSupportFragmentManager();
        ExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment datePicker = new DatePickerFragment();
                datePicker.show(fm, "datefragement");
            }
        });

        MedicineImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                kar=1;
                cameraIntent.putExtra("value",StringKeys.PICTURE_MEDICINE);
                startActivityForResult(cameraIntent, IntKeys.CAMERA_REQUEST);
            }
        });
        ExpiryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kar=2;
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("value",StringKeys.PICTURE_EXPIRY);
                startActivityForResult(cameraIntent, IntKeys.CAMERA_REQUEST);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntKeys.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            String text = data.getStringExtra("value");
            Log.d(TAG, "onActivityResult: Text: "+text);
            if(kar==1) {
                Log.d(TAG, "onActivityResult: I'm in medicine");
                MedicineImageView.setImageBitmap(photo);
            }else{
                Log.d(TAG, "onActivityResult: I'm in Expiry");
                ExpiryImageView.setImageBitmap(photo);
            }
        }
    }
}
