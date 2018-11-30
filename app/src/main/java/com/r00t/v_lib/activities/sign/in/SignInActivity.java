package com.r00t.v_lib.activities.sign.in;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.r00t.v_lib.R;

public abstract class SignInActivity extends AppCompatActivity {
    @BindView(R.id.mainView)
    protected LinearLayout mainView;

    @BindView(R.id.userNameET)
    protected EditText userNameET;
    @BindView(R.id.passwordET)
    protected EditText passwordET;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.signUpTV)
    protected abstract void onSignUpClicked();

    @OnClick(R.id.signInButton)
    protected abstract void onSignInClicked();
    protected abstract boolean checkEmail();
    protected abstract boolean checkPassword();
}
