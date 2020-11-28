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
import com.rosseti.models.Suggestion;

public class ChatFragment extends BaseFragment {

    private ChatAdapter chatAdapter;

    private Suggestion suggestion;

    public ChatFragment(Suggestion suggestion) {
        this.suggestion = suggestion;
    }

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
            chatAdapter.add(suggestion.getComments());
        }

        EditText etMessage = view.findViewById(R.id.etMessage);

        ImageButton btnSend = view.findViewById(R.id.btnSend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
