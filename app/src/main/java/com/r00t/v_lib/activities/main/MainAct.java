package com.r00t.v_lib.activities.main;

import android.content.Intent;

import com.r00t.v_lib.activities.start.StartAct;
import com.r00t.v_lib.data.FirebaseImpl;

public class MainAct extends MainActivity {
    @Override
    protected void onSignOutClicked() {
        FirebaseImpl.getInstance(this)
                .getFirebaseAuth()
                .signOut();

        startActivity(new Intent(this, StartAct.class));
        finish();
    }
}
