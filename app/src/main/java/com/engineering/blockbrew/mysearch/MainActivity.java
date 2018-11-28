package com.engineering.blockbrew.mysearch;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    EditText edtText;
    ImageButton bnSearch;
    WebView webView;
    String url = "https://www.google.com/search?q=";
    ProgressDialog pDialog;
    boolean doubleBackToExitPressedOnce = false; /* boolean for exiting the application*/
    final int BACK_PRESS_LENGTH = 2000;

    ImageButton bnPrevious;
    ImageButton bnMark;
    ImageButton bnNext;


    DrawerLayout drawer;
    NavigationView navigationView;
    ImageButton menuRight;
    UserInfo userInfo;
    ArrayList<String> arrayListBookMark;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setGui();

        loadBookmarkItems();

        bnSearch.setOnClickListener(this);


        bnPrevious.setOnClickListener(this);


        bnNext.setOnClickListener(this);


        bnMark.setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(this);

        menuRight.setOnClickListener(this);


    }


    public void setGui() {
        userInfo = new UserInfo(this);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view2);

        bnPrevious = (ImageButton) findViewById(R.id.bnPrevious);
        bnMark = (ImageButton) findViewById(R.id.bnMark);
        bnNext = (ImageButton) findViewById(R.id.bnNext);
        menuRight = (ImageButton) findViewById(R.id.menuRight);

        userInfo = new UserInfo(this);
        arrayListBookMark = new ArrayList<>();

        edtText = (EditText) findViewById(R.id.edtText);
        bnSearch = (ImageButton) findViewById(R.id.bnSearch);
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setDomStorageEnabled(true);

        pDialog = new ProgressDialog(this);
        pDialog.setTitle("Loading");
        pDialog.setCancelable(false);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if (id == R.id.nav_bookmark) {

            Intent intent = new Intent(this, BookMarkActivity.class);
            startActivity(intent);
            finish();

            return true;

        }
        return false;
    }


    public void loadBookmarkItems() {
        try {
            String strSearchTexts = getIntent().getStringExtra("strUrl");

            if (!strSearchTexts.equals(null) && !strSearchTexts.isEmpty()) {
                boolean bol = isValidURL(strSearchTexts);

                if (bol == true) {
                    webView.loadUrl(strSearchTexts);
                } else {
                    webView.loadUrl(url + strSearchTexts);
                }
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bnMark:

                String strUrl = webView.getUrl();
                if (!strUrl.equals(null) && !strUrl.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Add in BookMarks", Toast.LENGTH_SHORT).show();
                    try {
                        arrayListBookMark = userInfo.getBookMarklist();
                    } catch (Exception e) {
                        Log.e("Exception==", String.valueOf(e));
                    }

                    if (arrayListBookMark.size() > 0) {
                        for (int i = 0; i < arrayListBookMark.size(); i++) {
                            if (strUrl.equals(arrayListBookMark.get(i))) {
                                arrayListBookMark.set(i, strUrl);
                                userInfo.setBookMarklist(arrayListBookMark);
                                break;
                            } else {

                                arrayListBookMark.add(strUrl);
                                userInfo.setBookMarklist(arrayListBookMark);
                                break;

                            }


                        }
                    } else {
                        arrayListBookMark.add(strUrl);
                        userInfo.setBookMarklist(arrayListBookMark);

                    }


                }

                break;

            case R.id.bnSearch:

                String strSearchTexts = edtText.getText().toString();
                boolean bol = isValidURL(strSearchTexts);

                if (bol == true) {
                    webView.loadUrl(strSearchTexts);
                } else {
                    webView.loadUrl(url + strSearchTexts);
                }

                break;



            case R.id.bnPrevious:

                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    Toast.makeText(MainActivity.this, "No previous page avilable", Toast.LENGTH_SHORT).show();
                }

                break;


            case R.id.bnNext:

                if (webView.canGoForward()) {
                    webView.goForward();
                } else {
                    Toast.makeText(MainActivity.this, "No Next page avilable", Toast.LENGTH_SHORT).show();
                    // finish();
                }

                break;


            case R.id.menuRight:

                if (drawer.isDrawerOpen(GravityCompat.END)) {
                    drawer.closeDrawer(GravityCompat.END);
                } else {
                    drawer.openDrawer(GravityCompat.END);
                }

                break;


            default:
                break;
        }
    }


    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pDialog.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            /*** Hide ProgressBar while page completely load ***/
            edtText.setText(webView.getUrl());
            pDialog.dismiss();


        }
    }


    public static boolean isValidURL(String urlString) {
        try {
            URL url = new URL(urlString);
            url.toURI();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }


    @Override
    public void onBackPressed() {
        //   super.onBackPressed();


        if (doubleBackToExitPressedOnce) {
            this.finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, BACK_PRESS_LENGTH);
    }


}
