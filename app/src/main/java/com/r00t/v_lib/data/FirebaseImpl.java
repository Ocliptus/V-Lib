package com.r00t.v_lib.data;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseImpl {
    private static volatile FirebaseImpl instance;

    private FirebaseFirestore firestore;

    FirebaseImpl(Context context) {
        FirebaseApp.initializeApp(context);
        firestore = FirebaseFirestore.getInstance();
    }

    public static FirebaseImpl getInstance(Context context) {
        if (instance == null)
            synchronized (FirebaseImpl.class) {
                if (instance == null)
                    instance = new FirebaseImpl(context.getApplicationContext());
            }
        return instance;
    }

    public FirebaseFirestore getFirestore() {
        return firestore;
    }
}
