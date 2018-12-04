package com.r00t.v_lib.activities.addBook.isbn;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONObject;

public class isbnSearch {
    private static final String API_BASE_URL = "https://openlibrary.org/api/books?bibkeys=ISBN:";
    private static final String API_BASE_URL_END = "&format=json";
    private static String API_URL_ISBN= "";


    protected static String urlCombine(String isbn) {
       return API_URL_ISBN= API_BASE_URL + isbn + API_BASE_URL_END;
    }

    public static String getText(String url) throws Exception {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream(),"UTF8"));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null)
            response.append(inputLine);

        in.close();

        return response.toString();
    }

}

