package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity implements EndpointsAsyncTask.AsyncResponse{


    EndpointsAsyncTask.AsyncResponse thisInterface = this;
    Context thisContext = this;
    MainActivityFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         frag = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
    }

    public void fetch_async_joke() {
        new EndpointsAsyncTask(thisInterface).execute(new Pair<Context, String>(thisContext, "Manfred"));
    }

    @Override
    public void processFinish(String output){
        frag.processFinish(output);
    }
}
