package com.rosseti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.rosseti.LoginActivity;
import com.rosseti.MainActivity;
import com.rosseti.R;
import com.rosseti.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class RegFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reg, container, false);

        RelativeLayout rlReg = view.findViewById(R.id.rlReg);
        rlReg.setOnClickListener(v -> ((LoginActivity)getMainActivity()).pushFragment(new LoginFragment(), true));

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
