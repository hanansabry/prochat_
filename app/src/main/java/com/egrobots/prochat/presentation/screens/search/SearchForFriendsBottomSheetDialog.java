package com.egrobots.prochat.presentation.screens.search;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.egrobots.prochat.R;
import com.egrobots.prochat.presentation.screens.userprofile.UserProfileActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class SearchForFriendsBottomSheetDialog extends BottomSheetDialog {

    @BindView(R.id.deactivated_search_edit_text)
    EditText deactivatedSearchEditText;
    @BindView(R.id.activated_search_edit_text)
    EditText activatedSearchEditText;
    @BindView(R.id.search_layout_deactivated)
    View deactivatedSearchLayout;
    @BindView(R.id.search_layout_activated)
    View activatedSearchLayout;

    @BindView(R.id.not_searching_yet_layout)
    View notSearchYetLayout;
    @BindView(R.id.not_found_user_search_layout)
    View notFoundUserSearchLayout;
    @BindView(R.id.user_found_layout)
    View userFoundLayout;

    public SearchForFriendsBottomSheetDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.search_for_friends_dialog);
        ButterKnife.bind(this);
        deactivatedSearchEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    deactivatedSearchLayout.setVisibility(View.INVISIBLE);
                    activatedSearchLayout.setVisibility(View.VISIBLE);
                    activatedSearchEditText.requestFocus();
                    //show keypad
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        });
    }

    @OnClick(R.id.search_back_button)
    public void onSearchBackButtonClicked() {
        deactivatedSearchLayout.setVisibility(View.VISIBLE);
        activatedSearchLayout.setVisibility(View.INVISIBLE);
        userFoundLayout.setVisibility(View.GONE);
        notFoundUserSearchLayout.setVisibility(View.GONE);
        notSearchYetLayout.setVisibility(View.VISIBLE);
    }

    @OnTextChanged(R.id.activated_search_edit_text)
    public void onSearchTextChanged(CharSequence s, int start, int count, int after) {
        if (s.toString().equals("")) {
            userFoundLayout.setVisibility(View.GONE);
            notFoundUserSearchLayout.setVisibility(View.GONE);
            notSearchYetLayout.setVisibility(View.VISIBLE);
        } else if (s.toString().equals("am1234")) {
            //show the user
            userFoundLayout.requestFocus();
            userFoundLayout.setVisibility(View.VISIBLE);
            notFoundUserSearchLayout.setVisibility(View.GONE);
            notSearchYetLayout.setVisibility(View.GONE);
        } else {
            //show no results found
            userFoundLayout.setVisibility(View.GONE);
            notFoundUserSearchLayout.setVisibility(View.VISIBLE);
            notSearchYetLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.close_dialog_button)
    public void onCloseDialogClicked() {
        dismiss();
    }

    @OnClick(R.id.contact_button)
    public void onContactUserButtonClicked() {
        getContext().startActivity(new Intent(getContext(), UserProfileActivity.class));
    }
}
