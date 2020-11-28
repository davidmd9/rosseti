package com.rosseti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rosseti.R;
import com.rosseti.base.BaseFragment;

public class ProjectsFragment extends BaseFragment {

    private RecyclerView recyclerProjects;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        recyclerProjects = view.findViewById(R.id.recyclerProjects);
        recyclerProjects.setLayoutManager(new LinearLayoutManager(getMainActivity()));

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        setToolbar(toolbar, "Проекты");


        return view;
    }

}
