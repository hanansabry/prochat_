package com.egrobots.prochat.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.model.Group;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.GroupViewHolder> {

    private List<Group> groupList;
    private OnGroupSelectedCallback onGroupSelectedCallback;

    public GroupsAdapter(List<Group> groupList, OnGroupSelectedCallback onGroupSelectedCallback) {
        this.groupList = groupList;
        this.onGroupSelectedCallback = onGroupSelectedCallback;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item_layout, parent, false);
        return new GroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.groupName.setText(group.getGroupName());
        holder.groupType.setText(group.getGroupType());
        holder.itemView.setOnClickListener(v -> onGroupSelectedCallback.onGroupSelected(group));
        if (group.isPrivate()) {
            holder.membersLayout.setVisibility(View.VISIBLE);
        } else {
            holder.membersLayout.setVisibility(View.GONE);
        }

        //temp
        if (position== 0) {
            holder.groupImage.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.group_img_1));
        } else if (position == 1) {
            holder.groupImage.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.group_img_2));
        } else if (position == 2) {
            holder.groupImage.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.group_img_3));
        } else if (position == 3) {
            holder.groupImage.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.group_img_4));
        }
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.group_thumb_image_view)
        ImageView groupImage;
        @BindView(R.id.group_name)
        TextView groupName;
        @BindView(R.id.group_type)
        TextView groupType;
        @BindView(R.id.private_group_members_count_layout)
        View membersLayout;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
