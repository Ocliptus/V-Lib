package com.r00t.v_lib.activities.addBook.manuel;

import android.content.Intent;
import android.os.Bundle;

import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.addBookAct;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class addByManuelAct extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_by_manuel);

        ButterKnife.bind(this);
    }
}
