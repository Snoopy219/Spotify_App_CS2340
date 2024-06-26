package com.example.spotifyapp2340.asyncTasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.SpotifyCalls.SpotifyCalls;
import com.example.spotifyapp2340.handleJSON.HANDLE_JSON;
import com.example.spotifyapp2340.storage.FIRESTORE;
import com.example.spotifyapp2340.ui.newWrapped.NewWrappedFragment;
import com.example.spotifyapp2340.ui.wrapped.WrappedFragment;
import com.example.spotifyapp2340.wrappers.Wrapped;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Handles getting Wrapped artists & tracks.
 *
 * @author Froilan Trix Sunga
 * @version 11.0.20
 */
public class NewWrappedAsync extends AsyncTask<Void, Void, Void>  {
    /**
     * The M call 1.
     */
    Call mCall1;
    /**
     * The M call 2.
     */
    Call mCall2;
    /**
     * The constant controller.
     */
    public static NavController controller;
    /**
     * The constant activity.
     */
    public static Activity activity;
    private int numFail = 0;

    /**
     * The constant timePhase.
     */
/* 0 for short term
       1 for medium term
       2 for long term
     */
    public static int timePhase = 0;

    /**
     * Constructor that takes in a controller.
     *
     * @param controller controller used to navigate to next screen
     * @param activity   the activity
     */
    public NewWrappedAsync(NavController controller, Activity activity) {
        if (controller == null) {
            throw new NullPointerException("NavController is null.");
        }
        this.controller = controller;
        this.activity = activity;
    }

    /**
     * Performs getting artists & tracks in the background.
     * @param params not needed. Void.
     *
     * @return null
     */
    @Override
    protected Void doInBackground(Void... params) {
//        wrapped = new Wrapped(new Date());
        final Wrapped[] wrapped = new Wrapped[1];
        final String[] JSONTrack = new String[1];
        final String[] JSONArt = new String[1];
        final int[] numGot = {0};

//        MainActivity.currUser.addWrapped(wrapped);
        Log.d("Test", "This is a test to check if doInBackground is working.");
        //Getting tracks

        final Request req;
        if (timePhase == 0) {
            req = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks?time_range=short_term")
                    .addHeader("Authorization",
                            "Bearer " + MainActivity.mAccessToken)
                    .build();
        } else if (timePhase == 1) {
            req = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks?time_range=medium_term")
                    .addHeader("Authorization",
                            "Bearer " + MainActivity.mAccessToken)
                    .build();
        } else {
            req = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks?time_range=long_term")
                    .addHeader("Authorization",
                            "Bearer " + MainActivity.mAccessToken)
                    .build();
        }

        cancelCall(mCall1);
        mCall1 = MainActivity.mOkHttpClient.newCall(req);

        mCall1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String track = response.body().string();
                if (track.contains("\"status\": 401,")) {
                    System.out.println("TRACK FAIL" + track);
                    MainActivity.FAILED_CALL = true;
//                    SpotifyCalls.getToken(MainActivity.currActivity);
                    numFail++;
                    if (numFail >= 2) {
                        MainActivity.comingFromRefresh = true;
                        new RefreshAsync().execute();
                    }
                } else {
                    System.out.println("Track" + track);
                    numGot[0]++;
                    JSONTrack[0] = track;
                    if (numGot[0] == 2) {
                        wrapped[0] = HANDLE_JSON.createWrappedFromJSON(
                                JSONArt[0], JSONTrack[0], new Date());
                        System.out.println(wrapped[0].getArtists().size());
                        MainActivity.currUser.addWrapped(wrapped[0]);
                        System.out.println("BEFORE UPDATE"
                                + MainActivity.currUser.getWraps().size());
                        FIRESTORE.updateUser(MainActivity.currUser);
                        MainActivity.comingFromRefresh = false;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                navigateToNew();
                            }
                        });
                    }
                }
            }
            //fragment.notifyTrack();
            //setTextAsync(jsonObject.toString(3), profileTextView);
        });

        //Getting artists
        final Request req2;
        if (timePhase == 0) {
            req2 = new Request.Builder().url("https://api.spotify.com/v1/me/top/artists?time_range=short_term")
                    .addHeader("Authorization",
                            "Bearer " + MainActivity.mAccessToken)
                    .build();
        } else if (timePhase == 1) {
            req2 = new Request.Builder().url("https://api.spotify.com/v1/me/top/artists?time_range=medium_term")
                    .addHeader("Authorization",
                            "Bearer " + MainActivity.mAccessToken)
                    .build();

        } else {
            req2 = new Request.Builder().url("https://api.spotify.com/v1/me/top/artists?time_range=long_term")
                    .addHeader("Authorization",
                            "Bearer " + MainActivity.mAccessToken)
                    .build();
        }

        cancelCall(mCall2);
        mCall2 = MainActivity.mOkHttpClient.newCall(req2);



        mCall2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String art = response.body().string();
                if (art.contains("\"status\": 401,")) {
                    System.out.println("ART FAIL" + art);
                    MainActivity.FAILED_CALL = true;
//                    SpotifyCalls.getToken(MainActivity.currActivity);
                    numFail++;
                    if (numFail >= 2) {
                        MainActivity.comingFromRefresh = true;
                        new RefreshAsync().execute();
                    }
                } else {
                    System.out.println("ART" + art);
                    numGot[0]++;
                    JSONArt[0] = art;
                    if (numGot[0] == 2) {
                        wrapped[0] = HANDLE_JSON.createWrappedFromJSON(
                                JSONArt[0], JSONTrack[0], new Date());
                        MainActivity.currUser.addWrapped(wrapped[0]);
                        System.out.println("BEFORE UPDATE"
                                + MainActivity.currUser.getWraps().size());
                        FIRESTORE.updateUser(MainActivity.currUser);
                        MainActivity.comingFromRefresh = false;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                navigateToNew();
                            }
                        });
                    }
                    //fragment.notifyArt();
                    //setTextAsync(jsonObject.toString(3), profileTextView);
                }
            }
        });
        return null;
    }

    /**
     * Once all information has been obtained, transfers to next fragment.
     * @param result Void.
     */
    @Override
    protected void onPostExecute(Void result) {
//        FIRESTORE.updateUser(MainActivity.currUser);
        System.out.println("here it is");
    }

    private void navigateToNew() {
        try {
            MainActivity.currActivity.afterCall();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void cancelCall(Call mCall) {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}