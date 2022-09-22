package com.egrobots.prochat.presentation.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.model.ChatMessage;
import com.egrobots.prochat.presentation.dialogs.userprofile.ChatMessageActionsDialog;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ChatMessageViewHolder> {

    private List<ChatMessage> chatMessages;

    public ChatMessagesAdapter(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_layout, parent, false);
        return new ChatMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        holder.chatMessage.setText(chatMessage.getText());
        holder.chatMessageTime.setText(chatMessage.getTime());
        if (chatMessage.isSent()) {
            holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_sent_bg));
            holder.mainLayout.setGravity(Gravity.END);
        } else {
            holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_recieved_bg));
            holder.mainLayout.setGravity(Gravity.START);
        }

        //add long click action to message
        holder.itemView.setOnLongClickListener(v -> {
            if (chatMessage.isSent()) {
                holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_sent_selected_bg));
            } else {
                holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_recieved_selected_bg));
            }
            holder.chatMessage.setTextColor(holder.itemView.getContext().getColor(R.color.White));
            ChatMessageActionsDialog chatMessageActionsDialog = new ChatMessageActionsDialog(holder.itemView.getContext());
            chatMessageActionsDialog.show();
            chatMessageActionsDialog.setOnDismissListener(dialog -> {
                if (chatMessage.isSent()) {
                    holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_sent_bg));
                } else {
                    holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_recieved_bg));
                }
                holder.chatMessage.setTextColor(holder.itemView.getContext().getColor(R.color.SecondaryGray));
            });
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    static class ChatMessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.main_layout)
        LinearLayout mainLayout;
        @BindView(R.id.chat_message_text)
        TextView chatMessage;
        @BindView(R.id.chat_message_time)
        TextView chatMessageTime;

        public ChatMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
