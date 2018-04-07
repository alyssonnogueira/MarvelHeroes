package com.example.alysson.marvelcomics;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.alysson.marvelcomics.dummy.DummyContent;

public class MainActivity extends AppCompatActivity implements heroFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        Toast.makeText(this, "Click", Toast.LENGTH_LONG).show();
    }
}
