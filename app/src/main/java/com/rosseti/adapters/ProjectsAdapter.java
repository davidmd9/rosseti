package com.rosseti.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.rosseti.R;
import com.rosseti.base.BaseAdapter;

import org.jetbrains.annotations.NotNull;

public class ProjectsAdapter extends BaseAdapter<ProjectsAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
       return new ViewHolder(view);
    }

    class ViewHolder extends BaseAdapter.BaseViewHolder{

        public ViewHolder(@NotNull View view) {
            super(view);
        }

        @Override
        public void bind(@NotNull Object item) {

        }
    }
}
