package com.example.spotifyapp2340.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spotifyapp2340.MainActivity;
import com.example.spotifyapp2340.R;
import com.example.spotifyapp2340.ui.newWrapped.NewWrappedFragment;
import com.example.spotifyapp2340.ui.wrapped.WrappedFragment;
import com.example.spotifyapp2340.wrappers.Wrapped;

import java.io.IOException;
import java.util.Calendar;

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
    Call mCall1;
    Call mCall2;
    WrappedFragment fragment;
    NavController controller;
    Wrapped wrapped;

    /**
     * Constructor that takes in a controller.
     *
     * @param controller controller used to navigate to next screen
     */
    public NewWrappedAsync(NavController controller) {
        if (controller == null) {
            throw new NullPointerException("NavController is null.");
        }
        this.controller = controller;
    }

    /**
     * Performs getting artists & tracks in the background.
     * @param params not needed. Void.
     *
     * @return null
     */
    @Override
    protected Void doInBackground(Void... params) {
        wrapped = new Wrapped(Calendar.getInstance());
        MainActivity.currUser.addWrapped(wrapped);
        Log.d("Test", "This is a test to check if doInBackground is working.");
        //Getting tracks
        final Request req = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks")
                .addHeader("Authorization",
                        "Bearer " + MainActivity.mAccessToken)
                .build();

        mCall1 = MainActivity.mOkHttpClient.newCall(req);

        mCall1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String track = response.body().string();
                System.out.println(track);
                wrapped.setJSONTrack(track);
                //fragment.notifyTrack();
                //setTextAsync(jsonObject.toString(3), profileTextView);
            }
        });

        //Getting artists
        final Request req2 = new Request.Builder().url("https://api.spotify.com/v1/me/top/tracks")
                .addHeader("Authorization", "Bearer " + MainActivity.mAccessToken)
                .build();

        mCall2 = MainActivity.mOkHttpClient.newCall(req2);

        mCall2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("HTTP", "Failed to fetch data: " + e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String art = response.body().string();
                System.out.println(art);
                wrapped.setJSONArt(art);
                //fragment.notifyArt();
                //setTextAsync(jsonObject.toString(3), profileTextView);
            }
        });
        MainActivity.updateUser(MainActivity.currUser);
        return null;
    }

    /**
     * Once all information has been obtained, transfers to next fragment.
     * @param result Void.
     */
    @Override
    protected void onPostExecute(Void result) {
        MainActivity.updateUser(MainActivity.currUser);
        controller.navigate(R.id.action_navigation_newWrapped_to_wrap);
    }
}
