package com.example.mimitantono.myapplication;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private EditText imageUrl;

    private GestureDetectorCompat gestureDetectorCompat;

    private boolean imageLoaded = false;

    private ImageView imageView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        TextView hint = (TextView) findViewById(R.id.hint);
        gestureDetectorCompat =
                new GestureDetectorCompat(this, new GestureListener(imageView, hint));
    }

    @Override
    protected void onResume() {
        if (!imageLoaded) {
            loadImage("http://i61.tinypic.com/w0omeb.jpg");
        }
        super.onResume();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void loadImage(String url) {
        if (isNetworkAvailable()) {
            new DownloadImageTask(imageView).execute(url);
            imageLoaded = true;
        } else {
            Log.e("POPUP", "Internet unavailable");
            DialogFragment dialog = new YesDialog();
            Bundle args = new Bundle();
            args.putString("title", "Error");
            args.putString("message",
                    "Please make sure that you have a working Internet Connection");
            dialog.setArguments(args);
            dialog.show(getFragmentManager(), "tag");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


}
