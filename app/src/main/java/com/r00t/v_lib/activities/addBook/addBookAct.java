package com.r00t.v_lib.activities.addBook;

import android.content.Intent;
import android.os.Bundle;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.isbn.isbnAct;
import com.r00t.v_lib.activities.addBook.manuel.addByManuelAct;

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
    protected void isbnButtonClicked() {
        startActivity(new Intent(this, isbnAct.class));
    }
}

