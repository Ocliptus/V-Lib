package com.r00t.v_lib.activities.sign.up;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.r00t.v_lib.activities.start.StartAct;
import com.r00t.v_lib.data.FirebaseImpl;
import com.r00t.v_lib.models.UserDetails;

import androidx.annotation.NonNull;

public class SignUpAct extends SignUpActivity {
    @Override
    protected void onSignUpClicked() {
        if (checkFields()) {
            FirebaseImpl.getInstance(this)
                    .getFirebaseAuth()
                    .createUserWithEmailAndPassword(userNameET.getText().toString(), passwordET.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                createDatabaseInstance(task.getResult().getUser().getUid());
                            } else {
                                Toast.makeText(
                                        SignUpAct.this,
                                        "Error",
                                        Toast.LENGTH_LONG
                                ).show();
                                task.getException().printStackTrace();
                            }
                        }
                    });
        } else {
            //TODO: Empty fields
        }
    }

    private boolean checkFields() {
        if (TextUtils.isEmpty(userNameET.getText().toString()))
            return false;
        if (TextUtils.isEmpty(passwordET.getText().toString()))
            return false;
        return true;
    }

    private void createDatabaseInstance(String uid) {
        UserDetails temp = new UserDetails();
        temp.setId(uid);
        temp.setData("qweqwe");

        FirebaseImpl.getInstance(this)
                .getFirestore()
                .collection("users")
                .document(uid)
                .set(temp)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        startActivity(new Intent(SignUpAct.this, StartAct.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(
                        SignUpAct.this,
                        "Error",
                        Toast.LENGTH_LONG
                ).show();
                e.printStackTrace();
            }
        });
    }
}
