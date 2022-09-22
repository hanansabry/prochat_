package com.egrobots.prochat.presentation.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.presentation.adapters.ChatMessagesAdapter;
import com.egrobots.prochat.model.ChatMessage;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserChatFragment extends Fragment {

    @BindView(R.id.chat_messages_recycler_view)
    RecyclerView chatMessagesRecyclerView;
    private ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    private ChatMessagesAdapter adapter;

    public UserChatFragment() {
        // Required empty public constructor
    }

    public static UserChatFragment newInstance() {
        return new UserChatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_chat, container, false);
        ButterKnife.bind(this, view);

        chatMessages.add(new ChatMessage("Hi Ahmed, I texting you because we\n" +
                "need some bla bla tomorrow. So what\n" +
                "do you think ablout making bla bla?", "08:24 pm", true));
        chatMessages.add(new ChatMessage("Hi Ahmed, I texting you because we\n" +
                "need some bla bla tomorrow. So what\n" +
                "do you think ablout making bla bla?", "08:24 pm", true));
        chatMessages.add(new ChatMessage("Okay, Thanks a lot", "08:24 pm", false));
        chatMessages.add(new ChatMessage("Hi Ahmed, I texting you because we\n" +
                "need some bla bla tomorrow. So what\n" +
                "do you think ablout making bla bla?", "08:24 pm", false));
        chatMessages.add(new ChatMessage("Okay, Thanks a lot", "08:24 pm", true));

        adapter = new ChatMessagesAdapter(chatMessages);
        chatMessagesRecyclerView.setAdapter(adapter);
        chatMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatMessagesRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
        return view;
    }

    public void addMessage(String message, String date) {
        chatMessages.add(new ChatMessage(message, date, true));
        adapter.notifyDataSetChanged();
        chatMessagesRecyclerView.smoothScrollToPosition(chatMessages.size() - 1);
    }
}