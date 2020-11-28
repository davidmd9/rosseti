package com.rosseti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rosseti.MainActivity;
import com.rosseti.R;
import com.rosseti.adapters.ProjectsAdapter;
import com.rosseti.base.BaseFragment;
import com.rosseti.models.Suggestion;
import com.rosseti.models.Suggestions;
import com.rosseti.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProjectsFragment extends BaseFragment {

    private RecyclerView recyclerProjects;
    private ProjectsAdapter projectsAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Suggestion> suggestionList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_projects, container, false);

        projectsAdapter = new ProjectsAdapter();
        recyclerProjects = view.findViewById(R.id.recyclerProjects);
        recyclerProjects.setLayoutManager(new LinearLayoutManager(getMainActivity()));

        projectsAdapter.setListener(suggestion -> ((MainActivity) getMainActivity()).pushFragment(new ProjectDetailsFragment(suggestion), true));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> refresh());


        getSuggestions();

        return view;
    }

    private void getSuggestions() {
        if(suggestionList.size() > 0){
            projectsAdapter.add(suggestionList);
            recyclerProjects.setAdapter(projectsAdapter);
        } else {
            showProgress();
            ApiClient.getApi().getSuggestions().enqueue(new Callback<Suggestions>() {
                @Override
                public void onResponse(Call<Suggestions> call, Response<Suggestions> response) {
                    hideProgress();
                    if (response.code() == 200) {
                        if (response.body() != null && response.body().isSuccess()) {
                            if (response.body().getSuggestions() != null && response.body().getSuggestions().size() > 0) {
                                suggestionList = response.body().getSuggestions();
                                projectsAdapter.add(suggestionList);
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

    private void refresh(){
        ApiClient.getApi().getSuggestions().enqueue(new Callback<Suggestions>() {
            @Override
            public void onResponse(Call<Suggestions> call, Response<Suggestions> response) {
                hideProgress();
                swipeRefreshLayout.setRefreshing(false);
                if (response.code() == 200) {
                    if (response.body() != null && response.body().isSuccess()) {
                        if (response.body().getSuggestions() != null && response.body().getSuggestions().size() > 0) {
                            suggestionList.clear();
                            suggestionList.addAll(response.body().getSuggestions());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Suggestions> call, Throwable t) {
                hideProgress();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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
