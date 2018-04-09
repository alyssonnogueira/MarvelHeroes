package com.example.alysson.marvelcomics.Views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alysson.marvelcomics.Models.Hero;
import com.example.alysson.marvelcomics.R;
import com.squareup.picasso.Picasso;

public class HeroPageActivity extends AppCompatActivity {

    private Hero hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.hero = getIntent().getParcelableExtra("HERO");

        toolbar.setTitle(hero.getName());

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(hero.getName());

        Log.d("HEROPAGEACTIVITY", "Receive hero "+ hero.getName());

        loadBackdrop();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(hero.getOnlineUrl()));
                startActivity(intent);
            }
        });

        TextView textView = (TextView) findViewById(R.id.description);
        if (hero.getDescription().compareTo("") == 0) {
            textView.setText("This Character doesn't have a Description");
        } else {
            textView.setText(hero.getDescription());
        }

        TextView comicsView = (TextView) findViewById(R.id.comicsAvailable);
        comicsView.setText("Number of Comics: " + hero.getComicsAvailable());

        TextView seriesView = (TextView) findViewById(R.id.seriesAvailable);
        seriesView.setText("Number of Series: " + hero.getSeriesAvailable());

        TextView storiesView = (TextView) findViewById(R.id.storiesAvailable);
        storiesView.setText("Number of Stories: " + hero.getStoriesAvailable());

        TextView eventsView = (TextView) findViewById(R.id.eventsAvailable);
        eventsView.setText("Number of Events: " + hero.getEventsAvailable());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.d("Button", ""+item.getItemId());
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadBackdrop() {
        //Carrega Imagem Estatica do App
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Picasso.get().load(hero.getThumbnail()).into(imageView);

    }
}
