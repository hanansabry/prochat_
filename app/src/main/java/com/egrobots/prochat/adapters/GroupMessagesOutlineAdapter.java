package com.egrobots.prochat.adapters;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.model.GroupMessageOutline;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupMessagesOutlineAdapter extends RecyclerView.Adapter<GroupMessagesOutlineAdapter.GroupMessageOutlineViewHolder> {

    private List<GroupMessageOutline> groupMessageOutlineList;
    private OnGroupSelectedCallback onGroupSelectedCallback;

    public GroupMessagesOutlineAdapter(List<GroupMessageOutline> groupMessageOutlineList, OnGroupSelectedCallback onGroupSelectedCallback) {
        this.groupMessageOutlineList = groupMessageOutlineList;
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
        GroupMessageOutline groupMessageOutline = groupMessageOutlineList.get(position);
        holder.groupMessageTitle.setText(groupMessageOutline.getGroupMessageTitle());
        holder.groupMessageSubTitle.setText(groupMessageOutline.getGroupMessageSubTitle());
        holder.groupMessageDate.setText(groupMessageOutline.getGroupMessageDate());
        if (position == 0 || position == 1) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.GreenyShadeTwo));
            holder.groupMessageTitle.setTypeface(Typeface.DEFAULT_BOLD);
        }

        holder.itemView.setOnClickListener(v -> onGroupSelectedCallback.onGroupSelected(groupMessageOutline));
    }

    @Override
    public int getItemCount() {
        return groupMessageOutlineList.size();
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
