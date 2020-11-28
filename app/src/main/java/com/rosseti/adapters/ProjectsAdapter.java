package com.rosseti.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rosseti.R;
import com.rosseti.base.BaseAdapter;
import com.rosseti.models.Suggestion;

import org.jetbrains.annotations.NotNull;

public class ProjectsAdapter extends BaseAdapter<ProjectsAdapter.ViewHolder> {

    public interface ProjectListener {
        void onProject(Suggestion suggestion);
    }

    private ProjectListener listener;

    public void setListener(ProjectListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class ViewHolder extends BaseAdapter.BaseViewHolder {

        private TextView tvName;
        private TextView tvAuthor;
        private TextView tvTheme;
        private ImageView imageView;

        public ViewHolder(@NotNull View view) {
            super(view);

            tvName = view.findViewById(R.id.tvName);
            tvAuthor = view.findViewById(R.id.tvAuthor);
            tvTheme = view.findViewById(R.id.tvTheme);

        }

        @Override
        public void bind(@NotNull Object item) {
            Suggestion suggestion = (Suggestion) item;
            if (suggestion != null) {
                tvName.setText(suggestion.getTitle());
                tvTheme.setText(suggestion.getTitle());
                if (suggestion.getAuthor() != null) {
                    tvAuthor.setText(suggestion.getAuthor().getFull_name() == null ? "" : suggestion.getAuthor().getFull_name());
                }
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) listener.onProject(suggestion);
            });
        }
    }
}

