package com.egrobots.prochat.presentation.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.model.GroupChatOutline;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupMessagesOutlineForOneMemberAdapter extends RecyclerView.Adapter<GroupMessagesOutlineForOneMemberAdapter.GroupMessageOutlineViewHolder> {

    private List<GroupChatOutline> groupChatOutlineList;
    private List<Chat> chatList;
    private String groupName;
    private OnGroupSelectedCallback onGroupSelectedCallback;

//    public GroupMessagesOutlineForOneMemberAdapter(List<GroupChatOutline> groupChatOutlineList, OnGroupSelectedCallback onGroupSelectedCallback) {
//        this.groupChatOutlineList = groupChatOutlineList;
//        this.onGroupSelectedCallback = onGroupSelectedCallback;
//    }

    public GroupMessagesOutlineForOneMemberAdapter(String groupName, List<Chat> chatList, OnGroupSelectedCallback onGroupSelectedCallback) {
        this.groupName = groupName;
        this.chatList = chatList;
        this.onGroupSelectedCallback = onGroupSelectedCallback;
    }

    @NonNull
    @Override
    public GroupMessageOutlineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_message_outline_item_layout, parent, false);
        return new GroupMessageOutlineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupMessageOutlineViewHolder holder, int position) {
        Chat groupChat = chatList.get(position);
        int chatSize = groupChat.getMessages().size();
        String outlineTitle = groupName;
        String outlineSubTitle = groupChat.getMessages().get(chatSize - 1).getText();
        String time = groupChat.getMessages().get(chatSize - 1).getFormattedTime();

        holder.groupMessageTitle.setText(outlineTitle);
        holder.groupMessageSubTitle.setText(outlineSubTitle);
        holder.groupMessageDate.setText(time);
        if (position == 0 || position == 1) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.GreenyShadeTwo));
            holder.groupMessageTitle.setTypeface(Typeface.DEFAULT_BOLD);
        }

        holder.itemView.setOnClickListener(v -> onGroupSelectedCallback.onGroupChatSelected(groupChat));
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class GroupMessageOutlineViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.group_message_title)
        TextView groupMessageTitle;
        @BindView(R.id.group_message_date)
        TextView groupMessageDate;
        @BindView(R.id.group_message_sub_title)
        TextView groupMessageSubTitle;

        public GroupMessageOutlineViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
