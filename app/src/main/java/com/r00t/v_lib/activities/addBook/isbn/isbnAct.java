package com.r00t.v_lib.activities.addBook.isbn;

import android.content.Intent;
import android.os.Bundle;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.manuel.addByManuelAct;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class isbnAct extends isbnActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isbn);
        ButterKnife.bind(this);
    }
    @Override
    protected void isbnButtonClicked(){startActivity(new Intent(this, isbnAct.class));}
}
