package com.r00t.v_lib.activities.addBook.isbn;



import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class isbnSearch {
    private static final String API_BASE_URL = "https://openlibrary.org/api/books?bibkeys=ISBN:";
    private static final String API_BASE_URL_END = "&jscmd=data&format=json";
    private static String API_URL_ISBN= "";


    protected static String urlCombine(String isbn) {
       return API_URL_ISBN= API_BASE_URL + isbn + API_BASE_URL_END;
    }

    public static String getText(String url) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        URL website = null;
        try {
            website = new URL(url);

        URLConnection connection = website.openConnection();
        BufferedReader in = new BufferedReader( new InputStreamReader(connection.getInputStream(),"UTF8"));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();
            return response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;


    }

}

