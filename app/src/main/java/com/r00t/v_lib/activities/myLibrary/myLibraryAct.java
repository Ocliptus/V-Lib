package com.r00t.v_lib.activities.myLibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.addBookAct;
import com.r00t.v_lib.activities.notificatins.notificationPageAct;
import com.r00t.v_lib.activities.start.StartAct;
import com.r00t.v_lib.data.Book;
import com.r00t.v_lib.data.FirebaseImpl;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;

public class myLibraryAct extends myLibraryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);
        ScrollView s = findViewById(R.id.myLibScroll);
    }

    @Override
    public void initViews() {
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter((ArrayList<Book>)bookList);

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

    @Override
    public void mylibclicked() {
        startActivity(new Intent(this, myLibraryAct.class));
    }
}
