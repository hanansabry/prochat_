package com.egrobots.prochat.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupChatSelectedCallback;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.model.GroupChatOutline;
import com.egrobots.prochat.model.Message;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupDetailsMessagesAdapter extends RecyclerView.Adapter<GroupDetailsMessagesAdapter.GroupMessageOutlineViewHolder> {

    private List<GroupChatOutline> groupChatOutlineList;
    private List<Message> chatList;
    private String memberName;
    private OnGroupChatSelectedCallback onGroupChatSelectedCallback;

//    public GroupMessagesOutlineForOneMemberAdapter(List<GroupChatOutline> groupChatOutlineList, OnGroupChatSelectedCallback onGroupChatSelectedCallback) {
//        this.groupChatOutlineList = groupChatOutlineList;
//        this.onGroupChatSelectedCallback = onGroupChatSelectedCallback;
//    }

    public GroupDetailsMessagesAdapter(String memberName, List<Message> chatList, OnGroupChatSelectedCallback onGroupChatSelectedCallback) {
        this.memberName = memberName;
        this.chatList = chatList;
        this.onGroupChatSelectedCallback = onGroupChatSelectedCallback;
    }

    @NonNull
    @Override
    public GroupMessageOutlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_outline_with_member_name_item_layout, parent, false);
        return new GroupMessageOutlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageOutlineViewHolder holder, int position) {
        Message groupChat = chatList.get(position);
        holder.message.setText(groupChat.getText());
        holder.memberName.setText(memberName);
        Chat chat = new Chat();
        chat.setMessages(Arrays.asList(groupChat));
        if (onGroupChatSelectedCallback != null) {
            holder.itemView.setOnLongClickListener(v -> {
                holder.selectedCheckbox.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(R.color.GreenyShadeTwo));
                onGroupChatSelectedCallback.onGroupChatSelected(chat);
                return true;
            });
            holder.itemView.setOnClickListener(v -> {
                holder.selectedCheckbox.setVisibility(View.GONE);
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(R.color.White));
                onGroupChatSelectedCallback.onGroupChatSelected(chat);
            });
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class GroupMessageOutlineViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.group_message_sub_title)
        TextView message;
        @BindView(R.id.member_name)
        TextView memberName;
        @BindView(R.id.selected_checkbox_button)
        ImageButton selectedCheckbox;

        public GroupMessageOutlineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
