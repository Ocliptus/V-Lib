package com.r00t.v_lib.activities.sign.up;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentReference;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.start.StartAct;
import com.r00t.v_lib.data.FirebaseImpl;
import com.r00t.v_lib.models.UserDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;

import androidx.annotation.NonNull;

public class SignUpAct extends SignUpActivity {

    @Override
    protected void onSignUpClicked() {
        if(!checkEmail()){
            Toast.makeText(this,"Invalid email pattern",Toast.LENGTH_LONG).show();
            return;
        }
        if(!checkPassword()){
            Toast.makeText(this,"Invalid Password",Toast.LENGTH_LONG).show();
            return;
        }

        if (checkFields()) {
            FirebaseImpl.getInstance(this)
                    .getFirebaseAuth()
                    .createUserWithEmailAndPassword(userNameET.getText().toString(), passwordET.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uid = task.getResult().getUser().getUid();
                                String eMail = ((EditText)findViewById(R.id.userNameET)).getText().toString();
                                String nameAndSurname =((EditText)findViewById(R.id.nameSurnameET)).getText().toString();
                                createDatabaseInstance(uid,nameAndSurname,eMail,"","","","",0);
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
    private void createDatabaseInstance(String uid,String nameAndSurname,String eMail, String books,
                                        String followers, String followed, String posts, int postCount) {
        UserDetails temp = new UserDetails();
        temp.setId(uid);
        temp.setNameAndSurname(nameAndSurname);
        temp.seteMail(eMail);
        temp.setBooks(books);
        temp.setFollowers(followers);
        temp.setFollowed(followed);
        temp.setPosts(posts);
        temp.setPostCount(postCount);

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

    private boolean checkFields() {
        if (TextUtils.isEmpty(userNameET.getText().toString()))
            return false;
        if (TextUtils.isEmpty(passwordET.getText().toString()))
            return false;
        return true;
    }
    @Override
    protected boolean checkPassword() {
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
