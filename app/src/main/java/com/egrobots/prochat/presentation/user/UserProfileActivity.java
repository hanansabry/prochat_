package com.egrobots.prochat.presentation.user;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.egrobots.prochat.R;
import com.egrobots.prochat.adapters.UserGroupsAdapter;
import com.egrobots.prochat.presentation.dialogs.LoginBottomSheetDialog;
import com.egrobots.prochat.presentation.dialogs.SearchForFriendsBottomSheetDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class UserProfileActivity extends AppCompatActivity {

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