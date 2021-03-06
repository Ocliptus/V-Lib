package com.r00t.v_lib.activities.addBook;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;

import com.r00t.v_lib.R;

public abstract class addBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        ButterKnife.bind(this);
    }
    @OnClick(R.id.manuelButton)
    protected abstract void manuelButtonClicked();
    @OnClick (R.id.isbnButton)
    protected abstract void isbnButtonClicked();
    @OnClick(R.id.ocrButton)
    protected abstract void ocrButtonClicked();
    @OnClick(R.id.nav_home)
    protected abstract void homeViewClicked();
    @OnClick(R.id.nav_explore)
    protected abstract void exploreViewClicked();
    @OnClick(R.id.nav_Notifications)
    protected abstract void notificationViewClicked();
    @OnClick(R.id.nav_profile)
    protected abstract void profileViewClicked();

}


