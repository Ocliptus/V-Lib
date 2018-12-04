package com.r00t.v_lib.activities.addBook.isbn;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.r00t.v_lib.R;
import com.r00t.v_lib.data.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.ButterKnife;

import com.r00t.v_lib.data.Book;

public class isbnAct extends isbnActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isbn);
        ButterKnife.bind(this);
    }

    @Override
    public void isbnAddClicked() {
        if (((EditText) findViewById(R.id.isbnET)).getText().toString().matches("[\\d]{10}|[\\d]{13}")) {
            try {
                String isbn = ((EditText) findViewById(R.id.isbnET)).getText().toString();
                JSONArray jsonArray = new JSONArray(isbnSearch.getText(isbnSearch.urlCombine(isbn)));
                String bib_key = "", preview = "", thumbnail_url = "", preview_url = "", info_url = "";
                Book book = new Book(bib_key, preview, thumbnail_url, preview_url, info_url);
                for (int i = 0; i < jsonArray.length(); i++) {
                    //get the JSON Object
                    JSONObject obj = jsonArray.getJSONObject(i);
                    book.setBib_key(obj.getString("bib_key"));
                    book.setPreview(obj.getString("preview"));
                    book.setThumbnail_url(obj.getString("thumbnail_url"));
                    book.setPreview_url(obj.getString("preview_url"));
                    book.setInfo_url(obj.getString("info_url"));
                }
                Toast.makeText(this, book.toString(), Toast.LENGTH_LONG).show();
            } catch (Exception e) { }
        } else {
            Toast.makeText(this, "Entry does not fit the standart rules", Toast.LENGTH_LONG).show();
        }
    }

}
