package com.r00t.v_lib.activities.addBook.isbn;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.r00t.v_lib.R;
import com.r00t.v_lib.data.Book;

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
    @OnClick(R.id.isbnAdd)
    public abstract void isbnAddClicked();
    public abstract void isClosePopUpClicked(TextView imgClose);
    public abstract void isAddLibraryClicked(String isbn,Book book,Button addBtn);
    public abstract void isWrongBookClicked(Button btnPass);
}
