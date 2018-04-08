package com.example.alysson.marvelcomics;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    private static String TAG = "LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        final HeroService heroService = new HeroService();
        final ArrayList<Parcelable> heroes = new ArrayList<Parcelable>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    heroes.addAll(heroService.getHeroes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                while(heroes.size() == 0);

                    Intent intent = new Intent(SplashScreenActivity.this,
                            MainActivity.class);
//                    for(int i = 0; i < heroes.size(); i++) {
//                        intent.putExtra("Hero"+i, heroes.get(i));
//                    }
                    intent.putExtra("limitID", heroService.getLimitID());
                    intent.putExtra("arrayList", heroes);
                    startActivity(intent);
                    finish();
            }
        }, 2000);
    }

}
