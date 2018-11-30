package com.r00t.v_lib.activities.addBook.isbn;


import android.os.Bundle;

import com.r00t.v_lib.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class isbnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isbn);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.isbnButton)
    protected abstract void isbnButtonClicked();
}
