package com.r00t.v_lib.activities.start;

import android.content.Intent;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.r00t.v_lib.activities.main.MainAct;
import com.r00t.v_lib.activities.sign.in.SignInAct;
import com.r00t.v_lib.activities.sign.up.SignUpAct;
import com.r00t.v_lib.data.FirebaseImpl;

import androidx.annotation.NonNull;

public class StartAct extends StartActivity {

    @Override
    protected void checkUser() {
        if (FirebaseImpl.getInstance(this).getFirebaseUser() == null) {
            direct(SignInAct.class);
        } else {
            FirebaseImpl.getInstance(this)
                    .getFirebaseUser()
                    .reload()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            direct(MainAct.class);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    direct(SignInAct.class);
                    //TODO: Account suspended
                }
            });
        }
    }

    private void direct(Class cls) {
        startActivity(new Intent(this, cls));
        finish();
    }
}
