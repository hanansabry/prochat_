package com.egrobots.prochat.presentation.user;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupSelectedCallback;
import com.egrobots.prochat.model.GroupChatOutline;
import com.egrobots.prochat.presentation.adapters.UserGroupsAdapter;
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
    @BindView(R.id.no_messages_layout)
    View noMessagesLayout;
    @BindView(R.id.content_fragment)
    View contentFragment;

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
    private boolean isLogged = false;
    private boolean isChatFragmentCurrentlyOpened;
    private boolean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        ButterKnife.bind(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        //set scrolling behavior
        setScrollingBehavior();
        //set send message button behavior
        sendMessageButton.setEnabled(false);
        sendMessageButton.setClickable(false);
        setTypeMessageEditTextTouchListener();

        //if there are group chats for the group, show the UserProfileContentFragment
        if (data) {
            fragmentTransaction
                    = getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_fragment, UserProfileContentFragment.newInstance());
            fragmentTransaction.commit();
            noMessagesLayout.setVisibility(View.GONE);
            contentFragment.setVisibility(View.VISIBLE);
        } else {
            noMessagesLayout.setVisibility(View.VISIBLE);
            contentFragment.setVisibility(View.GONE);
        }
        //init user groups
        initUserGroups();
    }

    private void setTypeMessageEditTextTouchListener() {
        typeMessageEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (!isChatFragmentCurrentlyOpened) {
                        if (selectGroupLayout.getVisibility() == View.VISIBLE) {
                            selectGroupLayout.setVisibility(View.GONE);
                            sendMessageButton.setVisibility(View.GONE);
                            recordAudioButton.setVisibility(View.VISIBLE);
                            appsButton.setVisibility(View.GONE);
                            addPhotoButton.setVisibility(View.VISIBLE);
                            attachButton.setVisibility(View.VISIBLE);
                            //hide keypad
                            hideKeypad();
                        } else {
                            selectGroupLayout.setVisibility(View.VISIBLE);
                            sendMessageButton.setVisibility(View.VISIBLE);
                            recordAudioButton.setVisibility(View.GONE);
                            appsButton.setVisibility(View.VISIBLE);
                            addPhotoButton.setVisibility(View.GONE);
                            attachButton.setVisibility(View.INVISIBLE);

                            //show keypad
                            hideKeypad();
                        }
                    } else {
                        sendMessageButton.setVisibility(View.VISIBLE);
                        recordAudioButton.setVisibility(View.GONE);
                        appsButton.setVisibility(View.VISIBLE);
                        addPhotoButton.setVisibility(View.GONE);
                        attachButton.setVisibility(View.INVISIBLE);

                        //show keypad
                        showKeypad();
                    }
                }
                return false;
            }
        });

    }

    private void setScrollingBehavior() {
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
    }

    private void initUserGroups() {
        UserGroupsAdapter userGroupsAdapter = new UserGroupsAdapter();
        userGroupsRecyclerView.setAdapter(userGroupsAdapter);
        userGroupsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @OnClick(R.id.collapse_select_group_image_button)
    public void onCollapseUserGroupsClicked() {
        selectGroupLayout.setVisibility(View.GONE);
        if (typeMessageEditText.getText().toString().isEmpty()) {
            setSendMessageInvisible();
        } else {
            setSendMessageVisible();
        }
        //hide keypad
        hideKeypad();
    }

    @OnTextChanged(R.id.type_message_edit_text)
    public void onTypingMessage(CharSequence s, int start, int count, int after) {
        if (s.toString().isEmpty()) {
            disableSendMessageButton();
        } else {
            enableSendMessageButton();
            setSendMessageVisible();
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
            LoginBottomSheetDialog loginDialog = LoginBottomSheetDialog.newInstance();
            loginDialog.show(getSupportFragmentManager(), LoginBottomSheetDialog.TAG);
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
        UserChatFragment chatFragment = (UserChatFragment) getSupportFragmentManager().findFragmentByTag("Chat Fragment");
        if (chatFragment != null && chatFragment.isVisible()) {
            collapsedHeaderLayout.setBackgroundColor(ContextCompat.getColor(UserProfileActivity.this, R.color.White));
        }
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void setSendMessageInvisible() {
        sendMessageButton.setVisibility(View.GONE);
        recordAudioButton.setVisibility(View.VISIBLE);
        attachButton.setVisibility(View.VISIBLE);
        addPhotoButton.setVisibility(View.VISIBLE);
        appsButton.setVisibility(View.GONE);
    }

    private void setSendMessageVisible() {
        sendMessageButton.setVisibility(View.VISIBLE);
        recordAudioButton.setVisibility(View.GONE);
        attachButton.setVisibility(View.GONE);
        addPhotoButton.setVisibility(View.GONE);
        appsButton.setVisibility(View.VISIBLE);
    }

    private void enableSendMessageButton() {
        sendMessageButton.setBackground(getDrawable(R.drawable.active_button_bg));
        sendMessageButton.setEnabled(true);
        sendMessageButton.setClickable(true);
    }

    private void disableSendMessageButton() {
        sendMessageButton.setBackground(getDrawable(R.drawable.dimmed_button_bg));
        sendMessageButton.setEnabled(false);
        sendMessageButton.setClickable(false);
    }

    private void hideKeypad() {
        View view = this.getCurrentFocus();
        if (view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void showKeypad() {
        View view = this.getCurrentFocus();
        if (view != null) {
            imm.showSoftInput(view, 0);
        }
    }

}