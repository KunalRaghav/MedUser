package com.asterisks.medchange.user.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.asterisks.medchange.user.R;
import com.asterisks.medchange.user.api.models.UserLoginCallbackModel;
import com.asterisks.medchange.user.api.models.UserRegisterModel;
import com.asterisks.medchange.user.api.service.MediChangeClient;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment {

    View view;
    TextInputEditText mEmailTextEdit;
    TextInputEditText mUsernameTextEdit;
    TextInputEditText mPhoneTextEdit;
    TextInputEditText mAadhaarTextEdit;
    TextInputEditText mAddressTextEdit;
    TextInputEditText mPasswordTextEdit;
    TextInputEditText mRetypePasswordTextEdit;
    MaterialButton mRegisterButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register_screen,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmailTextEdit=view.findViewById(R.id.register_email);
        mUsernameTextEdit=view.findViewById(R.id.register_username);
        mPhoneTextEdit=view.findViewById(R.id.register_phone);
        mAadhaarTextEdit=view.findViewById(R.id.register_aadhaar);
        mAddressTextEdit=view.findViewById(R.id.register_address);
        mPasswordTextEdit=view.findViewById(R.id.register_password);
        mRetypePasswordTextEdit=view.findViewById(R.id.register_password_confirmation);
        mRegisterButton=view.findViewById(R.id.register_button);
        mRegisterButton.setEnabled(false);
        mRetypePasswordTextEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String confirmPass=s.toString();
                if(confirmPass.contentEquals(mPasswordTextEdit.getText().toString().trim())){
                   mRegisterButton.setEnabled(true);
                }
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://authpreviewapi.herokuapp.com/").
                        addConverterFactory(GsonConverterFactory.create()).build();
        final MediChangeClient changeClient = retrofit.create(MediChangeClient.class);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegisterModel registerModel = new UserRegisterModel(
                        mUsernameTextEdit.getText().toString().trim(),
                        mEmailTextEdit.getText().toString().trim(),
                        mPhoneTextEdit.getText().toString().trim(),
                        mAadhaarTextEdit.getText().toString().trim(),
                        mAddressTextEdit.getText().toString().trim(),
                        mPasswordTextEdit.getText().toString().trim()
                );
                Call<UserLoginCallbackModel> register = changeClient.register(registerModel);
                register.enqueue(new Callback<UserLoginCallbackModel>() {
                    @Override
                    public void onResponse(Call<UserLoginCallbackModel> call, Response<UserLoginCallbackModel> response) {
                        if(response.isSuccessful()){
                            Snackbar.make(getView(),response.body().getToken().toString(),Snackbar.LENGTH_LONG).show();
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
