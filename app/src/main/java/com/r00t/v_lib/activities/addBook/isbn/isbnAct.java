package com.r00t.v_lib.activities.addBook.isbn;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.r00t.v_lib.R;
import com.r00t.v_lib.data.Book;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import butterknife.ButterKnife;


public class isbnAct extends isbnActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isbn);
        ButterKnife.bind(this);

    }

    @Override
    public void isbnAddClicked() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        if (((EditText) findViewById(R.id.isbnET)).getText().toString().matches("[\\d]{10}|[\\d]{13}")) {
            URL url;
           // get URL content
            String isbn = ((EditText) findViewById(R.id.isbnET)).getText().toString();

            try {
                url = new URL(isbnSearch.urlCombine(isbn));

            URLConnection con = url.openConnection();

                // open the stream and put it into BufferedReader
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String inputLine = "";
                inputLine = br.readLine();

                JSONObject obj = new JSONObject(inputLine);
                //String objName = "ISBN:"+ isbn;
                //String bib_key = obj.getJSONObject(objName).getString("bib_key");
                //String  publishers = obj.getJSONObject(objName).getString("publishers");
                //String  weight = obj.getJSONObject(objName).getString("weight");
                //String  title = obj.getJSONObject(objName).getString("title");
                //String  urlBook = obj.getJSONObject(objName).getString("url");
                //String  number_of_pages = obj.getJSONObject(objName).getString("number_of_pages");
                //String  cover_little = obj.getJSONObject(objName).getString("cover");
                //String  cover_middle = obj.getJSONObject(objName).getString("cover");
                //String  cover_big = obj.getJSONObject(objName).getString("cover");
                //String  publishDate = obj.getJSONObject(objName).getString("publish_date");
                //String  authors = obj.getJSONObject(objName).getString("authors");
                //String  publishPlaces = obj.getJSONObject(objName).getString("publish_places");



                //Book book = new Gson().fromJson(obj,Book.class);

                TextView testTV = (TextView) findViewById(R.id.testTV);
                //testTV.setText(book.toString());
                //TODO: pop up yap objeyi firebase e yolla kaydet




            } catch (MalformedURLException e) {
                e.printStackTrace();
                Toast.makeText(this, "1 rd exception", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "2 rd exception", Toast.LENGTH_LONG).show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "Entry does not fit the standart rules", Toast.LENGTH_LONG).show();
        }
    }

}
