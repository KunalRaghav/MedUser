package com.asterisks.medchange.user.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.api.models.UserLoginCallbackModel;
import com.asterisks.medchange.user.api.models.UserLoginModel;
import com.asterisks.medchange.user.api.service.MediChangeClient;
import com.asterisks.medchange.user.constants.StringKeys;
import com.asterisks.medchange.user.ui.activities.DashActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginFragment extends Fragment {

    View view;
    TextInputEditText mEmailTextEdit;
    TextInputEditText mPasswordTextEdit;
    MaterialButton mLoginButton;
    MaterialButton mRegisterButton;
    InputMethodManager mInputMethodManager;
    FragmentTransaction mFragmentTransaction;
    SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_login_screen,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mEmailTextEdit = view.findViewById(R.id.login_email);
        mPasswordTextEdit = view.findViewById(R.id.login_password);
        mLoginButton = view.findViewById(R.id.login_button);
        mRegisterButton = view.findViewById(R.id.login_register_button);
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mEmailTextEdit.requestFocus();
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterFragment registerFragment = new RegisterFragment();
                mFragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                mFragmentTransaction.replace(R.id.view_container,registerFragment);
                mFragmentTransaction.commit();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://authpreviewapi.herokuapp.com/").
                addConverterFactory(GsonConverterFactory.create()).build();
        final MediChangeClient changeClient = retrofit.create(MediChangeClient.class);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputMethodManager.hideSoftInputFromWindow(mEmailTextEdit.getWindowToken(),0);
                UserLoginModel loginModel= new UserLoginModel(
                        mEmailTextEdit.getText().toString().trim(),
                        mPasswordTextEdit.getText().toString().trim());
                Call<UserLoginCallbackModel> login = changeClient.login(loginModel);
                login.enqueue(new Callback<UserLoginCallbackModel>() {
                    @Override
                    public void onResponse(Call<UserLoginCallbackModel> call, Response<UserLoginCallbackModel> response) {
                        if(response.isSuccessful()){
                            Snackbar.make(getView(),response.body().getToken().toString(),Snackbar.LENGTH_LONG).show();
                            //Getting values form response:
                            String token = response.body().getToken();
                            String email = response.body().getUser().getEmail();
                            String username = response.body().getUser().getUsername();
                            String credits = response.body().getUser().getTotalCredits();
                            String aadhaarNo = response.body().getUser().getAadhaarNo();
                            String address= response.body().getUser().getAddress();

                            //Saving return values in preferences
                            sharedPreferences = getContext().getSharedPreferences(StringKeys.APP_PREFS,Context.MODE_PRIVATE);
                            sharedPreferences.edit().putString(StringKeys.TOKEN_PREF,token).commit();
                            sharedPreferences.edit().putString(StringKeys.USER_EMAIL_PREF,email).commit();
                            sharedPreferences.edit().putString(StringKeys.USER_NAME_PREF,username).commit();
                            sharedPreferences.edit().putString(StringKeys.USER_CREDITS_PREF,credits).commit();
                            sharedPreferences.edit().putString(StringKeys.USER_AADHAAR_PREF,aadhaarNo).commit();
                            sharedPreferences.edit().putString(StringKeys.USER_ADDRESS_PREF,address).commit();

                            //Launching dashboard
                            Intent intent = new Intent(getContext(), DashActivity.class);
                            startActivity(intent);
                            //closing current activity
                            getActivity().finish();
                        }else{
                            Snackbar.make(getView(),"Invalid credentials",Snackbar.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserLoginCallbackModel> call, Throwable t) {
                        Toast.makeText(getContext(),"Internet Not found",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
