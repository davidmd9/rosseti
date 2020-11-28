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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpertFragment extends BaseFragment {

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

        projectsAdapter.setListener(suggestion -> ((MainActivity) getMainActivity()).pushFragment(new ExpertDetailsFragment(suggestion), true));

        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> refresh());

        getSuggestions();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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

                                for(Suggestion suggestion : response.body().getSuggestions()){
                                    if(suggestion.getStatus_id() == 2){
                                        suggestionList.add(suggestion);
                                    }
                                }

                                projectsAdapter.add(suggestionList);
                                recyclerProjects.setAdapter(projectsAdapter);
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
                            for(Suggestion suggestion : response.body().getSuggestions()){
                                if(suggestion.getStatus_id() == 2){
                                    suggestionList.add(suggestion);
                                }
                            }

                            projectsAdapter.notifyDataSetChanged();
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
