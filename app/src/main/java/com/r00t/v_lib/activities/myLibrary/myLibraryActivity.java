package com.r00t.v_lib.activities.myLibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.r00t.v_lib.R;

public abstract class myLibraryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);
    }
}
