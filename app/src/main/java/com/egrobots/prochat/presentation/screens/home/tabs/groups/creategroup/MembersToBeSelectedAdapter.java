package com.egrobots.prochat.presentation.screens.home.tabs.groups.creategroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnSelectMemberToAddToGroupCallback;
import com.egrobots.prochat.model.Member;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MembersToBeSelectedAdapter extends RecyclerView.Adapter<MembersToBeSelectedAdapter.MemberToBeSelectedViewHolder>
implements Filterable {

    private List<Member> memberList;
    private List<Member> memberListFull;
    private OnSelectMemberToAddToGroupCallback onSelectMemberToAddToGroupCallback;

    public MembersToBeSelectedAdapter(List<Member> memberList, OnSelectMemberToAddToGroupCallback onSelectMemberToAddToGroupCallback) {
        this.memberList = memberList;
        this.onSelectMemberToAddToGroupCallback = onSelectMemberToAddToGroupCallback;
        memberListFull = new ArrayList<>(memberList);
    }

    @NonNull
    @Override
    public MemberToBeSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.member_item_to_be_selected_layout, parent, false);
        return new MemberToBeSelectedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberToBeSelectedViewHolder holder, int position) {
        Member member = memberList.get(position);
        holder.memberName.setText(member.getName());
        holder.memberCode.setText(member.getCode());
        holder.selectMemberButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onSelectMemberToAddToGroupCallback.onSelectMember(member);
                } else {
                    onSelectMemberToAddToGroupCallback.onRemoveSelection(member);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    @Override
    public Filter getFilter() {
        return membersFilter;
    }

    private Filter membersFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Member> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(memberListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Member member : memberListFull) {
                    if (member.getName().toLowerCase().contains(filterPattern)
                            || member.getCode().toLowerCase().contains(filterPattern)) {
                        filteredList.add(member);
                    }
                }
                //search for member not found in the list by the code
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (((List)results.values).isEmpty()) {
                onSelectMemberToAddToGroupCallback.onNotFoundMember();
            } else {
                memberList.clear();
                memberList.addAll((List) results.values);
                onSelectMemberToAddToGroupCallback.onFoundMembers();
                notifyDataSetChanged();
            }
        }
    };

    class MemberToBeSelectedViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.member_name)
        TextView memberName;
        @BindView(R.id.member_code)
        TextView memberCode;
        @BindView(R.id.select_member_button)
        CheckBox selectMemberButton;

        public MemberToBeSelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
