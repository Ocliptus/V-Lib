package com.r00t.v_lib.data;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class FirebaseImpl {
    private static volatile FirebaseImpl instance;

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private FirebaseFirestore firestore;
    private FirebaseStorage storage;

    FirebaseImpl(Context context) {
        FirebaseApp.initializeApp(context);
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public static FirebaseImpl getInstance(Context context) {
        if (instance == null)
            synchronized (FirebaseImpl.class) {
                if (instance == null)
                    instance = new FirebaseImpl(context.getApplicationContext());
            }
        return instance;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }

    public FirebaseUser getFirebaseUser() {
        return getFirebaseAuth().getCurrentUser();
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    public StorageReference getStorageRef() {
        return getStorage().getReference();
    }
}
