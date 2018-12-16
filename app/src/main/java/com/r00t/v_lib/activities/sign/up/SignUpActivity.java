package com.r00t.v_lib.activities.sign.up;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.r00t.v_lib.R;

public abstract class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.mainView)
    protected LinearLayout mainView;

    @BindView(R.id.userNameET)
    protected EditText userNameET;
    @BindView(R.id.passwordET)
    protected EditText passwordET;
    @BindView(R.id.nameSurnameET)
    protected EditText nameSurnameET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.signUpButton)
    protected abstract void onSignUpClicked();
    protected abstract boolean checkEmail();
    protected abstract boolean checkPassword();

}
