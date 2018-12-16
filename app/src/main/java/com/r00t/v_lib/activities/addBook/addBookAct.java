package com.r00t.v_lib.activities.addBook;

import android.content.Intent;
import android.os.Bundle;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.OCR.OcrCaptureActivity;
import com.r00t.v_lib.activities.addBook.isbn.isbnAct;
import com.r00t.v_lib.activities.addBook.manuel.addByManuelAct;
import com.r00t.v_lib.activities.explore.explorePageAct;
import com.r00t.v_lib.activities.main.MainAct;
import com.r00t.v_lib.activities.notifications.notificationPageAct;
import com.r00t.v_lib.activities.profilePage.profilePageAct;

import butterknife.ButterKnife;

public class addBookAct extends addBookActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        ButterKnife.bind(this);
    }

    @Override
    protected void manuelButtonClicked() {
        startActivity(new Intent(this, addByManuelAct.class));
    }
    @Override
    protected void isbnButtonClicked() { startActivity(new Intent(this, isbnAct.class)); }
    @Override
    protected void ocrButtonClicked() {startActivity(new Intent(this,OcrCaptureActivity.class));}
    @Override
    protected void homeViewClicked(){startActivity(new Intent(this,MainAct.class)); }
    @Override
    protected void exploreViewClicked(){startActivity(new Intent(this,explorePageAct.class)); }
    @Override
    protected void notificationViewClicked(){startActivity(new Intent(this,notificationPageAct.class)); }
    @Override
    protected void profileViewClicked(){startActivity(new Intent(this,profilePageAct.class)); }
}

