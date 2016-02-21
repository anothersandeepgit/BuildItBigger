package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.end2end.jokedisplaylibrary.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class MainActivity extends ActionBarActivity implements EndpointsAsyncTask.AsyncResponse{

    InterstitialAd mInterstitialAd;
    EndpointsAsyncTask.AsyncResponse thisInterface = this;
    Context thisContext = this;
    boolean adShown = false;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        MainActivityFragment frag = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (frag.isAdViewPresent()) {
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(getResources().getString(R.string.test_interstitial_ad_unit_id));

            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    requestNewInterstitial();
                    //beginPlayingGame();
                    adShown = true;
                    progressBar.setVisibility(View.VISIBLE);
                    new EndpointsAsyncTask(thisInterface).execute(new Pair<Context, String>(thisContext, "Manfred"));
                }
            });

            requestNewInterstitial();

        }
    }
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    public void processFinish(String output){

            adShown = false;
        progressBar.setVisibility(View.GONE);
            Intent jokeIntent = new Intent(this, JokeDisplayActivity.class);
            String finalOutput = "FromMainActivityRemote: " + output;
            jokeIntent.putExtra(JokeDisplayActivity.JOKE_KEY, finalOutput);
            startActivity(jokeIntent);

    }
    public void tellJoke(View view){

        if (mInterstitialAd == null) {
            progressBar.setVisibility(View.VISIBLE);
            new EndpointsAsyncTask(this).execute(new Pair<Context, String>(this, "Manfred"));
        } else if (mInterstitialAd != null && mInterstitialAd.isLoaded() && !adShown) {
            mInterstitialAd.show();
        }
        //Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
