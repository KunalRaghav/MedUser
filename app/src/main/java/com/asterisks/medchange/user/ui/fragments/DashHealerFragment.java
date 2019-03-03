package com.asterisks.medchange.user.ui.fragments;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.api.models.MedicineLocationArrayModel;
import com.asterisks.medchange.user.api.models.PharmacistLocationModel;
import com.asterisks.medchange.user.api.service.MediChangeClient;
import com.asterisks.medchange.user.ui.activities.DashActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class DashHealerFragment extends Fragment {
    // Commented Old Code
//    GoogleMap mMap;
//    FusedLocationProviderClient mFusedLocationProviderClient;
//    Boolean mLocationPermissionGranted=true;
//    LatLng sydney;
//    Location mLastKnownLocation;
//    Activity hostActivity;
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_dash_healers,container,false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        SupportMapFragment mapFragment = new SupportMapFragment();
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.dash_container_frame_layout,mapFragment).commit();
//        mapFragment.getMapAsync(this);
//        hostActivity=getActivity();
//        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
//
//    }
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//         sydney= new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
////        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,20));
//        getDeviceLocation();
//        updateLocationUI();
//    }
//
//    private void updateLocationUI() {
//        if (mMap == null) {
//            return;
//        }
//        try {
//            if (mLocationPermissionGranted) {
//                mMap.setMyLocationEnabled(true);
//                mMap.getUiSettings().setMyLocationButtonEnabled(true);
//                mMap.getUiSettings().setTiltGesturesEnabled(true);
//            } else {
//                mMap.setMyLocationEnabled(false);
//                mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                mLastKnownLocation = null;
//            }
//        } catch (SecurityException e)  {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }
//    private void getDeviceLocation() {
//        /*
//         * Get the best and most recent location of the device, which may be null in rare
//         * cases when a location is not available.
//         */
//        try {
//            if (mLocationPermissionGranted) {
//                Task locationResult = mFusedLocationProviderClient.getLastLocation();
//                locationResult.addOnCompleteListener(hostActivity, new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        if (task.isSuccessful()) {
//                            // Set the map's camera position to the current location of the device.
//                            mLastKnownLocation = (Location) task.getResult();
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                    new LatLng(mLastKnownLocation.getLatitude(),
//                                            mLastKnownLocation.getLongitude()), 15));
//                        } else {
//                            Log.d(TAG, "Current location is null. Using defaults.");
//                            Log.e(TAG, "Exception: %s", task.getException());
//                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 15));
//                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
//                        }
//                    }
//                });
//            }
//        } catch(SecurityException e)  {
//            Log.e("Exception: %s", e.getMessage());
//        }
//    }

    MapView mHealersMapView;
    GoogleMap gmap;
    FloatingActionButton mGetCurrentLocation;
    FusedLocationProviderClient mFusedLocationProviderClient;
    Boolean mLocationPermissionGranted=true;
    Location mLastKnownLocation;
    Activity mHostActivity;
    List<PharmacistLocationModel> pharmacistLocationModelList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dash_healers,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Mapping
        super.onViewCreated(view, savedInstanceState);
        mHealersMapView= view.findViewById(R.id.healers_map_view);
        mGetCurrentLocation = view.findViewById(R.id.healer_get_present);
        mHostActivity = getActivity();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        //Getting all pharmacist Locations
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://206.189.133.177")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MediChangeClient mediChangeClient = retrofit.create(MediChangeClient.class);
        Call<MedicineLocationArrayModel> getMedcinesWithLocation = mediChangeClient.getMedcinesWithLocation();
        //Setting Up MapView
        mHealersMapView.onCreate(savedInstanceState);
        mHealersMapView.onResume();
        try{
            MapsInitializer.initialize(getActivity().getApplicationContext());
        }catch (Exception e){
            e.printStackTrace();
        }

        mHealersMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gmap=googleMap;
                getDeviceLocation(googleMap);
                updateLocationUI(googleMap);
            }
        });
        mGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                     new LatLng(mLastKnownLocation.getLatitude(),
                             mLastKnownLocation.getLongitude()), 15));
            }
        });
        //Getting Locations
        getMedcinesWithLocation.enqueue(new Callback<MedicineLocationArrayModel>() {
            @Override
            public void onResponse(Call<MedicineLocationArrayModel> call, Response<MedicineLocationArrayModel> response) {
                if(response.isSuccessful()){
                    pharmacistLocationModelList=response.body().medicineLocation;
                    Toast.makeText(mHostActivity.getApplicationContext(),"Pharmacists location scraped successfully",Toast.LENGTH_LONG).show();
                    addMarkers(gmap,response.body().medicineLocation);
                }else{
                    Log.d(TAG, "onResponse: failed:\n"+response.toString());
                }
            }

            @Override
            public void onFailure(Call<MedicineLocationArrayModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getDeviceLocation(final GoogleMap mMap){
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(mHostActivity, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), 15));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void updateLocationUI(GoogleMap mMap) {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setTiltGesturesEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void addMarkers(GoogleMap mMap,List<PharmacistLocationModel> pharmacistLocationModelList){
        int size = pharmacistLocationModelList.size();
        for(int i=0;i<size;i++){
            PharmacistLocationModel model = pharmacistLocationModelList.get(i);
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(model.getPharmacist__latitude()),Double.parseDouble(model.getPharmacist__longitude()))).title(model.getPharmacist__username()));
        }
    }
}
