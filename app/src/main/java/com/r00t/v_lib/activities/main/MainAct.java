package com.r00t.v_lib.activities.main;

import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.addBookAct;
import com.r00t.v_lib.activities.myLibrary.myLibraryAct;
import com.r00t.v_lib.activities.start.StartAct;
import com.r00t.v_lib.data.FirebaseImpl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;

public class MainAct extends MainActivity {


    @Override
    protected void addBookViewClicked() {

        startActivity(new Intent(this, addBookAct.class));

    }
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

}
