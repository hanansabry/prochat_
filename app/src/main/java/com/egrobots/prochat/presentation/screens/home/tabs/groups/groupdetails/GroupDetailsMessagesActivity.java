package com.egrobots.prochat.presentation.screens.home.tabs.groups.groupdetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupChatSelectedCallback;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.model.Message;
import com.egrobots.prochat.presentation.adapters.GroupDetailsMessagesAdapter;
import com.egrobots.prochat.presentation.screens.home.tabs.groups.groupdetails.dialogs.MessageSelectedActionsDialog;
import com.egrobots.prochat.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GroupDetailsMessagesActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.subtitle)
    TextView subtitle;
    @BindView(R.id.group_messages_recyclerview)
    RecyclerView messagesRecyclerView;

    private List<Chat> selectedMessages = new ArrayList<>();
    private MessageSelectedActionsDialog messageSelectedActionsDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details_messages);
        ButterKnife.bind(this);
        title.setText(getString(R.string.messages));
        subtitle.setText("Group Name");

        setRecyclerView();
    }

    private void setRecyclerView() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message("Hi Ahmed, I am texting you because we need to do somd lk", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Ok, Thanks.", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi Ahmed, I am texting you because we need to do somd lk", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi Ahmed, I am texting you because we need to do somd lk", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi Ahmed, I am texting you because we need to do somd lk", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi Ahmed, I am texting you because we need to do somd lk", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi Ahmed, I am texting you because we need to do somd lk", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi Ahmed, I am texting you because we need to do somd lk", Calendar.getInstance().getTimeInMillis(), true, true));
        messageList.add(new Message("Hi Ahmed, I am texting you because we need to do somd lk", Calendar.getInstance().getTimeInMillis(), true, true));

        GroupDetailsMessagesAdapter adapter = new GroupDetailsMessagesAdapter("Ahmed Morsi", messageList, new OnGroupChatSelectedCallback() {
            @Override
            public void onGroupChatSelected(Chat chat) {
                if (selectedMessages.contains(chat)) {
                    selectedMessages.remove(chat);
                } else {
                    Intent intent = new Intent(GroupDetailsMessagesActivity.this, ChatActivity.class);
                    intent.putExtra(Constants.CHAT, chat);
                    startActivity(intent);
                }
            }

            @Override
            public void onChatSelectedLongClicked(Chat chat) {
                selectedMessages.add(chat);
                messageSelectedActionsDialog = new MessageSelectedActionsDialog(GroupDetailsMessagesActivity.this);
                messageSelectedActionsDialog.show();
            }
        });
        messagesRecyclerView.setAdapter(adapter);
        messagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}