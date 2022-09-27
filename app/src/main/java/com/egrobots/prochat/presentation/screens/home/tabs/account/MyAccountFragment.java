package com.egrobots.prochat.presentation.screens.home.tabs.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.utils.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;

public class MyAccountFragment extends Fragment {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsed_header_layout)
    View collapsedHeaderLayout;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);
        ButterKnife.bind(this, view);
        setScrollingBehavior();
        return view;
    }

    private void setScrollingBehavior() {
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                if (state == State.COLLAPSED) {
                    collapsedHeaderLayout.setVisibility(View.VISIBLE);
                } else {
                    collapsedHeaderLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @OnClick(R.id.change_password)
    public void onChangePasswordClicked() {
        Toast.makeText(getContext(), getString(R.string.check_your_email_msg), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.change_language_layout)
    public void onChangeLanguageClicked() {
        ChangeLanguageBottomSheetDialog dialog = new ChangeLanguageBottomSheetDialog(getContext());
        dialog.show();
    }

    @OnClick(R.id.more_actions_button)
    public void onMoreActionButtonClicked() {
        ShareAccountDialog dialog = new ShareAccountDialog(getContext());
        dialog.show();
    }
}