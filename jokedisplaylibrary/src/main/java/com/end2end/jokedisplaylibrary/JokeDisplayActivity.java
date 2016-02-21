package com.end2end.jokedisplaylibrary;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by lendevsanadmin on 1/30/2016.
 */
public class JokeDisplayActivity extends ActionBarActivity {
    public static String JOKE_KEY = "Joke key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
    }

}
