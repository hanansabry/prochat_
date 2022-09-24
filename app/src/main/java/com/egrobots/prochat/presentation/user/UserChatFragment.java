package com.egrobots.prochat.presentation.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.presentation.adapters.ChatMessagesAdapter;
import com.egrobots.prochat.model.Message;
import com.egrobots.prochat.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;

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

    public static final String TAG = "UserChatFragment";

    @BindView(R.id.chat_messages_recycler_view)
    RecyclerView chatMessagesRecyclerView;
    private ChatMessagesAdapter adapter;
    private Chat chat;

    public UserChatFragment() {
        // Required empty public constructor
    }

    public static UserChatFragment newInstance(Chat chat) {
        UserChatFragment userChatFragment = new UserChatFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.CHAT, chat);
        userChatFragment.setArguments(args);
        return userChatFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chat = getArguments().getParcelable(Constants.CHAT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_chat, container, false);
        ButterKnife.bind(this, view);

//        messages.add(new Message("Hi Ahmed, I texting you because we\n" +
//                "need some bla bla tomorrow. So what\n" +
//                "do you think ablout making bla bla?", Calendar.getInstance().getTimeInMillis(), true));
//        messages.add(new Message("Hi Ahmed, I texting you because we\n" +
//                "need some bla bla tomorrow. So what\n" +
//                "do you think ablout making bla bla?", Calendar.getInstance().getTimeInMillis(), true));
//        messages.add(new Message("Okay, Thanks a lot", Calendar.getInstance().getTimeInMillis(), false));
//        messages.add(new Message("Hi Ahmed, I texting you because we\n" +
//                "need some bla bla tomorrow. So what\n" +
//                "do you think ablout making bla bla?", Calendar.getInstance().getTimeInMillis(), false));
//        messages.add(new Message("Okay, Thanks a lot", Calendar.getInstance().getTimeInMillis(), true));

        adapter = new ChatMessagesAdapter(chat.getMessages());
        chatMessagesRecyclerView.setAdapter(adapter);
        chatMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        chatMessagesRecyclerView.scrollToPosition(chat.getMessages().size() - 1);
        return view;
    }

    public void addMessage(String message, long date) {
        chat.getMessages().add(new Message(message, date, true, false));
        adapter.notifyDataSetChanged();
//        chatMessagesRecyclerView.scrollToPosition(chat.getMessages().size() - 1);
    }
}