package com.edgar.rss_app2;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity{

    Toolbar myToolbar;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.myToolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        myToolbar.setNavigationIcon(R.drawable.ic_back);
        myToolbar.setTitle(R.string.rss_news);

        setSupportActionBar(myToolbar);


        ReadRss readRss = new ReadRss(this, recyclerView);
        readRss.execute();
    }
}
