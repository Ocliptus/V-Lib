package com.r00t.v_lib.activities.addBook.isbn;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.r00t.v_lib.R;
import com.r00t.v_lib.data.Book;

import org.json.JSONArray;
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
                String objName = "ISBN:"+ isbn;
                String isbnNumber = isbn;
                String  weight = obj.getJSONObject(objName).getString("weight");
                String  urlBook = obj.getJSONObject(objName).getString("url");
                String  number_of_pages = obj.getJSONObject(objName).getString("number_of_pages");
                String  publishDate = obj.getJSONObject(objName).getString("publish_date");
                String  title = obj.getJSONObject(objName).getString("title");
                JSONObject cover = obj.getJSONObject(objName).getJSONObject("cover");
                String  cover_small = cover.getString("small");
                String  cover_medium = cover.getString("medium");
                String  cover_large = cover.getString("large");
                JSONArray authors = obj.getJSONObject(objName).getJSONArray("authors");
                String author="";
                JSONArray publishPlaces = obj.getJSONObject(objName).getJSONArray("publish_places");
                String publish_places="";
                int i;
                for (i=0; i<authors.length(); i++){
                    String tempInput = authors.get(i).toString();
                    JSONObject tempObj = new JSONObject(tempInput);
                    author = author +" " + tempObj.getString("name");
                    String tempInput2 = publishPlaces.get(i).toString();
                    JSONObject tempObj2 = new JSONObject(tempInput2);
                    publish_places =publish_places+" "+ tempObj2.getString("name");
                }







                TextView testTV = (TextView) findViewById(R.id.testTV);
                testTV.setText(title+" "+objName+" "+isbnNumber+" "+weight+" "+urlBook+" "+number_of_pages+" "+publishDate+" "+cover_small+" "+
                        cover_medium+" "+cover_large+" "+author+" "+publish_places );
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
