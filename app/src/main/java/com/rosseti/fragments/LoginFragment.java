package com.rosseti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rosseti.models.BaseModel;
import com.rosseti.LoginActivity;
import com.rosseti.R;
import com.rosseti.base.BaseFragment;
import com.rosseti.network.ApiClient;

import br.com.sapereaude.maskedEditText.MaskedEditText;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        MaskedEditText etPhone = view.findViewById(R.id.etPhone);
        RelativeLayout rlLogin = view.findViewById(R.id.rlLogin);

        rlLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPhone.getRawText().length() != 10) return;

                showProgress();
                ApiClient.getApi().auth("+7" + etPhone.getRawText()).enqueue(new Callback<BaseModel>() {
                    @Override
                    public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                        hideProgress();
                        if (response.code() == 200) {
                            if (response.body() != null) {
                                if (response.body().isSuccess()) {
                                    ((LoginActivity) getMainActivity()).pushFragment(new LoginMessageFragment("+7" + etPhone.getRawText()), true);
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
}
