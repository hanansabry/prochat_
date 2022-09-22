package com.egrobots.prochat.presentation.user;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.egrobots.prochat.R;
import com.egrobots.prochat.presentation.adapters.UserGroupsAdapter;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.model.GroupChatOutline;
import com.egrobots.prochat.presentation.dialogs.authentication.LoginBottomSheetDialog;
import com.egrobots.prochat.presentation.dialogs.userprofile.UserProfileActionsBottomSheetDialog;
import com.egrobots.prochat.utils.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UserProfileActivity extends AppCompatActivity implements OnGroupSelectedCallback {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.collapsed_header_layout)
    View collapsedHeaderLayout;

    @BindView(R.id.user_profile_main_header)
    View mainProfileHeader;

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
    private FragmentTransaction fragmentTransaction;
    private boolean isLogged = true;
    private boolean isChatFragmentCurrentlyOpened;

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

        fragmentTransaction
                = getSupportFragmentManager().beginTransaction()
                .add(R.id.content_fragment, UserProfileContentFragment.newInstance());
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        sendMessageButton.setEnabled(false);
        sendMessageButton.setClickable(false);
        typeMessageEditText.setShowSoftInputOnFocus(false);
        typeMessageEditText.requestFocus();
        typeMessageEditText.setOnClickListener(v -> {
            if (!isChatFragmentCurrentlyOpened) {
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
            } else {
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
        if (isLogged) {
            //send the message
            UserChatFragment userChatFragment = (UserChatFragment) getSupportFragmentManager().findFragmentByTag("Chat Fragment");
            userChatFragment.addMessage(typeMessageEditText.getText().toString(), "11:20 am");
            typeMessageEditText.setText("");
        } else {
            LoginBottomSheetDialog dialog = new LoginBottomSheetDialog(this);
            dialog.show();
        }
    }

    @Override
    public void onGroupChatSelected(GroupChatOutline groupChatOutline) {
        isChatFragmentCurrentlyOpened = true;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.content_fragment, UserChatFragment.newInstance(), "Chat Fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        appBarLayout.setExpanded(false);
        collapsedHeaderLayout.setBackgroundColor(ContextCompat.getColor(UserProfileActivity.this, R.color.colorSecondary));
    }

    @OnClick(R.id.back_button)
    public void onMainBackArrowClicked() {
        onBackPressed();
    }

    @OnClick(R.id.actions_button)
    public void onActionsButtonClicked() {
        UserProfileActionsBottomSheetDialog actionsDialog = new UserProfileActionsBottomSheetDialog(this);
        actionsDialog.show();
    }

    @Override
    public void onBackPressed() {
        UserChatFragment chatFragment = (UserChatFragment)getSupportFragmentManager().findFragmentByTag("Chat Fragment");
        if (chatFragment != null && chatFragment.isVisible()) {
            collapsedHeaderLayout.setBackgroundColor(ContextCompat.getColor(UserProfileActivity.this, R.color.White));
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}