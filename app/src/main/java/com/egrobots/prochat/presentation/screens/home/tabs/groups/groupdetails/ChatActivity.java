package com.egrobots.prochat.presentation.screens.home.tabs.groups.groupdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.os.Bundle;

import com.egrobots.prochat.R;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.presentation.adapters.ChatMessagesAdapter;
import com.egrobots.prochat.utils.Constants;

public class ChatActivity extends AppCompatActivity {

    @BindView(R.id.chat_messages_recycler_view)
    RecyclerView chatMessagesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        Chat chat = getIntent().getParcelableExtra(Constants.CHAT);
        ChatMessagesAdapter adapter = new ChatMessagesAdapter(chat.getMessages());
        chatMessagesRecyclerView.setAdapter(adapter);
        chatMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}