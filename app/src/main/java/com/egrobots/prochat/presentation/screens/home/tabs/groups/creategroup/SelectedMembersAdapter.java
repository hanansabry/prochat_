package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.model.Member;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedMembersAdapter extends RecyclerView.Adapter<SelectedMembersAdapter.SelectedMemberViewHolder> {

    private List<Member> memberList = new ArrayList<>();

    @NonNull
    @Override
    public SelectedMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_member_layout, parent, false);
        return new SelectedMemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedMemberViewHolder holder, int position) {
        Member member = memberList.get(position);
        holder.memberName.setText(member.getName());
        holder.removeSelectedMember.setOnClickListener(v -> {
            unSelectMember(member);
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public void selectMember(Member member) {
        memberList.add(member);
        notifyItemInserted(memberList.size()-1);
    }

    public void unSelectMember(Member member) {
        memberList.remove(member);
        notifyDataSetChanged();
    }

    public void removeAll() {
        memberList.clear();
        notifyDataSetChanged();
    }

    class SelectedMemberViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.remove_selected_member)
        ImageButton removeSelectedMember;
        @BindView(R.id.member_name)
        TextView memberName;

        public SelectedMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
