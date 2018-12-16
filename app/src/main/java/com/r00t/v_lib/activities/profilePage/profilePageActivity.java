package com.r00t.v_lib.activities.profilePage;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.r00t.v_lib.R;
import com.r00t.v_lib.data.FirebaseImpl;
import com.r00t.v_lib.models.UserDetails;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class profilePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    protected DrawerLayout drawer;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
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
        TextView usernameTV = findViewById(R.id.userNameTV);
        final String userUID =FirebaseImpl.getInstance(this).getFirebaseUser().getUid();
        FirebaseImpl.getInstance(this).getFirestore().collection("users").document(userUID).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                   UserDetails user = task.getResult().toObject(UserDetails.class);
                   String userID = user.getId();
                   String books = user.getBooks();
                   String eMail = user.geteMail();
                   String followed = user.getFollowed();
                   String followers = user.getFollowers();
                   int postCount = user.getPostCount();
                   String posts = user.getPosts();
                   String userName = user.getUserName();
                   Toast.makeText(getApplicationContext(), "UserID :" + userID + "Books : " + books +
                           "E-Mail :" + eMail + "Followed : " + followed + "Followers : " + followers
                           + "Post Count :" + postCount + "Posts : "+ posts + "User Name :" + userName, Toast.LENGTH_LONG).show();



                }else{
                    Toast.makeText(getApplicationContext(), "Oops ! We couldn't get your account details ! Sorry :(", Toast.LENGTH_LONG).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Oops ! We couldn't get your account details ! Sorry :(", Toast.LENGTH_LONG).show();
            }
        });

    }
    @OnClick(R.id.nav_addBook)
    protected abstract void addBookViewClicked();
}
