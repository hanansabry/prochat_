package com.egrobots.prochat.presentation.adapters;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.model.Message;
import com.egrobots.prochat.presentation.dialogs.userprofile.ChatMessageActionsDialog;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatMessagesAdapter extends RecyclerView.Adapter<ChatMessagesAdapter.ChatMessageViewHolder> {

    private List<Message> messages;
    private int lastItemPosition = -1;

    public ChatMessagesAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_message_layout, parent, false);
        return new ChatMessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.chatMessage.setText(message.getText());
        holder.chatMessageTime.setText(message.getFormattedTime());
        if (message.isSent()) {
            holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_sent_bg));
            holder.mainLayout.setGravity(Gravity.END);
        } else {
            holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_recieved_bg));
            holder.mainLayout.setGravity(Gravity.START);
        }

        if (position + 1 < messages.size()) {
            boolean isSentBySameUser = (messages.get(position + 1).isSent() && messages.get(position).isSent()) ||
                    (!messages.get(position + 1).isSent() && !messages.get(position).isSent());
            long timeDiff = messages.get(position + 1).getTime() - messages.get(position).getTime();
            long diffInSec = TimeUnit.MILLISECONDS.toSeconds(timeDiff);
            boolean isDiffLessThanSec = diffInSec < 60;
//            if (isDiffLessThanSec && isSentBySameUser) {
            if (isSentBySameUser) {
                holder.chatMessageTime.setVisibility(View.GONE);
            }
        }

        //add long click action to message
        holder.itemView.setOnLongClickListener(v -> {
            if (message.isSent()) {
                holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_sent_selected_bg));
            } else {
                holder.chatMessage.setBackground(holder.itemView.getContext().getDrawable(R.drawable.chat_message_recieved_selected_bg));
            }
            holder.chatMessage.setTextColor(holder.itemView.getContext().getColor(R.color.White));
            ChatMessageActionsDialog chatMessageActionsDialog = new ChatMessageActionsDialog(holder.itemView.getContext());
            chatMessageActionsDialog.show();
            chatMessageActionsDialog.setOnDismissListener(dialog -> {
                if (message.isSent()) {
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
        return messages.size();
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
