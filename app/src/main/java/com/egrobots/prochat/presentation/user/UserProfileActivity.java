package com.egrobots.prochat.presentation.user;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.egrobots.prochat.R;
import com.egrobots.prochat.adapters.UserGroupsAdapter;
import com.egrobots.prochat.adapters.UserGroupsFragmentAdapter;
import com.egrobots.prochat.presentation.dialogs.LoginBottomSheetDialog;
import com.egrobots.prochat.presentation.dialogs.SearchForFriendsBottomSheetDialog;
import com.egrobots.prochat.utils.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UserProfileActivity extends AppCompatActivity {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.collapsed_header_layout)
    View collapsedHeaderLayout;

    @BindView(R.id.group_tabs_viewpager)
    ViewPager2 groupTabsViewPager;
    @BindView(R.id.groups_tablayout)
    TabLayout groupTabsLayout;
    @BindView(R.id.type_message_edit_text)
    EditText typeMessageEditText;
    @BindView(R.id.record_audio_button)
    ImageButton recordAudioButton;
    @BindView(R.id.send_message_button)
    ImageButton sendMessageButton;
    @BindView(R.id.user_groups_recycler_view)
    RecyclerView userGroupsRecyclerView;
    @BindView(R.id.select_group_layout)
    View selectGroupLayout;
    @BindView(R.id.add_photo_button)
    ImageButton addPhotoButton;
    @BindView(R.id.apps_button)
    ImageButton appsButton;
    @BindView(R.id.attach_button)
    ImageButton attachButton;
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
                    collapsedHeaderLayout.setVisibility(View.VISIBLE);
                } else {
                    collapsedHeaderLayout.setVisibility(View.GONE);
                }
            }
        });

        sendMessageButton.setEnabled(false);
        sendMessageButton.setClickable(false);
        typeMessageEditText.setShowSoftInputOnFocus(false);
        typeMessageEditText.requestFocus();
        typeMessageEditText.setOnClickListener(v -> {
            if (selectGroupLayout.getVisibility() == View.VISIBLE) {
                selectGroupLayout.setVisibility(View.GONE);
                sendMessageButton.setVisibility(View.GONE);
                recordAudioButton.setVisibility(View.VISIBLE);
                appsButton.setVisibility(View.GONE);
                addPhotoButton.setVisibility(View.VISIBLE);
                attachButton.setVisibility(View.VISIBLE);
                //hide keypad
                View view = this.getCurrentFocus();
                if (view != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            } else {
                selectGroupLayout.setVisibility(View.VISIBLE);
                sendMessageButton.setVisibility(View.VISIBLE);
                recordAudioButton.setVisibility(View.GONE);
                appsButton.setVisibility(View.VISIBLE);
                addPhotoButton.setVisibility(View.GONE);
                attachButton.setVisibility(View.INVISIBLE);

                //show keypad
                View view = this.getCurrentFocus();
                if (view != null) {
                    imm.showSoftInput(view, 0);
                }
            }
        });

        //init user groups
        initUserGroups();
        //setup groups tab
        setupGroupTabs();
    }

    private void setupGroupTabs() {
        UserGroupsFragmentAdapter groupsFragmentAdapter = new UserGroupsFragmentAdapter(getSupportFragmentManager(), getLifecycle());
        groupTabsViewPager.setAdapter(groupsFragmentAdapter);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(groupTabsLayout, groupTabsViewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("All");
            } else if (position == 1) {
                tab.setText("My Groups");
            } else if (position == 2) {
                tab.setText("Group 1");
            } else if (position == 3) {
                tab.setText("Group 2");
            } else if (position == 4) {
                tab.setText("Group 3");
            } else if (position == 5) {
                tab.setText("Group 4");
            } else if (position == 6) {
                tab.setText("Group 5");
            }
        });
        tabLayoutMediator.attach();
    }

    private void initUserGroups() {
        UserGroupsAdapter userGroupsAdapter = new UserGroupsAdapter();
        userGroupsRecyclerView.setAdapter(userGroupsAdapter);
        userGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.collapse_select_group_image_button)
    public void onCollapseUserGroupsClicked() {
        selectGroupLayout.setVisibility(View.GONE);
        sendMessageButton.setVisibility(View.GONE);
        recordAudioButton.setVisibility(View.VISIBLE);
        //hide keypad
        View view = this.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @OnTextChanged(R.id.type_message_edit_text)
    public void onTypingMessage(CharSequence s, int start, int count, int after) {
        if (s.toString().isEmpty()) {
            sendMessageButton.setBackground(getDrawable(R.drawable.dimmed_button_bg));
            sendMessageButton.setEnabled(false);
            sendMessageButton.setClickable(false);
        } else {
            sendMessageButton.setBackground(getDrawable(R.drawable.active_button_bg));
            sendMessageButton.setEnabled(true);
            sendMessageButton.setClickable(true);
        }
    }

    @OnClick(R.id.send_message_button)
    public void onSendMessageClicked() {
        //if user is not logged, show login bottom sheet dialog
        LoginBottomSheetDialog dialog = new LoginBottomSheetDialog(this);
        dialog.show();
    }
}