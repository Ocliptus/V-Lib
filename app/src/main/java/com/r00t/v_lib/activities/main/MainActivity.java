package com.r00t.v_lib.activities.main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.myLibrary.myLibraryAct;
import com.r00t.v_lib.data.FirebaseImpl;
import com.r00t.v_lib.models.UserDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public abstract class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    protected DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }


    public abstract boolean onNavigationItemSelected(@NonNull MenuItem menuItem);
    @OnClick(R.id.nav_addBook)
    protected abstract void addBookViewClicked();
    @OnClick(R.id.nav_profile)
    protected abstract void profilePageClicked();
    @OnClick(R.id.nav_explore)
    protected abstract void explorePageClicked();
    @OnClick(R.id.nav_Notifications)
    protected abstract void notificationsClicked();

}
