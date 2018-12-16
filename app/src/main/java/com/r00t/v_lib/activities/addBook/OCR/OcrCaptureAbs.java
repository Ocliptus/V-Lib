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
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.text.Element;
import com.google.android.gms.vision.text.Line;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.r00t.v_lib.R;
import com.r00t.v_lib.activities.addBook.OCR.ui.camera.CameraSource;
import com.r00t.v_lib.activities.addBook.OCR.ui.camera.CameraSourcePreview;
import com.r00t.v_lib.activities.addBook.OCR.ui.camera.GraphicOverlay;
import com.r00t.v_lib.data.Book;
import com.r00t.v_lib.data.FirebaseImpl;

import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.core.app.ActivityCompat;
import butterknife.OnClick;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class OcrCaptureAbs extends AppCompatActivity {

    protected enum BookDetailEnum {
        ISBN, TITLE, AUTOHORS, NUMBEROFPAGE,
        PUBLISHDATE

    }

    protected enum MethodCase {
        OnTap,
        btnSendClicked,
        btnPrevClicked

    }

    protected static final String TAG = "OcrCaptureActivity";

    // Intent request code to handle updating play services if needed.
    protected static final int RC_HANDLE_GMS = 9001;

    // Permission request codes need to be < 256
    protected static final int RC_HANDLE_CAMERA_PERM = 2;

    // Constants used to pass extra data in the intent
    public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
    public static final String TextBlockObject = "String";

    protected CameraSource cameraSource;
    public CameraSourcePreview preview;
    protected GraphicOverlay<OcrGraphic> graphicOverlay;
    //GraphicOverlay<OcrGraphic> graphicOverlay;

    // Helper objects for detecting taps and pinches.
    protected ScaleGestureDetector scaleGestureDetector;
    protected GestureDetector gestureDetector;

    // A TextToSpeech engine for speaking a String value.
    protected TextToSpeech tts;
    //ÜST CLASSA GİDECEKLER
    protected Button btnPrev;
    protected Button btnSend;

    protected EditText etDetails;
    protected Book bookToAdd = new Book();
    protected BookDetailEnum currentDetail = BookDetailEnum.ISBN;
    protected OcrPopUp myDialog;

    /**
     * Initializes the UI and creates the detector pipeline.
     */
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // Set good defaults for capturing text.


        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.

        //gestureDetector and will be added scaleGestureDetector
    }

    protected abstract void switchStructure(MethodCase methodCase);

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */

    protected abstract void requestCameraPermission();

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        return super.onTouchEvent(e);
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the ocr detector to detect small text samples
     * at long distances.
     * <p>
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    protected abstract void createCameraSource(boolean autoFocus, boolean useFlash);
    //STEP1
    // A text recognizer is created to find text.  An associated multi-processor instance
    // is set to receive the text recognition results, track the text, and maintain
    // graphics for each text block on screen.  The factory is used by the multi-processor to
    // create a separate tracker instance for each text block.
    // Note: The first time that an app using a Vision API is installed on a
    // device, GMS will download a native libraries to the device in order to do detection.
    // Usually this completes before the app is run for the first time.  But if that
    // download has not yet completed, then the above call will not detect any text,
    // barcodes, or faces.

    // isOperational() can be used to check if the required native libraries are currently
    // available.  The detectors will automatically become operational once the library
    // downloads complete on device.
    //STEP 2
    // Check for low storage.  If there is low storage, the native library will not be
    // downloaded, so detection will not become operational.
    //STEP 3
    // Creates and starts the camera.  Note that this uses a higher resolution in comparison
    // to other detection examples to enable the text recognizer to detect small pieces of text.


    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    protected abstract void startCameraSource();
    // check that the device has play services available.


    /**
     * onTap is called to speak the tapped TextBlock, if any, out loud.
     *
     * @param rawX - the raw position of the tap
     * @param rawY - the raw position of the tap.
     * @return true if the tap was on a TextBlock
     */
    protected abstract boolean onTap(float rawX, float rawY);
    //Gets the detected test


    protected class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    protected class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if (cameraSource != null) {
                cameraSource.doZoom(detector.getScaleFactor());
            }
        }
    }

    protected class OcrPopUp extends Dialog {

        private Button btnAdd;
        private Button btnPass;
        private TextView tvTitle;
        private TextView tvIsbn;
        private TextView tvNumberOfPages;
        private TextView tvPublishDate;
        private TextView tvAuthors;
        private ImageView imgCoverView;

        public OcrPopUp(@NonNull Context context) {
            super(context);
            setContentView(R.layout.book_popup_ocr);
            btnAdd = findViewById(R.id.add_library);
            btnPass = findViewById(R.id.wrong_book);

            tvTitle = findViewById(R.id.titleTV);

            tvIsbn = findViewById(R.id.isbnTV);

            tvNumberOfPages = findViewById(R.id.numberOfPagesTV);

            tvPublishDate = findViewById(R.id.publishDateTV);

            tvAuthors = findViewById(R.id.authorsTV);

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseImpl.getInstance(getApplicationContext())
                            .getFirestore()
                            .collection("bookDetails")
                            .document(bookToAdd.getIsbn())
                            .set(bookToAdd);
                    Toast.makeText(graphicOverlay.getContext(), "New book ha ? Hmmm yummy !", Toast.LENGTH_SHORT).show();
                    myDialog.dismiss();

                }
            });
            btnPass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(graphicOverlay.getContext(), "Let's try another ISBN !", Toast.LENGTH_SHORT).show();
                    myDialog.dismiss();
                }
            });

        }

        public Button getBtnAdd() {
            return btnAdd;
        }

        public Button getBtnPass() {
            return btnPass;
        }

        public TextView getTvTitle() {
            return tvTitle;
        }

        public TextView getTvIsbn() {
            return tvIsbn;
        }

        public TextView getTvNumberOfPages() {
            return tvNumberOfPages;
        }

        public TextView getTvPublishDate() {
            return tvPublishDate;
        }

        public TextView getTvAuthors() {
            return tvAuthors;
        }

        public ImageView getImgCoverView() {
            return imgCoverView;
        }

        public void setImgCoverView(ImageView imgCoverView) {
            this.imgCoverView = imgCoverView;
        }
    }

    protected abstract void openPopUp();
    protected abstract String correctISBN(String isbn);
}



