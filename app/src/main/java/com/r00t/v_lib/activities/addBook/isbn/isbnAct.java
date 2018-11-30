package com.r00t.v_lib.activities.addBook.isbn;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

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
    public void isbnAddClicked() {
        if (((EditText) findViewById(R.id.isbnET)).getText().toString().matches("(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})\n" +
                        "[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)\n" +
                        "(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$")
                )
        {
            //TODO: Search in books api
        }
        else{
            Toast.makeText(this,"Entry does not fit the standart rules",Toast.LENGTH_LONG).show();
        }
    }

}
