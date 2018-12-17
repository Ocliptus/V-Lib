package com.r00t.v_lib.activities.addBook.isbn;

import android.app.Dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.SystemParameterOrBuilder;
import com.google.firebase.firestore.DocumentSnapshot;
import com.r00t.v_lib.R;
import com.r00t.v_lib.data.Book;
import com.r00t.v_lib.data.FirebaseImpl;
import com.r00t.v_lib.models.UserDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import androidx.annotation.NonNull;
import butterknife.ButterKnife;


public class isbnAct extends isbnActivity {
    protected Dialog myDialog;
    private static final String TAG = "+=+";
    private boolean isExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isbn);
        ButterKnife.bind(this);
        myDialog = new Dialog(this);
    }

    @Override
    public void isbnAddClicked() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if (((EditText) findViewById(R.id.isbnET))
                .getText().toString().matches("[\\d]{10}|[\\d]{13}")) {
            final String isbn = ((EditText) findViewById(R.id.isbnET)).getText().toString();
            System.out.println("+=+ " + isbn);
            FirebaseImpl.getInstance(getApplicationContext())
                    .getFirestore()
                    .collection("bookDetails")
                    .document(isbn)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        Book book = task.getResult().toObject(Book.class);
                        if(book!=null){
                            Toast.makeText(getApplicationContext(), "This book comes from firebase", Toast.LENGTH_LONG).show();
                            TextView imgClose;
                            ImageView cover_medium_view;
                            Button btnAdd;
                            Button btnPass;
                            TextView titleTV;
                            TextView isbnTV;
                            TextView bookUrlTV;
                            TextView numberOfPagesTV;
                            TextView publishDateTV;
                            TextView authorsTV;
                            TextView publishPlacesTV;
                            myDialog.setContentView(R.layout.add_book_popup);
                            cover_medium_view = (ImageView) myDialog.findViewById(R.id.cover_medium_view);
                            titleTV = (TextView) myDialog.findViewById(R.id.titleTV);
                            isbnTV = (TextView) myDialog.findViewById(R.id.isbnTV);
                            bookUrlTV = (TextView) myDialog.findViewById(R.id.bookUrlTV);
                            numberOfPagesTV = (TextView) myDialog.findViewById(R.id.numberOfPagesTV);
                            publishDateTV = (TextView) myDialog.findViewById(R.id.publishDateTV);
                            authorsTV = (TextView) myDialog.findViewById(R.id.authorsTV);
                            publishPlacesTV = (TextView) myDialog.findViewById(R.id.publishPlacesTV);
                            imgClose = (TextView) myDialog.findViewById(R.id.popup_close);
                            btnAdd = (Button) myDialog.findViewById(R.id.add_library);
                            btnPass = (Button) myDialog.findViewById(R.id.wrong_book);
                            titleTV.setText(book.getTitle());
                            isbnTV.setText(book.getIsbn());
                            cover_medium_view.setImageBitmap(getBitmapFromURL(book.getCover_medium()));
                            bookUrlTV.setText(book.getUrlBook());
                            numberOfPagesTV.setText(book.getNumber_of_pages());
                            authorsTV.setText(book.getAuthors());
                            publishPlacesTV.setText(book.getPublish_places());
                            publishDateTV.setText(book.getPublishDate());

                            myDialog.dismiss();
                            myDialog.show();
                            isAddLibraryClicked(isbn, book, btnAdd);
                            isClosePopUpClicked(imgClose);
                            isWrongBookClicked(btnPass);
                            System.out.println("Doesnot goes to else");
                        }else{
                            try {
                                //get URL content
                                URL url;
                                url = new URL(isbnSearch.urlCombine(isbn));
                                URLConnection con = url.openConnection();
                                // open the stream and put it into BufferedReader
                                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                                String inputLine = "";
                                inputLine = br.readLine();
                                JSONObject obj = new JSONObject(inputLine);
                                String objName = "ISBN:" + isbn;
                                String weight = obj.getJSONObject(objName).optString("weight");
                                String urlBook = obj.getJSONObject(objName).optString("url");
                                String number_of_pages = obj.optJSONObject(objName).optString("number_of_pages");
                                String publishDate = obj.optJSONObject(objName).optString("publish_date");
                                String title = obj.optJSONObject(objName).optString("title");
                                try {
                                    JSONObject cover = obj.optJSONObject(objName).optJSONObject("cover");
                                    String cover_small = cover.optString("small");
                                    String cover_medium = cover.optString("medium");
                                    String cover_large = cover.optString("large");
                                    JSONArray authors = obj.optJSONObject(objName).optJSONArray("authors");
                                    String author = "";
                                    JSONArray publishPlaces = obj.optJSONObject(objName).optJSONArray("publish_places");
                                    String publish_places = "";
                                    int i;
                                    for (i = 0; i < authors.length(); i++) {
                                        String tempInput = authors.get(i).toString();
                                        JSONObject tempObj = new JSONObject(tempInput);
                                        author = author + " / " + tempObj.optString("name");
                                        String tempInput2 = publishPlaces.get(i).toString();
                                        JSONObject tempObj2 = new JSONObject(tempInput2);
                                        publish_places = publish_places + " / " + tempObj2.optString("name");
                                    }
                                    Book book2 = new Book(isbn, weight, urlBook, number_of_pages, publishDate,
                                            title, cover_small, cover_medium, cover_large, author, publish_places);
                                    Toast.makeText(getApplicationContext(), "This book comes from OpenLibrary API", Toast.LENGTH_LONG).show();
                                    TextView imgClose;
                                    ImageView cover_medium_view;
                                    Button btnAdd;
                                    Button btnPass;
                                    TextView titleTV;
                                    TextView isbnTV;
                                    TextView bookUrlTV;
                                    TextView numberOfPagesTV;
                                    TextView publishDateTV;
                                    TextView authorsTV;
                                    TextView publishPlacesTV;
                                    myDialog.setContentView(R.layout.add_book_popup);
                                    cover_medium_view = (ImageView) myDialog.findViewById(R.id.cover_medium_view);
                                    titleTV = (TextView) myDialog.findViewById(R.id.titleTV);
                                    isbnTV = (TextView) myDialog.findViewById(R.id.isbnTV);
                                    bookUrlTV = (TextView) myDialog.findViewById(R.id.bookUrlTV);
                                    numberOfPagesTV = (TextView) myDialog.findViewById(R.id.numberOfPagesTV);
                                    publishDateTV = (TextView) myDialog.findViewById(R.id.publishDateTV);
                                    authorsTV = (TextView) myDialog.findViewById(R.id.authorsTV);
                                    publishPlacesTV = (TextView) myDialog.findViewById(R.id.publishPlacesTV);
                                    imgClose = (TextView) myDialog.findViewById(R.id.popup_close);
                                    btnAdd = (Button) myDialog.findViewById(R.id.add_library);
                                    btnPass = (Button) myDialog.findViewById(R.id.wrong_book);
                                    titleTV.setText(title);
                                    isbnTV.setText(isbn);
                                    bookUrlTV.setText(urlBook);
                                    numberOfPagesTV.setText(number_of_pages);
                                    authorsTV.setText(author);
                                    publishPlacesTV.setText(publish_places);
                                    publishDateTV.setText(publishDate);
                                    titleTV.setText(title);
                                    cover_medium_view.setImageBitmap(getBitmapFromURL(cover_medium));
                                    myDialog.dismiss();
                                    myDialog.show();
                                    isAddLibraryClicked(isbn, book2, btnAdd);
                                    isClosePopUpClicked(imgClose);
                                    isWrongBookClicked(btnPass);
                                } catch (NullPointerException e2) {
                                    String cover_small = "";
                                    String cover_medium = "";
                                    String cover_large = "";
                                }
                            } catch (MalformedURLException e1) {
                                e1.printStackTrace();
                                Toast.makeText(getApplicationContext(), "1 rd exception", Toast.LENGTH_LONG).show();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                                Toast.makeText(getApplicationContext(), "2 rd exception", Toast.LENGTH_LONG).show();

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                                Toast.makeText(getApplicationContext(), "This isbn is not exist in our database !", Toast.LENGTH_LONG).show();
                            } catch (NullPointerException e1) {
                                Toast.makeText(getApplicationContext(), "This isbn is not exist in our database !", Toast.LENGTH_LONG).show();
                            }

                        }

                    }
                }
            });
        } else {
            Toast.makeText(this, "Entry does not fit the standart rules", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void isClosePopUpClicked(TextView imgClose) {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                EditText isbnET = (EditText) findViewById(R.id.isbnET);
                isbnET.setText("");
                Toast.makeText(getApplicationContext(), "Let' s try another ISBN number !", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void isAddLibraryClicked(String isbn, Book book, Button btnAdd) {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseImpl
                        .getInstance(getApplicationContext())
                        .getFirestore()
                        .collection("bookDetails")
                        .document(isbn).set(book);
                String uid = FirebaseImpl
                        .getInstance(getApplicationContext())
                        .getFirebaseUser()
                        .getUid();
                FirebaseImpl
                        .getInstance(getApplicationContext())
                        .getFirestore()
                        .collection("users")
                        .document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task2) {
                        UserDetails user = task2.getResult().toObject(UserDetails.class);
                        String books = user.getBooks();
                        if(!books.contains(isbn)){
                            int bookCount = user.getBookCount();
                            bookCount++;
                            user.setBookCount(bookCount);
                            books = books + ","+isbn;
                            user.setBooks(books);
                            FirebaseImpl
                                    .getInstance(getApplicationContext())
                                    .getFirestore()
                                    .collection("users")
                                    .document(uid).set(user);
                            myDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "New book ha ? Hmmm yummy !", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.
                                    makeText(getApplicationContext(), "You already have this book in your library ! YOU CAN'T FOOL ME !", Toast.LENGTH_LONG)
                                    .show();
                            myDialog.dismiss();;
                        }
                    }
                });

            }
        });

    }

    @Override
    public void isWrongBookClicked(Button btnPass) {

        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
                EditText isbnET = (EditText) findViewById(R.id.isbnET);
                isbnET.setText("");
                Toast.makeText(getApplicationContext(), "Let' s try another ISBN number !", Toast.LENGTH_LONG).show();
            }
        });

    }


    public static Bitmap getBitmapFromURL(String src) {
        try {

            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
