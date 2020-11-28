package com.rosseti.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rosseti.models.BaseModel;
import com.rosseti.MainActivity;
import com.rosseti.R;
import com.rosseti.Storage;
import com.rosseti.base.BaseFragment;
import com.rosseti.network.ApiClient;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMessageFragment extends BaseFragment {

    private String phone = "";

    LoginMessageFragment(String phone) {
        this.phone = phone;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_message, container, false);

        EditText etCode = view.findViewById(R.id.etCode);

        RelativeLayout rlReady = view.findViewById(R.id.rlReady);
        rlReady.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etCode.getText().toString().length() != 4) return;

                showProgress();
                ApiClient.getApi().verifyCode(phone, etCode.getText().toString()).enqueue(new Callback<BaseModel>() {
                    @Override
                    public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                        hideProgress();
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                if (response.body().isSuccess()) {
                                    if (response.body().getToken() != null) {
                                        Storage storage = new Storage(getMainActivity());
                                        storage.setToken(response.body().getToken());

                                        Intent intent = new Intent(getMainActivity(), MainActivity.class);
                                        getMainActivity().startActivity(intent);
                                        getMainActivity().finish();
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<BaseModel> call, Throwable t) {
                        hideProgress();
                    }
                });
            }
        });

        return view;
    }

    @NotNull
    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(@NotNull String title) {

    }
}
