package com.r00t.v_lib.activities.sign.in;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.sign.up.SignUpAct;
import com.r00t.v_lib.activities.start.StartAct;
import com.r00t.v_lib.data.FirebaseImpl;

import androidx.annotation.NonNull;

public class SignInAct extends SignInActivity {
    @Override
    protected void onSignUpClicked() {
        startActivity(new Intent(this, SignUpAct.class));
    }

    @Override
    protected void onSignInClicked() {
        if(!checkEmail()){
            Toast.makeText(this,"Invalid email pattern",Toast.LENGTH_LONG).show();
            return;
        }
        if(!checkPassword()){
            Toast.makeText(this,"Invalid Password",Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseImpl.getInstance(this)
                .getFirebaseAuth()
                .signInWithEmailAndPassword(userNameET.getText().toString(), passwordET.getText().toString())
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(SignInAct.this, StartAct.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(
                        SignInAct.this,
                        e.getCause().getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }
    @Override
    protected boolean checkPassword() {
        //TODO: regexi düzelt
        return ((EditText)findViewById(R.id.passwordET)).getText().toString().matches(
                "[\\d\\w\\s]{6,}"
        );
    }

    @Override
    protected boolean checkEmail() {
        return ((EditText)findViewById(R.id.userNameET)).getText().toString().matches(
                "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*"+
                        "|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
                        "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                        "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
                ;
    }
}
