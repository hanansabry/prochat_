package com.egrobots.prochat.presentation.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.egrobots.prochat.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileContentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileContentFragment extends Fragment {

    public UserProfileContentFragment() {
        // Required empty public constructor
    }

    public static UserProfileContentFragment newInstance() {
        return new UserProfileContentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_profile_content, container, false);
    }
}