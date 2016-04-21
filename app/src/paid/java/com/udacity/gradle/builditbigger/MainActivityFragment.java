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


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ProgressBar progressBar;
    Button jokeButton;
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

        return root;
    }

    public void tellJoke(View view){

        progressBar.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).fetch_async_joke();
        //Toast.makeText(this, joke, Toast.LENGTH_SHORT).show();
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
