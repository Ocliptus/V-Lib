package com.r00t.v_lib.activities.addBook.manuel;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

import android.os.Bundle;

import com.r00t.v_lib.R;

public abstract class addByManuelActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_by_manuel);

        ButterKnife.bind(this);
    }

}
