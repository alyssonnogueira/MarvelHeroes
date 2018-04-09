package com.example.alysson.marvelcomics.Views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.example.alysson.marvelcomics.Services.HeroService;
import com.example.alysson.marvelcomics.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SplashScreenActivity extends AppCompatActivity {
    private static String TAG = "LOG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView marvelLogo = (ImageView) findViewById(R.id.imageView);
        Picasso.get().load("http://www.blogdoselback.com.br/wp-content/uploads/2017/06/Marvel-Comics-800x420-710x373.jpg").into(marvelLogo);
        //http://www.blogdoselback.com.br/wp-content/uploads/2017/06/Marvel-Comics-800x420-710x373.jpg

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

        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            // notify user you are online
            Log.d("net", "net");
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
        } else {
            // notify user you are not online
            Log.d("net", "net else");
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this); //Read Update
            alertDialog.setMessage("Você está sem conexão de Internet, por favor tente novamente mais tarde.");
            alertDialog.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SplashScreenActivity.this.finish();
                }
            });
            alertDialog.show();
        }

        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {

                while(heroes.size() == 0);

                    Intent intent = new Intent(SplashScreenActivity.this,
                            MainActivity.class);
                    intent.putExtra("limitID", heroService.getLimitID());
                    intent.putExtra("arrayList", heroes);
                    startActivity(intent);
                    finish();
            }
        }, 100);
    }

}
