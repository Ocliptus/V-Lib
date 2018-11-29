package com.r00t.v_lib.activities.sign.in;

import android.content.Intent;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
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
}
