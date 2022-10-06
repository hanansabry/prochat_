package com.egrobots.prochat.presentation.screens.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.egrobots.prochat.R;
import com.egrobots.prochat.callbacks.OnGroupChatSelectedCallback;
import com.egrobots.prochat.model.Chat;
import com.egrobots.prochat.presentation.screens.home.tabs.ChatsFragment;
import com.egrobots.prochat.presentation.screens.home.tabs.groups.GroupsFragment;
import com.egrobots.prochat.presentation.screens.home.tabs.MembersFragment;
import com.egrobots.prochat.presentation.screens.home.tabs.account.MyAccountFragment;
import com.egrobots.prochat.presentation.screens.search.SearchForFriendsBottomSheetDialog;
import com.egrobots.prochat.utils.AppBarStateChangeListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class HomeActivity extends AppCompatActivity implements OnGroupChatSelectedCallback {

    private FragmentTransaction fragmentTransaction;

    @BindView(R.id.content_fragment)
    FrameLayout contentFragment;
    @BindView(R.id.coordinator)
    CoordinatorLayout coordinatorLayout;
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
    @BindView(R.id.notifications_button)
    ImageButton notificationsButton;

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
                        setTabTitle(R.string.chats);
                        showAppBarLayout();
                        setNotificationIconColorTint(R.color.colorPrimary);
                        setStatusBarTextDark();
                        Toast.makeText(HomeActivity.this, "Chats", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.groupsFragment:
                        fragmentTransaction
                                = getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_fragment, GroupsFragment.newInstance());
                        fragmentTransaction.commit();
                        setTabTitle(R.string.groups);
                        showAppBarLayout();
                        setNotificationIconColorTint(R.color.colorPrimary);
                        setStatusBarTextDark();
                        Toast.makeText(HomeActivity.this, "Groups", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.membersFragment:
                        fragmentTransaction
                                = getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_fragment, MembersFragment.newInstance());
                        fragmentTransaction.commit();
                        setTabTitle(R.string.members);
                        showAppBarLayout();
                        setNotificationIconColorTint(R.color.colorPrimary);
                        setStatusBarTextDark();
                        Toast.makeText(HomeActivity.this, "Members", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.myAccountFragment:
                        fragmentTransaction
                                = getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_fragment, MyAccountFragment.newInstance());
                        fragmentTransaction.commit();
                        setTabTitle(R.string.my_account);
                        hideAppBarLayout();
                        setNotificationIconColorTint(R.color.White);
                        setStatusBarTextLight();
                        Toast.makeText(HomeActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
    }

    private void setTabTitle(int title) {
        tabTitle.setText(getString(title));
        tabCollapsedTitle.setText(title);
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

    private void hideAppBarLayout() {
        appBarLayout.setVisibility(View.GONE);
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) contentFragment.getLayoutParams();
        params.setBehavior(null);
        contentFragment.requestLayout();
    }

    private void showAppBarLayout() {
        appBarLayout.setVisibility(View.VISIBLE);
        CoordinatorLayout.LayoutParams params =
                (CoordinatorLayout.LayoutParams) contentFragment.getLayoutParams();
        params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
    }

    private void setNotificationIconColorTint(int color) {
        notificationsButton.setColorFilter(ContextCompat.getColor(this, color), android.graphics.PorterDuff.Mode.SRC_IN);
    }

    private void setStatusBarTextLight() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    private void setStatusBarTextDark() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @OnClick(R.id.search_fab)
    public void onSearchFabClicked() {
        SearchForFriendsBottomSheetDialog dialog = SearchForFriendsBottomSheetDialog.newInstance();
        dialog.show(getSupportFragmentManager(), SearchForFriendsBottomSheetDialog.TAG);
    }

    @Override
    public void onGroupChatSelected(Chat chat) {

    }

    @Override
    public void onChatSelectedLongClicked(Chat chat) {

    }
}