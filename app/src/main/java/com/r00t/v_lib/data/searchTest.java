package com.r00t.v_lib.data;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
public class searchTest {
    private static final String API_BASE_URL = "https://openlibrary.org/api/books?bibkeys=ISBN:";
    private static final String API_BASE_URL_END = "&format=json";
    private static String API_URL_ISBN= "";
    private AsyncHttpClient client;


    public searchTest() {
        this.client = new AsyncHttpClient();
    }
    private void getApiUrlIsbn(String isbn) {
     API_URL_ISBN= API_BASE_URL + isbn + API_BASE_URL_END;

    }
    private String getApiUrl(String part) {

        return API_BASE_URL + part + API_BASE_URL_END;
    }
    public void getBooks(final String query, JsonHttpResponseHandler handler) {
        try {

            String url = API_URL_ISBN;
            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
            String test = client.getHttpContext().toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    public void getExtraBookDetails(String openLibraryId, JsonHttpResponseHandler handler) {
        String url = getApiUrl("books/");
        client.get(url + openLibraryId + ".json", handler);
    }
    public Book BookDetails(){
        Gson gson = new Gson(String jsonInString);

        Book bookDetail= gson.fromJson(jsonInString, Book.class);

        return bookDetail;

    }
    public static void main(String[] args){
        searchTest st = new searchTest();
        JsonHttpResponseHandler handler = null;
        st.getBooks("{\"ISBN:0201558025\": {\"bib_key\": \"ISBN:0201558025\", \"preview\": \"restricted\", \"thumbnail_url\": \"https://covers.openlibrary.org/b/id/135182-S.jpg\", \"preview_url\": \"https://archive.org/details/concretemathemat00grah_444\", \"info_url\": \"https://openlibrary.org/books/OL1429049M/Concrete_mathematics\"}}" , handler);

    }

}
