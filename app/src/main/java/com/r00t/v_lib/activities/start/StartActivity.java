package com.r00t.v_lib.activities.start;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.r00t.v_lib.R;

public abstract class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        checkUser();
    }

    protected abstract void checkUser();
}
