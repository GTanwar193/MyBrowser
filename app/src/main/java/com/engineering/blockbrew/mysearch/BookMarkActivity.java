package com.engineering.blockbrew.mysearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class BookMarkActivity extends AppCompatActivity  {

    ImageButton ibn_back;
    RecyclerView recbookmarklist;
    LinearLayoutManager mLayoutManager;
    public static RecyclerView.Adapter mBookmarkAdapter;
    ArrayList<String> arrayListBookmark;
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        setGui();

        ibn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        arrayListBookmark = userInfo.getBookMarklist();
        Log.e("arrayListBookmark", String.valueOf(arrayListBookmark));

        mLayoutManager = new LinearLayoutManager(BookMarkActivity.this, LinearLayoutManager.VERTICAL, false);
        recbookmarklist.setLayoutManager(mLayoutManager);
        recbookmarklist.setNestedScrollingEnabled(false);
        mBookmarkAdapter = new BookMarkAdapter(BookMarkActivity.this, arrayListBookmark);
        recbookmarklist.setAdapter(mBookmarkAdapter);


    }

    public void setGui() {
        userInfo = new UserInfo(this);


        userInfo = new UserInfo(this);
        ibn_back = (ImageButton) findViewById(R.id.ibn_back);
        recbookmarklist = (RecyclerView) findViewById(R.id.rec_book_mark_list);

    }


    @Override
    public void onBackPressed() {
        //   super.onBackPressed();


        Intent intent = new Intent(BookMarkActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


}
