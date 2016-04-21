package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.end2end.jokedisplaylibrary.JokeDisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment   {


    boolean adShown = false;
    Button jokeButton;
    InterstitialAd mInterstitialAd;
    ProgressBar progressBar;
    boolean adViewPresent = false;
    boolean isAdViewPresent() { return adViewPresent; }

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        jokeButton = (Button) root.findViewById(R.id.jokeButton);
        jokeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tellJoke(v);
            }
        });

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        adViewPresent = true;
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("05846a9b")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                //.addTestDevice("ECF62A8153C063AD8920C4E03C7A90EF")
                .build();
        mAdView.loadAd(adRequest);

        //mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.test_interstitial_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                //beginPlayingGame();
                adShown = true;
                progressBar.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).fetch_async_joke();
            }
        });

        requestNewInterstitial();

        return root;
    }

    public void tellJoke(View view){

        if (mInterstitialAd == null) {
            progressBar.setVisibility(View.VISIBLE);
            ((MainActivity) getActivity()).fetch_async_joke();
        } else if (mInterstitialAd != null && mInterstitialAd.isLoaded() && !adShown) {
            mInterstitialAd.show();
        }
        //Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("SEE_YOUR_LOGCAT_TO_GET_YOUR_DEVICE_ID")
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    public void processFinish(String output){

        //adShown = false;
        progressBar.setVisibility(View.GONE);
        //Intent jokeIntent = new Intent(this, JokeDisplayActivity.class);
        Intent jokeIntent = new Intent(getActivity(), JokeDisplayActivity.class);
        String finalOutput = "FromMainActivityRemote: " + output;
        jokeIntent.putExtra(JokeDisplayActivity.JOKE_KEY, finalOutput);
        startActivity(jokeIntent);

    }
}
