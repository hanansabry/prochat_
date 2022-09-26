package com.egrobots.prochat.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.model.Member;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecentMembersAdapter extends RecyclerView.Adapter<RecentMembersAdapter.RecentMemberViewHolder> {

    private List<Member> memberList;

    public RecentMembersAdapter(List<Member> memberList) {
        this.memberList = memberList;
    }

    @NonNull
    @Override
    public RecentMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_member_item_layout, parent, false);
        return new RecentMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentMemberViewHolder holder, int position) {
        Member member = memberList.get(position);
        holder.memberNameTextView.setText(member.getName());
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    class RecentMemberViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.member_name_text_view)
        TextView memberNameTextView;
        @BindView(R.id.member_image)
        ShapeableImageView memberImageView;

        public RecentMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
