package com.example.hirewritertech;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    public final int REQUEST_CODE = 1010;
    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);
        floatingActionButton=findViewById(R.id.floating);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Internet Permission Grant Was Successful", Toast.LENGTH_LONG).show();
            callInternetCheckWithWebView();

        } else {
            requestPermissionAgain();
        }

    }

    private void callInternetCheckWithWebView() {

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null) {
            new MaterialAlertDialogBuilder(this)
                    .setIcon(R.drawable.ic_baseline_no_internet)
                    .setTitle("No internet")
                    .setMessage("Detected No Internet Connection Please Turn On The Internet," +
                            "If This Is Not the Case Then Please Purchase Internet Bundles To Access Awesome Features " +
                            "From HireWriterTech.")
                    .setCancelable(false)
                    .setPositiveButton("Open Internet Settings", (dialogInterface, i) -> {
                        startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                        dialogInterface.dismiss();
                    }).setNegativeButton("Buy Data Bundles", (dialogInterface, i) -> {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:*544#")));
                        dialogInterface.dismiss();
                    }).create()
                    .show();
        } else {
            alreadyCodedWebViewForHireWriterTech();

        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void alreadyCodedWebViewForHireWriterTech() {
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://support.hirewriter.tech");
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadsImagesAutomatically(true);

    }

    private void requestPermissionAgain() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            new AlertDialog.Builder(this)
                    .setTitle("Internet Permission")
                    .setMessage("HireWriterTech App Wont Function Offline Until the Internet Permission Is Granted Since," +
                            "it Works While Online Only!")
                    .setIcon(R.drawable.ic_baseline_perm_device_information_24)
                    .setCancelable(false)
                    .setPositiveButton("OK,Grant Permission", (dialogInterface, i) -> {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
                        dialogInterface.dismiss();

                    })
                    .setNegativeButton("No, Don't Grant Permission", (dialogInterface, i) -> new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Did Not Grant Permission")
                            .setMessage("It Seems You Did Not Grant Permission,This Means You Wont Be Able To Use " +
                                    "HireWriterTech Application and Explore The Most Of Features It Offers." +
                                    "You Will Only Use This App Again If You clear The Application Data in the Application Info,to Restart It " +
                                    "freshly Again, thank You")
                            .setIcon(R.drawable.ic_baseline_info_24)
                            .setCancelable(false)
                            .setPositiveButton("take Me there and Clear Info", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS));
                                }
                            })
                            .setNegativeButton("Never, I dont Want to Clear!", (dialogInterface1, i1) -> dialogInterface1.dismiss()).create()
                            .show())
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(this, "Internet Permission Grant Was Successful", Toast.LENGTH_LONG).show();
                callInternetCheckWithWebView();

            }
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        callInternetCheckWithWebView();
    }



    public void functionClicked(View view) {
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.abc_popup_enter);
        view.startAnimation(animation);

        Intent intent=new Intent();
        intent.setType("text/plain");
        intent.setAction(Intent.ACTION_SEND);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Intent.EXTRA_TEXT,"Download HireWriterTech App From DevOps Society");
        startActivity(intent);
    }
}