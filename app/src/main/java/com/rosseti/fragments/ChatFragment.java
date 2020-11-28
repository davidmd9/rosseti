package com.rosseti.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rosseti.R;
import com.rosseti.adapters.ChatAdapter;
import com.rosseti.base.BaseFragment;
import com.rosseti.models.Comment;
import com.rosseti.models.Comments;
import com.rosseti.models.Suggestion;
import com.rosseti.network.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends BaseFragment {

    private ChatAdapter chatAdapter;

    private Suggestion suggestion;

    private ArrayList<Comment> comments = new ArrayList();

    public ChatFragment(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

    LinearLayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        chatAdapter = new ChatAdapter();


        RecyclerView recyclerChat = view.findViewById(R.id.recyclerChat);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getMainActivity());
        layoutManager.setStackFromEnd(true);
        recyclerChat.setLayoutManager(layoutManager);
        recyclerChat.setAdapter(chatAdapter);

        if (suggestion != null && suggestion.getComments() != null && suggestion.getComments().size() > 0) {
            comments.addAll(suggestion.getComments());
            chatAdapter.add(comments);
            chatAdapter.notifyDataSetChanged();
        }

        EditText etMessage = view.findViewById(R.id.etMessage);

        ImageButton btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etMessage.getText().toString().trim().isEmpty()){
                    showProgress();
                    ApiClient.getApi().addComment(suggestion.getId(), etMessage.getText().toString().trim()).enqueue(new Callback<Comments>() {
                        @Override
                        public void onResponse(Call<Comments> call, Response<Comments> response) {
                            hideProgress();
                            if(response.code() == 200){
                                etMessage.setText("");
                                if(response.body() != null && response.body().isSuccess()){
                                    if(response.body().getComments() != null && response.body().getComments().size() > 0){
                                        comments.clear();
                                        comments.addAll(response.body().getComments());
                                        chatAdapter.add(comments);
                                        chatAdapter.notifyDataSetChanged();

                                        layoutManager.scrollToPositionWithOffset(comments.size() - 1, 0);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Comments> call, Throwable t) {
                            hideProgress();
                        }
                    });
                }
            }
        });

        return view;
    }

}
