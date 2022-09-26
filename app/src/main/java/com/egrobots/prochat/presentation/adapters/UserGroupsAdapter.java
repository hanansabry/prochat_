package com.egrobots.prochat.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.egrobots.prochat.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserGroupsAdapter extends RecyclerView.Adapter<UserGroupsAdapter.UserGroupViewHolder> {

    private List<String> groupList = new ArrayList<>();
    public UserGroupsAdapter() {
        //init temp list
        groupList.add("General Group");
        groupList.add("Company Clients");
        groupList.add("Group Name");
        groupList.add("Need A Freelancing Project?");
        groupList.add("Ask Me");
    }

    @NonNull
    @Override
    public UserGroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choosing_group_item_layout, parent, false);
        return new UserGroupViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserGroupViewHolder holder, int position) {
        String groupItem = groupList.get(position);
        holder.groupName.setText(groupItem);
        if (position == 0) {
            holder.itemView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.user_group_bg1));
            holder.imageBg.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.linear_gradient_dark_bg));
        } else if (position == 1) {
            holder.itemView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.user_group_bg3));
            holder.imageBg.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.linear_gradient_dark_bg));
        } else if (position == 2) {
            holder.itemView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.user_group_bg2));
            holder.imageBg.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.linear_gradient_light_bg));
            holder.groupName.setTextColor(holder.itemView.getContext().getColor(R.color.colorPrimary));
        } else if (position == 3) {
            holder.itemView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.user_group_bg4));
            holder.imageBg.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.linear_gradient_dark_bg));
        } else if (position == 4) {
            holder.itemView.setBackground(holder.itemView.getContext().getDrawable(R.drawable.user_group_bg5));
            holder.imageBg.setImageDrawable(holder.itemView.getContext().getDrawable(R.drawable.linear_gradient_dark_bg));
        }
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    static class UserGroupViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.group_name_text_view)
        TextView groupName;
        @BindView(R.id.image_bg)
        ImageView imageBg;

        public UserGroupViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
