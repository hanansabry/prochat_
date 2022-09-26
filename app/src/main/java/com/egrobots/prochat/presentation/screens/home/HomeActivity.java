package com.egrobots.prochat.presentation.screens.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupChatSelectedCallback;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.presentation.screens.home.tabs.ChatsFragment;
import com.egrobots.prochat.presentation.screens.home.tabs.GroupsFragment;
import com.egrobots.prochat.presentation.screens.home.tabs.MembersFragment;
import com.egrobots.prochat.presentation.screens.home.tabs.MyAccountFragment;
import com.egrobots.prochat.presentation.screens.search.SearchForFriendsBottomSheetDialog;
import com.egrobots.prochat.utils.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements OnGroupChatSelectedCallback {

    private FragmentTransaction fragmentTransaction;

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.collapsed_header_layout)
    View collapsedHeaderLayout;
    @BindView(R.id.no_messages_layout)
    View noMessagesLayout;
    @BindView(R.id.home_tab_title_text_view)
    TextView tabTitle;
    @BindView(R.id.home_tab_title_collapsed_text_view)
    TextView tabCollapsedTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        //set scrolling behavior
        setScrollingBehavior();
        fragmentTransaction
                = getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_fragment, ChatsFragment.newInstance());
        fragmentTransaction.commit();
        bottomNavigationView.setSelectedItemId(R.id.chatsFragment);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.chatsFragment:
                        fragmentTransaction
                                = getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_fragment, ChatsFragment.newInstance());
                        fragmentTransaction.commit();
                        tabTitle.setText(getString(R.string.chats));
                        tabCollapsedTitle.setText(getString(R.string.chats));
                        Toast.makeText(HomeActivity.this, "Chats", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.groupsFragment:
                        fragmentTransaction
                                = getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_fragment, GroupsFragment.newInstance());
                        fragmentTransaction.commit();
                        tabTitle.setText(getString(R.string.groups));
                        tabCollapsedTitle.setText(getString(R.string.groups));
                        Toast.makeText(HomeActivity.this, "Groups", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.membersFragment:
                        fragmentTransaction
                                = getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_fragment, MembersFragment.newInstance());
                        fragmentTransaction.commit();
                        tabTitle.setText(getString(R.string.members));
                        tabCollapsedTitle.setText(getString(R.string.members));
                        Toast.makeText(HomeActivity.this, "Members", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myAccountFragment:
                        fragmentTransaction
                                = getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_fragment, MyAccountFragment.newInstance());
                        fragmentTransaction.commit();
                        tabTitle.setText(getString(R.string.my_account));
                        tabCollapsedTitle.setText(getString(R.string.my_account));
                        Toast.makeText(HomeActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
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

    @OnClick(R.id.search_fab)
    public void onSearchFabClicked() {
        SearchForFriendsBottomSheetDialog dialog = new SearchForFriendsBottomSheetDialog(this);
        dialog.show();
    }

    @Override
    public void onGroupChatSelected(Chat chat) {

    }
}