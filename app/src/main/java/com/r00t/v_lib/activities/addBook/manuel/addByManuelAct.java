package com.r00t.v_lib.activities.addBook.manuel;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.r00t.v_lib.FetchBook;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.addBookAct;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

public class addByManuelAct extends addByManuelActivity {
    private static final String TAG = addByManuelAct.class.getSimpleName();
    @Override
    protected void addBookButtonClicked() {
        queryString = etBookName.getText().toString();

        Log.i(TAG,"Searched"+queryString);
        new FetchBook().execute();


    }
}
