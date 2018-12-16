package com.r00t.v_lib.activities.explore;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.addBookAct;
import com.r00t.v_lib.activities.explore.explorePageAct;
import com.r00t.v_lib.activities.main.MainAct;
import com.r00t.v_lib.activities.myLibrary.myLibraryAct;
import com.r00t.v_lib.activities.notifications.notificationPageAct;
import com.r00t.v_lib.activities.profilePage.profilePageAct;
import com.r00t.v_lib.activities.start.StartAct;
import com.r00t.v_lib.data.FirebaseImpl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.ButterKnife;

public class explorePageAct extends explorePageActivity {

    protected DrawerLayout drawer;
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_signOut: {
                FirebaseImpl.getInstance(this)
                        .getFirebaseAuth()
                        .signOut();
                Toast.makeText(this, "Signed out", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, StartAct.class));
                finish();
                break;
            }
            case R.id.nav_library:
                startActivity(new Intent(this, myLibraryAct.class));
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }
    @Override
    protected  void homeViewClicked(){
        startActivity(new Intent(this, MainAct.class));
    }
    @Override
    protected void addBookViewClicked() {
        startActivity(new Intent(this, addBookAct.class));
    }
    @Override
    protected void notificationsViewClicked(){ startActivity(new Intent(this, notificationPageAct.class)); }
    @Override
    protected void profileViewClicked(){ startActivity(new Intent(this, profilePageAct.class));}
}
