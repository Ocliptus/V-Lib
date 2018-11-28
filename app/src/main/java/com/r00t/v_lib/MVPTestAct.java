package com.r00t.v_lib;

import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;
import com.r00t.v_lib.data.FirebaseImpl;
import com.r00t.v_lib.models.TestData;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class MVPTestAct extends MVPTestActivity {
    List<TestData> testDataList;

    @Override
    protected void onClicked() {
        testDataList = new ArrayList<>();

        FirebaseImpl.getInstance(this)
                .getFirestore()
                .collection("users")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        testDataList.addAll(queryDocumentSnapshots.toObjects(TestData.class));

                        writeData();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(
                        MVPTestAct.this,
                        "ERROR",
                        Toast.LENGTH_LONG
                ).show();

                e.printStackTrace();

            }
        });
    }

    private void writeData() {

        for (TestData testData : testDataList)
            System.out.println(testData.getName());

    }
}
