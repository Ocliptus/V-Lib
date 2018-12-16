package com.r00t.v_lib.activities.myLibrary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class myLibraryAct extends myLibraryActivity {
    private List<Book> dataSet;
    private BookAdapter bookAdapter;

    @Override
    public void initViews() {
        dataSet = new ArrayList<>();
        bookAdapter = new BookAdapter(dataSet);
        sectorsRV.setLayoutManager(new LinearLayoutManager(this));
        sectorsRV.setItemAnimator(new DefaultItemAnimator());
        sectorsRV.setAdapter(bookAdapter);
    }

    @Override
    protected void updateDataSet() {
        FirebaseImpl.getInstance(this)
                .getFirestore()
                .collection("book")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        updateUI(queryDocumentSnapshots.toObjects(Book.class));

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void updateUI(List<Book> newDataSet) {
        dataSet.clear();
        dataSet.addAll(newDataSet);
        bookAdapter.notifyDataSetChanged();
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
