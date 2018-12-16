package com.r00t.v_lib.activities.addBook.OCR;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.Image;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Element;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.Text;
import com.google.firebase.firestore.DocumentSnapshot;
import com.r00t.v_lib.activities.addBook.OCR.ui.camera.*;

import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.snackbar.Snackbar;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.OCR.ui.camera.CameraSourcePreview;

import com.r00t.v_lib.activities.addBook.OCR.ui.camera.CameraSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import butterknife.OnClick;

import com.r00t.v_lib.data.Book;
import com.r00t.v_lib.data.FirebaseImpl;

import static com.r00t.v_lib.activities.addBook.isbn.isbnAct.getBitmapFromURL;

/**
 * Activity for the Ocr Detecting app.  This app detects text and displays the value with the
 * rear facing camera. During detection overlay graphics are drawn to indicate the position,
 * size, and contents of each TextBlock.
 */
public final class OcrCaptureActivity extends OcrCaptureAbs {
    boolean isExist;


    /**
     * Initializes the UI and creates the detector pipeline.
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_ocr_capture);

        btnPrev = (Button) findViewById(R.id.btnPrev);
        btnSend = (Button) findViewById(R.id.btnSend);
        etDetails = (EditText) findViewById(R.id.etDetails);
        preview = (CameraSourcePreview) findViewById(R.id.preview);
        graphicOverlay = (GraphicOverlay<OcrGraphic>) findViewById(R.id.graphicOverlay);
        myDialog = new OcrPopUp(graphicOverlay.getContext());
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "--------sendButonClicked: ");

                switchStructure(MethodCase.btnSendClicked);
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchStructure(MethodCase.btnPrevClicked);
            }
        });

        // Set good defaults for capturing text.
        boolean autoFocus = true;
        boolean useFlash = false;

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(autoFocus, useFlash);
        } else {
            requestCameraPermission();
        }

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        Snackbar.make(graphicOverlay, "Tap to Speak. Pinch/Stretch to zoom",
                Snackbar.LENGTH_LONG)
                .show();
    }

    @Override
    protected void openPopUp() {
        myDialog.show();

    }

    @Override
    protected void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Snackbar.make(graphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean b = scaleGestureDetector.onTouchEvent(e);

        boolean c = gestureDetector.onTouchEvent(e);

        return b || c || super.onTouchEvent(e);
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the ocr detector to detect small text samples
     * at long distances.
     * <p>
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @Override
    protected void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = this;

        TextRecognizer textRecognizer = new TextRecognizer.Builder(context).build();

        textRecognizer.setProcessor(new OcrDetectorProcessor(graphicOverlay));


        if (!textRecognizer.isOperational()) {

            Log.w(TAG, "Detector dependencies are not yet available.");

            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();

            }
        }

        cameraSource =
                new CameraSource.Builder(context, textRecognizer)
                        .setFacing(CameraSource.CAMERA_FACING_BACK)
                        .setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)
                        .setFlashMode(Camera.Parameters.FLASH_MODE_AUTO)
                        .setRequestedPreviewSize(1280, 1024)
                        .setRequestedFps(15.0f)
                        .build();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            boolean autoFocus = getIntent().getBooleanExtra(AutoFocus, true);
            boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
            createCameraSource(autoFocus, useFlash);
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    @Override
    protected void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (cameraSource != null) {
            try {
                preview.start(cameraSource, graphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                cameraSource.release();
                cameraSource = null;
            }
        }
    }

    protected boolean onTap(float rawX, float rawY) {
        //OcrGraphic graphic = graphicOverlay.getGraphicAtLocation(rawX, rawY);
        OcrGraphic graphic = graphicOverlay.getGraphicAtLocation(rawX, rawY);
        Element text = null;

        if (graphic != null) {
            text = graphic.getWord();

            List l = text.getComponents();
            if (text != null && text.getValue() != null) {
                String s = text.getValue();
                if (etDetails.getText() != null)
                    etDetails.setText(etDetails.getText().toString() + " " + s);
                else
                    etDetails.setText(s);

                /*
                for (Object t:l
                     ) {
                    Snackbar.make(graphicOverlay, ((Line)t).getValue(),
                            Snackbar.LENGTH_LONG)
                            .show();


                }*/
            } else {
                Log.d(TAG, "text data is null");
            }
        } else {
            Log.d(TAG, "no text detected");
        }
        return text != null;
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (preview != null) {
            preview.stop();
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (preview != null) {
            preview.release();
        }
    }

    public boolean checkBook(String isbn) {
        Log.i(TAG, "checkBook in");
        Log.i(TAG, "isbn:"+isbn);
        FirebaseImpl.getInstance(this)
                .getFirestore()
                .collection("bookDetails")
                .document(isbn).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.i(TAG, "onComplete in");

                bookToAdd = task.getResult().toObject(Book.class);


                Log.i(TAG, "isBookNull: " + ((Boolean)(bookToAdd == null)).toString());
                if(task.isSuccessful()){
                    Log.i(TAG, "Task is succesfull");
                if (bookToAdd != null) {
                    Log.i(TAG, "Book != bull");
                    if (bookToAdd.getIsbn().equals(isbn)) {
                        Log.i(TAG, "bookToAdd.getIsbn().equals(isbn)");
                        myDialog.getTvIsbn().setText(bookToAdd.getIsbn());
                        myDialog.getTvTitle().setText(bookToAdd.getTitle());
                        myDialog.getTvAuthors().setText(bookToAdd.getAuthors());
                        myDialog.getTvPublishDate().setText(bookToAdd.getPublishDate());
                        myDialog.getTvNumberOfPages().setText(bookToAdd.getNumber_of_pages());
                        Log.i(TAG, "onComplete: "+bookToAdd.getCover_medium());
                        ImageView cover_medium2 = findViewById(R.id.cover_medium_view2);
                        cover_medium2.setImageBitmap(getBitmapFromURL(bookToAdd.getCover_medium()));
                        //myDialog.getImgCoverView().setImageBitmap(getBitmapFromURL(bookToAdd.getCover_medium()));
                        myDialog.show();
                        isExist = true;
                    } else {
                        isExist = false;
                        Log.i(TAG, "inner else");

                    }

                } else {
                    isExist = false;
                    Log.i(TAG, "outer else");

                }
                }else
                    Log.i(TAG, "Task is failed");

            }
        });
        return isExist;

    }
    @Override
    protected String correctISBN(String isbn) {
        if(isbn.contains("-")){
            String[] isbnArray = isbn.split("[-]");
            isbn="";
            for(int i = 0; i<isbnArray.length; i++)
            {
                isbn+=isbnArray[i];
            }
        }
        if (isbn.contains(" ")){
            String[] isbnArray = isbn.split("[ ]");
            isbn="";
            for(int i = 0; i<isbnArray.length; i++)
            {
                isbn+=isbnArray[i];
            }
        }
        return isbn;
    }
    @Override
    protected void switchStructure(MethodCase methodCase) {
        switch (currentDetail) {
            case ISBN:
                if (methodCase == MethodCase.btnSendClicked) {
                    String isbn = correctISBN(etDetails.getText().toString());
                    if (checkBook(isbn)) {
                        Log.i(TAG, "checkBook(etDetails.getText().toString())");
                    } else {
                        Log.i(TAG, "else");
                        bookToAdd.setIsbn(isbn);
                        myDialog.getTvIsbn().setText(isbn);
                        btnSend.setText("Done");
                        etDetails.setText("");
                        currentDetail = BookDetailEnum.TITLE;
                    }
                }


                break;
            case TITLE:

                Log.i(TAG, "switchStructure: title in");
                if (methodCase == MethodCase.btnSendClicked) {
                    Log.i(TAG, "switchStructure: Title if start");
                    bookToAdd.setTitle(etDetails.getText().toString());
                    myDialog.getTvTitle().setText(etDetails.getText().toString());
                    currentDetail = BookDetailEnum.AUTOHORS;
                    etDetails.setText("");
                    Log.i(TAG, "switchStructure: Title if end");
                } else if (methodCase == MethodCase.btnPrevClicked) {
                    etDetails.setText(bookToAdd.getIsbn());
                    currentDetail = BookDetailEnum.ISBN;
                    Toast.makeText(graphicOverlay.getContext(), "Back to ISBN", Toast.LENGTH_SHORT).show();
                }
                break;
            case AUTOHORS:
                if (methodCase == MethodCase.btnSendClicked) {
                    //TODO: ASK USER WHETHER TEHER  IS ANITHER AUTHOR OR NOT
                    bookToAdd.setAuthors(etDetails.getText().toString());
                    myDialog.getTvAuthors().setText(etDetails.getText().toString());
                    currentDetail = BookDetailEnum.PUBLISHDATE;
                    etDetails.setText("");
                } else if (methodCase == MethodCase.btnPrevClicked) {
                    etDetails.setText(bookToAdd.getTitle());
                    currentDetail = BookDetailEnum.TITLE;
                    Toast.makeText(graphicOverlay.getContext(), "Back to title", Toast.LENGTH_SHORT).show();
                }
                break;
            case PUBLISHDATE:
                if (methodCase == MethodCase.btnSendClicked) {
                    bookToAdd.setPublishDate(etDetails.getText().toString());
                    myDialog.getTvPublishDate().setText(etDetails.getText().toString());
                    currentDetail = BookDetailEnum.NUMBEROFPAGE;
                    etDetails.setText("");
                } else if (methodCase == MethodCase.btnPrevClicked) {
                    etDetails.setText(bookToAdd.getAuthors());
                    currentDetail = BookDetailEnum.AUTOHORS;
                    Toast.makeText(graphicOverlay.getContext(), "Back to authors", Toast.LENGTH_SHORT).show();
                }

                break;

            case NUMBEROFPAGE:
                Toast.makeText(this, "done", Toast.LENGTH_LONG).show();
                Log.i(TAG, "switchStructure: annen ama artık");
                if (methodCase == MethodCase.btnSendClicked) {
                    bookToAdd.setNumber_of_pages(etDetails.getText().toString());

                    myDialog.getTvNumberOfPages().setText(etDetails.getText().toString());
                    myDialog.show();
                    etDetails.setText("");
                } else if (methodCase == MethodCase.btnPrevClicked) {
                    etDetails.setText(bookToAdd.getPublishDate());
                    currentDetail = BookDetailEnum.PUBLISHDATE;
                    btnSend.setText("next");
                    Toast.makeText(graphicOverlay.getContext(), "Back to Publish Date", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
