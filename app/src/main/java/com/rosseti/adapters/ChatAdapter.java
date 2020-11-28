package com.rosseti.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.rosseti.R;
import com.rosseti.base.BaseAdapter;
import com.rosseti.models.Comment;

import org.jetbrains.annotations.NotNull;

public class ChatAdapter extends BaseAdapter<ChatAdapter.ViewHolder> {

    private final int INCOME_TYPE = 1;
    private final int OUTCOME_TYPE = 2;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == INCOME_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_income, parent, false);
        } else if (viewType == OUTCOME_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_outcome, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (((Comment) getItems().get(position)).getYou() == 1) {
            return OUTCOME_TYPE;
        } else {
            return INCOME_TYPE;
        }
    }

    class ViewHolder extends BaseAdapter.BaseViewHolder {

        private TextView tvAuthor;
        private TextView tvMessageText;

        public ViewHolder(@NotNull View view) {
            super(view);

            tvAuthor = view.findViewById(R.id.tvAuthor);
            tvMessageText = view.findViewById(R.id.tvMessageText);
        }

        @Override
        public void bind(@NotNull Object item) {
            Comment comment = (Comment) item;
            if (item != null) {
                if (((Comment) item).getText() != null) {
                    tvMessageText.setText(((Comment) item).getText());
                }
                if (((Comment) item).getUser() != null && ((Comment) item).getUser().getFull_name() != null) {
                    tvAuthor.setText(((Comment) item).getUser().getFull_name());
                }

                if(((Comment) item).getYou() == 1){
                    tvAuthor.setVisibility(View.GONE);
                }
            }
        }
    }
}
