package com.r00t.v_lib.activities.addBook.manuel;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.r00t.v_lib.R;

public abstract class addByManuelActivity extends AppCompatActivity {
    protected final EditText etBookName = (EditText) findViewById(R.id.bookNameET),
                    etAuthour = (EditText)findViewById(R.id.writerNameET),
                    etPublisher = (EditText) findViewById(R.id.publisherET),
                    etPublishedDate = (EditText)findViewById(R.id.publishDateET)
            ;
    protected final Button btnAdd = (Button)findViewById(R.id.addBookButton);
    protected String queryString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_by_manuel);

        ButterKnife.bind(this);
    }
    @OnClick(R.id.addBookButton)
    protected abstract void addBookButtonClicked();


}
