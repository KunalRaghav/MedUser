package com.asterisks.medchange.user.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.api.models.MedicineModel;
import com.asterisks.medchange.user.api.service.MediChangeClient;
import com.asterisks.medchange.user.constants.IntKeys;
import com.asterisks.medchange.user.constants.StringKeys;
import com.asterisks.medchange.user.ui.fragments.DatePickerFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddMedicineActivity extends AppCompatActivity {
    private static final String TAG = "AddMedicineActivity";
    Spinner mDropdown;
    TextView ExpiryDate;
    TextInputEditText Quantity;
    ImageView MedicineImageView;
    ImageView ExpiryImageView;
    MaterialButton AddMedicineButton;
    FragmentManager fm;
    List<MedicineModel> listMedicines;
    List<String> list;
    File medfile,expfile;
    Bitmap MedBitmap;
    Bitmap ExpiryBitmap;
    int imageview_selector=0;
    long listdropdown_selector=0;

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
        list = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences(StringKeys.APP_PREFS,MODE_PRIVATE);
        final String token = sp.getString(StringKeys.TOKEN_PREF,"NA");
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl url = original.url();
                        HttpUrl newHttpUrl = url.newBuilder().addQueryParameter("token",token).build();
                        Request.Builder builder = original.newBuilder().url(newHttpUrl);
                        Request request = builder.build();
                        return chain.proceed(request);
                    }
                }).build();

//        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://authpreviewapi.herokuapp.com")
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(okHttpClient).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://206.189.133.177")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient).build();

        final MediChangeClient mediChangeClient = retrofit.create(MediChangeClient.class);
        Call<List<MedicineModel>> getMedicineList = mediChangeClient.getMedicineList();

        getMedicineList.enqueue(new Callback<List<MedicineModel>>() {
            @Override
            public void onResponse(Call<List<MedicineModel>> call, Response<List<MedicineModel>> response) {
                if(response.isSuccessful()){
                    listMedicines=response.body();
                    Log.d(TAG, "onResponse: List of meds: \n"+listMedicines);
                    for(int i=0;i<listMedicines.size();i++){
                        MedicineModel medicine = listMedicines.get(i);
                        String name=medicine.getName();
                        Log.d(TAG, "onResponse: Medicine"+i+" "+name);
                        list.add(name);
                    }
                    Log.d(TAG, "onResponse: List\n"+list);
                    String[] listing=new String[list.size()];
                    listing = list.toArray(listing);
                    ArrayAdapter adapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item,listing);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mDropdown.setAdapter(adapter);
                    Toast.makeText(getApplicationContext(),"Medicines Loaded Successfully",Toast.LENGTH_LONG).show();
                    listdropdown_selector=listMedicines.get(0).getId();
                }else{
                    Log.d(TAG, "onResponse: Response wasn't successful\n"+response.body());
                }
            }

            @Override

            public void onFailure(Call<List<MedicineModel>> call, Throwable t) {
                t.printStackTrace();
            }
        });
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
                imageview_selector=1;
                cameraIntent.putExtra("value",StringKeys.PICTURE_MEDICINE);
                startActivityForResult(cameraIntent, IntKeys.CAMERA_REQUEST);
            }
        });
        ExpiryImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageview_selector=2;
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra("value",StringKeys.PICTURE_EXPIRY);
                startActivityForResult(cameraIntent, IntKeys.CAMERA_REQUEST);
            }
        });
        mDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                listdropdown_selector = listMedicines.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        AddMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                RequestBody medfilereq = RequestBody.create(MediaType.parse("multipart/form-data"),medfile);
//                RequestBody expfilereq = RequestBody.create(MediaType.parse("multipart/form-data"),expfile);
//                RequestBody expDate = RequestBody.create(MediaType.parse("multipart/form-data"),ExpiryDate.getText().toString());
//                RequestBody quanMed = RequestBody.create(MediaType.parse("multipart/form-data"),Quantity.getText().toString());
//                RequestBody medID = RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(listdropdown_selector));
//                Map<String,RequestBody> map = new HashMap<>();
//                map.put("medicinePicture",medfilereq);
//                map.put("expiryPicture",expfilereq);
//                map.put("expiryDate",expDate);
//                map.put("medicine",medID);
//                map.put("quantityOfMedicine",quanMed);
//                Call postMeds = mediChangeClient.postMedicine(map);
//                postMeds.enqueue(new Callback() {
//                    @Override
//                    public void onResponse(Call call, Response response) {
//                        Log.d(TAG, "onResponse: "+response.body());
//                    }
//
//                    @Override
//                    public void onFailure(Call call, Throwable t) {
//                        t.printStackTrace();
//                    }
//                });
                Toast.makeText(getApplicationContext(),"Sent successfully",Toast.LENGTH_LONG).show();
                finish();
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
            if(imageview_selector==1) {
                Log.d(TAG, "onActivityResult: I'm in medicine");
                MedBitmap=photo;
                MedicineImageView.setImageBitmap(photo);
                try{
                    medfile = new File(getApplicationContext().getCacheDir(),"medfile.png");
                    medfile.createNewFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    MedBitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                    byte[] bitmapdata = bos.toByteArray();
                    FileOutputStream fos = new FileOutputStream(medfile);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Log.d(TAG, "onActivityResult: I'm in Expiry");
                ExpiryBitmap=photo;
                ExpiryImageView.setImageBitmap(photo);
                try{
                    expfile = new File(getApplicationContext().getCacheDir(),"expfile.png");
                    expfile.createNewFile();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ExpiryBitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
                    byte[] bitmapdata = bos.toByteArray();
                    FileOutputStream fos = new FileOutputStream(expfile);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

}
