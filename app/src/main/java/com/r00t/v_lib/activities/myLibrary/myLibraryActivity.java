package com.r00t.v_lib.activities.myLibrary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import android.content.Context;
import android.os.Bundle;

import com.google.android.material.internal.NavigationMenuView;
import com.google.android.material.navigation.NavigationView;
import com.r00t.v_lib.R;
import com.r00t.v_lib.data.Book;

import java.util.List;

public abstract class myLibraryActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.sectorsRV)
    protected RecyclerView sectorsRV;
    protected DrawerLayout drawer;
    protected BookAdapter bookAdapter;
    protected List<Book> bookList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        drawer = (DrawerLayout) findViewById(R.id.mylibrary_drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view_mylib);

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);

        initViews();
        updateDataSet();

    }

    @OnClick(R.id.nav_library)
    @Optional
    public abstract void myLibClicked();

    public abstract void initViews();

    protected abstract void updateDataSet();
    @OnClick(R.id.nav_explore)
    protected abstract void exploreViewClicked();
    @OnClick(R.id.nav_addBook)
    protected abstract void addBookViewClicked();
    @OnClick(R.id.nav_Notifications)
    protected abstract void notificationViewClicked();
    @OnClick(R.id.nav_profile)
    protected abstract void profileViewClicked();
    @OnClick(R.id.nav_home)
    protected abstract void homeViewClicked();
}
