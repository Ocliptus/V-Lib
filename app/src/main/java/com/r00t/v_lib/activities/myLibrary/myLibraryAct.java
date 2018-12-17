package com.r00t.v_lib.activities.myLibrary;

import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.addBookAct;
import com.r00t.v_lib.activities.explore.explorePageAct;
import com.r00t.v_lib.activities.main.MainAct;
import com.r00t.v_lib.activities.notifications.notificationPageAct;
import com.r00t.v_lib.activities.profilePage.profilePageAct;
import com.r00t.v_lib.activities.start.StartAct;
import com.r00t.v_lib.data.Book;
import com.r00t.v_lib.data.FirebaseImpl;
import com.r00t.v_lib.models.UserDetails;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        String uid = FirebaseImpl.getInstance(getApplicationContext()).getFirebaseUser().getUid();
        FirebaseImpl
                .getInstance(getApplicationContext())
                .getFirestore()
                .collection("users")
                .document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    UserDetails user = task.getResult().toObject(UserDetails.class);
                    String usersBooks = user.getBooks();
                     String[] isbnArray = usersBooks.split("[,]");
                     System.out.println("+=+ ISBN ARRAY HERE"+Arrays.toString(isbnArray));
                     String isbn;
                     int i;
                     dataSet.clear();
                     for (i=1;i<isbnArray.length;i++){
                         isbn= isbnArray[i];
                         isbn = correctISBN(isbn);
                         System.out.println("+=+ ISBN's are here" + isbnArray[i]);
                         FirebaseImpl.getInstance(getApplicationContext())
                                 .getFirestore()
                                 .collection("bookDetails")
                                 .document(isbn)
                                 .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

                             @Override
                             public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                             bookAdapter.notifyDataSetChanged();
                             Book book = task.getResult().toObject(Book.class);
                             dataSet.add(book);
                             }
                         });
                     }

                    Toast.makeText(
                            myLibraryAct.this,
                            "Oh here they are ! I found your books !",
                            Toast.LENGTH_LONG).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(
                        myLibraryAct.this,
                        "Ooops ! There is problem accursed I couldn't get your books. Sorry :/",
                        Toast.LENGTH_LONG).show();
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
    public void myLibClicked() {
        startActivity(new Intent(this, myLibraryAct.class));
    }
    @Override
    public void homeViewClicked() {
        startActivity(new Intent(this, MainAct.class));
    }
    @Override
    public void exploreViewClicked() {
        startActivity(new Intent(this, explorePageAct.class));
    }
    @Override
    public void addBookViewClicked() {
        startActivity(new Intent(this, addBookAct.class));
    }
    @Override
    public void notificationViewClicked() { startActivity(new Intent(this, notificationPageAct.class)); }
    @Override
    public void profileViewClicked() {
        startActivity(new Intent(this, profilePageAct.class));
    }


    public String correctISBN(String isbn) {
        if(isbn.contains("-")){
            String[] isbnArray = isbn.split("[-]");
            isbn="";
            for(int i = 0; i<isbnArray.length; i++)
            {
                isbn+=isbnArray[i];
            }
        }
        if (isbn.contains(" ")){
            String[] isbnArray = isbn.split("[ ]");
            isbn="";
            for(int i = 0; i<isbnArray.length; i++)
            {
                isbn+=isbnArray[i];
            }
        }
        return isbn;
    }
}
