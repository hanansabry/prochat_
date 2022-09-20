package com.egrobots.prochat.presentation.user.groups;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;
import com.egrobots.prochat.adapters.GroupMessagesOutlineAdapter;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.model.GroupMessageOutline;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {

    @BindView(R.id.group_messages_recycler_view)
    RecyclerView groupMessagesRecyclerView;

    private OnGroupSelectedCallback onGroupSelectedCallback;

    public GroupFragment() {
        // Required empty public constructor
    }
    public static GroupFragment newInstance() {
        return new GroupFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            onGroupSelectedCallback = (OnGroupSelectedCallback) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group, container, false);
        ButterKnife.bind(this, view);

        List<GroupMessageOutline> groupMessages = new ArrayList<>();
        groupMessages.add(new GroupMessageOutline("Morsi’s Work Group", "Hi Ahmed, I am texting you because we need to discu...", "21 Aug 2022"));
        groupMessages.add(new GroupMessageOutline("Ask Me", "Hi, Please check your mail.", "21 Aug 2022"));
        groupMessages.add(new GroupMessageOutline("A.Morsi General Group", "Hi Ahmed, I am texting you because we need to discu...", "10:50 pm"));
        groupMessages.add(new GroupMessageOutline("Company Team", "Hi, Please check your mail.", "Yesterday"));
        groupMessages.add(new GroupMessageOutline("Morsi’s Work Group", "Hi Ahmed, I am texting you because we need to discu...", "21 Aug 2022"));
        groupMessages.add(new GroupMessageOutline("Ask Me", "Hi, Please check your mail.", "10:50 pm"));

        GroupMessagesOutlineAdapter adapter = new GroupMessagesOutlineAdapter(groupMessages, onGroupSelectedCallback);
        groupMessagesRecyclerView.setAdapter(adapter);
        groupMessagesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onDetach() {
        onGroupSelectedCallback = null;
        super.onDetach();
    }
}