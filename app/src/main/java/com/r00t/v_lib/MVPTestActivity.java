package com.r00t.v_lib;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public abstract class MVPTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvptest);

        findViewById(R.id.testButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClicked();
            }
        });
    }

    protected abstract void onClicked();
}
