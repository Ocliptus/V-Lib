package com.r00t.v_lib.activities.main;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;

import com.r00t.v_lib.R;

public abstract class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.signOutButton)
    protected abstract void onSignOutClicked();
}
