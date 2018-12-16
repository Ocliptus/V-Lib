package com.r00t.v_lib.activities.explore;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.r00t.v_lib.R;

public abstract class explorePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_page);
        ButterKnife.bind(this);
    }



    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.nav_addBook)
    protected abstract void addBookViewClicked();
    @OnClick(R.id.nav_home)
    protected abstract void homeViewClicked();
    @OnClick(R.id.nav_Notifications)
    protected abstract void notificationsViewClicked();
    @OnClick(R.id.nav_profile)
    protected abstract void profileViewClicked();
}
