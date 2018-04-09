package com.example.alysson.marvelcomics.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.alysson.marvelcomics.Models.Hero;
import com.example.alysson.marvelcomics.R;
import com.example.alysson.marvelcomics.ViewModels.heroFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements heroFragment.OnListFragmentInteractionListener{

    List<Hero> heroes;
    int limitID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.heroes = getIntent().getParcelableArrayListExtra("arrayList");
        this.limitID = getIntent().getIntExtra("limitID", 0);
        Log.d("MainActivity SIZE", String.valueOf(heroes.size()));
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(Hero item) {
        Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this.getApplicationContext(), HeroPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("HERO", item);
        this.getApplicationContext().startActivity(intent);
    }

    public List<Hero> getHeroes(){
        return this.heroes;
    }

    public int getLimitID(){
        return this.limitID;
    }
}
