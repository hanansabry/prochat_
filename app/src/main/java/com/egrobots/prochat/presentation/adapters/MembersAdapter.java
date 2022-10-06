package com.egrobots.prochat.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.GroupMembersCallback;
import com.egrobots.prochat.model.Member;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MembersAdapter extends RecyclerView.Adapter<MembersAdapter.MemberViewHolder> {

    private List<Member> memberList;
    private GroupMembersCallback groupMembersCallback;

    public MembersAdapter(List<Member> memberList, GroupMembersCallback groupMembersCallback) {
        this.memberList = memberList;
        this.groupMembersCallback = groupMembersCallback;
    }

    @NonNull
    @Override
    public MemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showen_member_item_layout, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberViewHolder holder, int position) {
        Member member = memberList.get(position);
        holder.memberName.setText(member.getName());
        holder.memberCode.setText(member.getCode());
        if (groupMembersCallback != null) {
            holder.itemView.setOnLongClickListener(v -> {
                holder.selectedCheckbox.setVisibility(View.VISIBLE);
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(R.color.GreenyShadeTwo));
                groupMembersCallback.onMemberLongClicked(member);
                return true;
            });
            holder.itemView.setOnClickListener(v -> {
                holder.selectedCheckbox.setVisibility(View.GONE);
                holder.itemView.setBackgroundColor(holder.itemView.getContext().getColor(R.color.White));
                groupMembersCallback.onMemberClicked(member);
            });
        }
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    static class MemberViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.member_name)
        TextView memberName;
        @BindView(R.id.member_code)
        TextView memberCode;
        @BindView(R.id.member_image)
        ImageView memberImage;
        @BindView(R.id.selected_checkbox_button)
        ImageButton selectedCheckbox;

        public MemberViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
