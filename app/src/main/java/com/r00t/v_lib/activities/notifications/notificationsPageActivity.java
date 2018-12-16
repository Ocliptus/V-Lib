package com.r00t.v_lib.activities.notifications;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.r00t.v_lib.R;

public abstract class notificationsPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_page);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        drawer = (DrawerLayout)findViewById(R.id.notificationpage_drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }
    @OnClick(R.id.nav_home)
    protected abstract void homeViewClicked();
    @OnClick(R.id.nav_explore)
    protected abstract void exploreViewClicked();
    @OnClick(R.id.nav_addBook)
    protected abstract void addBookViewClicked();
    @OnClick(R.id.nav_profile)
    protected abstract void profilePageViewClicked();
    }

