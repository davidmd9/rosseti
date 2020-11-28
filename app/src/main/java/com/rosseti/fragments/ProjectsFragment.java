package com.rosseti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rosseti.MainActivity;
import com.rosseti.R;
import com.rosseti.adapters.ProjectsAdapter;
import com.rosseti.base.BaseFragment;
import com.rosseti.models.Suggestion;
import com.rosseti.models.Suggestions;
import com.rosseti.network.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectsFragment extends BaseFragment {

    private RecyclerView recyclerProjects;
    private ProjectsAdapter projectsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        projectsAdapter = new ProjectsAdapter();
        recyclerProjects = view.findViewById(R.id.recyclerProjects);
        recyclerProjects.setLayoutManager(new LinearLayoutManager(getMainActivity()));

        projectsAdapter.setListener(suggestion -> ((MainActivity) getMainActivity()).pushFragment(new ProjectDetailsFragment(suggestion), true));

        getSuggestions();

        return view;
    }

    private void getSuggestions() {
        showProgress();
        ApiClient.getApi().getSuggestions().enqueue(new Callback<Suggestions>() {
            @Override
            public void onResponse(Call<Suggestions> call, Response<Suggestions> response) {
                hideProgress();
                if (response.code() == 200) {
                    if (response.body() != null && response.body().isSuccess()) {
                        if (response.body().getSuggestions() != null && response.body().getSuggestions().size() > 0) {
                            projectsAdapter.add(response.body().getSuggestions());
                            recyclerProjects.setAdapter(projectsAdapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Suggestions> call, Throwable t) {
                hideProgress();
            }
        });
    }
}
