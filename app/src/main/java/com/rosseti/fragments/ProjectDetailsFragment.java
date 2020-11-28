package com.rosseti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.rosseti.R;
import com.rosseti.base.BaseFragment;
import com.rosseti.models.Suggestion;

import org.jetbrains.annotations.NotNull;

public class ProjectDetailsFragment extends BaseFragment {

    private Suggestion suggestion = new Suggestion();
    public ProjectDetailsFragment(Suggestion suggestion){
        this.suggestion = suggestion;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_details, container, false);

        return view;
    }

    @NotNull
    @Override
    public String getTitle() {
        return "Проекты";
    }

    @Override
    public void setTitle(@NotNull String title) {

    }
}
